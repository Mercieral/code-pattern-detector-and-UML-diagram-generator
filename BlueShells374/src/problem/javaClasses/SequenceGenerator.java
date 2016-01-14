package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;

import problem.interfaces.IClass;
import problem.interfaces.IGenerator;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;

/**
 * Used with the tool: http://sdedit.sourceforge.net/
 * 
 * Will make the Sequence Diagram
 * 
 * @author gateslm
 *
 */
public class SequenceGenerator implements IGenerator {

	public static final String GENERATOR_NAME = "SequenceGenerator";

	private static final String ERROR_NO_EXISTING_ARGS = "ERROR: "
			+ "The provided arguments do match any existing arguments";
	private static final String ERROR_ITEM_NOT_FOUND = "ERROR: "
			+ "The specified class or method could not be found";
	private static final String ERROR_EXCEPTION = "ERROR: "
			+ "Something went wrong while trying to generate the diagram";

	private static final String SYSTEM_OUTPUT_FILE = "input_output/diagram.sd";

	private IModel model;
	private String name;
	private String className;
	private String methodName;
	private List<String> parameters;
	private int callDepth;
	private IMethod startMethod;
	private IClass startClass;
	private List<String> classList;
	private List<String> methodList;
	private Map<String, String> variables;
	private List<String> instances;
	private int counter;

	public SequenceGenerator(IModel model) {
		this.model = model;
		this.name = GENERATOR_NAME;
		this.startClass = null;
		this.startMethod = null;
		this.classList = new ArrayList<>();
		this.methodList = new ArrayList<>();
		this.variables = new HashMap<String, String>();
		this.instances = new ArrayList<String>();
		counter = 0;
	}

	public SequenceGenerator(IModel model, String className, String methodName,
			List<String> parameters, Integer maximumCallDepth) {
		this.model = model;
		this.name = GENERATOR_NAME;
		this.className = className;
		this.methodName = methodName;
		this.parameters = parameters;
		this.callDepth = (maximumCallDepth == null) ? 5
				: (int) maximumCallDepth;
		this.startMethod = null;
		this.startClass = null;
		this.classList = new ArrayList<>();
		this.methodList = new ArrayList<>();
		this.variables = new HashMap<String, String>();
		this.instances = new ArrayList<String>();
		counter = 0;
	}

	/**
	 * TODO
	 * 
	 * @param name
	 *            - TODO
	 */
	public void setClassName(String name) {
		this.className = name;
	}

	/**
	 * TODO
	 * 
	 * @param name
	 *            - TODO
	 */
	public void setMethodName(String name) {
		this.methodName = name;
	}

	/**
	 * TODO
	 * 
	 * @param params
	 *            - TODO
	 */
	public void setParameters(List<String> params) {
		this.parameters = params;
	}

	public void setMaxCallDepth(int depth) {
		this.callDepth = depth;
	}

	@Override
	public void execute() {
		System.out.println("class: " + this.className);
		System.out.println("method: " + this.methodName);
		System.out.println("params: " + this.parameters.toString());
		System.out.println("depth: " + this.callDepth);
		try {
			IClass c = this.getModelClass(this.className);
			if (c != null) {
				this.startClass = c;
				IMethod m = this.getMethod(c, this.parameters);
				if (m != null) {
					this.startMethod = m;
				}
			}
			if (this.startMethod == null) {
				System.out.println(ERROR_ITEM_NOT_FOUND);
				return;
			}
			generateGraph();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(ERROR_EXCEPTION);
		}

	}
	
	//FIXME change method name
	private void testMethod(IMethod m, String varName, int depth){
		System.out.println("generating for method " + m.getName());
		for (MethodContainer innerCall : m.getInnerCalls()) {
			if (innerCall.isInstantiation()) {
				counter++;
				String name = "arg" + counter;
				instances.add(innerCall.getGoingToClass());
				variables.put(innerCall.getGoingToClass(), name);
				String line1 = "/" + name + ":"
						+ innerCall.getGoingToClass().replace("/", "")
						+ "[a]\n";
				this.classList.add(line1);
				String line2 = varName + ":" + name + ".new\n";
				this.methodList.add(line2);
			} else {
				String argString = getArgs(innerCall);
				getReturnType(innerCall);
				String name = variables.get(innerCall.getGoingToClass());
				if (!innerCall.getGoingToMethod().equals("<init>")) {
					String line2 = varName + ":" + name + "."
							+ innerCall.getGoingToMethod() + "(" + argString
							+ ")\n";
					this.methodList.add(line2);
					recursiveReach(innerCall.getGoingToClass(), name, innerCall.getGoingToMethod(), innerCall.getDesc(), depth -1);
				}
			}
		}
	}
	
	private void recursiveReach(String Class, String varName, String method, String desc, int depth){
		if (depth == 0){
			return;
		}
		IClass c = this.getModelClass(Class.replace("/", ""));
		if (c != null){
			if (desc != "") {
				Type[] args = Type.getArgumentTypes(desc);
				List<String> argStringArray = new ArrayList<String>();
				for (int i = 0; i < args.length; i++) {
					argStringArray.add(args[i].getClassName());
				}
				IMethod m = this.getMethod(c, argStringArray);
				if (m != null){
					//System.out.println("hit recursive method " + method +" "+ m.getDesc());
					testMethod(m, varName, depth - 1);
				}
			}
		}
		
	}

	/**
	 * TODO
	 * 
	 * @throws IOException
	 *             - TODO
	 */
	private void generateGraph() throws IOException {
		counter = 0;
		System.out.println("generating sequence diagram file");
		OutputStream out = new FileOutputStream(SYSTEM_OUTPUT_FILE);
		String firstLine = "arg0" + ":"
				+ this.startClass.getClassName().replace("/", "") + "[a]\n";
		out.write(firstLine.getBytes());
		instances.add(this.startClass.getClassName());
		variables.put(this.startClass.getClassName(), "arg0");
		for (MethodContainer innerCall : this.startMethod.getInnerCalls()) {
			if (innerCall.isInstantiation()) {
				counter++;
				String name = "arg" + counter;
				variables.put(innerCall.getGoingToClass(), name);
				instances.add(innerCall.getGoingToClass());
				String line1 = "/" + name + ":"
						+ innerCall.getGoingToClass().replace("/", "")
						+ "[a]\n";
				this.classList.add(line1);
				String line2 = "arg0" + ":" + name + ".new\n";
				this.methodList.add(line2);
			} else {
				if (!instances.contains(innerCall.getGoingToClass())) {
					counter++;
					String name = "arg" + counter;
					variables.put(innerCall.getGoingToClass(), name);
					instances.add(innerCall.getGoingToClass());
					String line1 = "/" + name + ":"
							+ innerCall.getGoingToClass().replace("/", "")
							+ "[a]\n";
					this.classList.add(line1);

					String argString = getArgs(innerCall);
					getReturnType(innerCall);
					if (!innerCall.getGoingToMethod().equals("<init>")) {
						String line2 = "arg0" + ":" + name + "."
								+ innerCall.getGoingToMethod() + "(" + argString
								+ ")\n";
						this.methodList.add(line2);
						recursiveReach(innerCall.getGoingToClass(), name, innerCall.getGoingToMethod(), innerCall.getDesc(), this.callDepth -1);
					}
				} else {
					String argString = getArgs(innerCall);
					getReturnType(innerCall);
					String name = variables.get(innerCall.getGoingToClass());
					if (!innerCall.getGoingToMethod().equals("<init>")) {
						String line2 = "arg0" + ":" + name + "."
								+ innerCall.getGoingToMethod() + "(" + argString
								+ ")\n";
						this.methodList.add(line2);
						recursiveReach(innerCall.getGoingToClass(), name, innerCall.getGoingToMethod(), innerCall.getDesc(), this.callDepth -1);
					}
				}
			}
		}

		for (String clazz : this.classList) {
			out.write(clazz.getBytes());
		}
		out.write("\n".getBytes());

		for (String methods : this.methodList) {
			out.write(methods.getBytes());
		}

		out.close();
		Runtime rt = Runtime.getRuntime();
		rt.exec("lib\\sdedit-4.01.exe -o input_output\\diagram.png -t"
				+ " png input_output\\diagram.sd");
	}

	private String getReturnType(MethodContainer innerCall){
		if (innerCall.getGoingToMethod().equals("listIterator")){
			System.out.println("hereeeee");
			System.out.println(innerCall.getDesc());
		}
		if (innerCall.getDesc() != "") {
			Type args = Type.getReturnType(innerCall.getDesc());
			String argtype = args.getClassName().replace(".", "");
			if (!instances.contains(argtype)){
				counter++;
				String name = "arg" + counter;
				variables.put(args.getClassName().replace(".", "/"), name);
				instances.add(argtype);
				String line1 = "" + name + ":"
						+ argtype.replace("/", "")
						+ "[a]\n";
				this.classList.add(line1);
			}
			return argtype;
			
		}
		return "";
	}
	/**
	 * TODO
	 * 
	 * @param innerCall
	 *            - TODO
	 * @return - TODO
	 */
	private String getArgs(MethodContainer innerCall) {
		String argString = "";
		if (innerCall.getDesc() != "") {
			Type[] args = Type.getArgumentTypes(innerCall.getDesc());
			for (int i = 0; i < args.length; i++) {
				String argtype = args[i].getClassName().replace(".", "");
				if (!instances.contains(argtype)){
					counter++;
					String name = "arg" + counter;
					variables.put(args[i].getClassName().replace(".", "/"), name);
					instances.add(argtype);
					String line1 = "" + name + ":"
							+ argtype.replace("/", "")
							+ "[a]\n";
					this.classList.add(line1);
				}
				if (i == args.length - 1) {
					argString = argString + args[i].toString().replace(";", "");
				} else {
					argString = argString + args[i].toString().replace(";", "")
							+ ", ";
				}
			}
		}
		return argString;
	}

	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	private IClass getModelClass(String name) {
		for (IClass c : this.model.getClasses()) {
			if (c.getClassName().replace("/", "")
					.equals(name.replace(".", ""))) {
				return c;
			}
		}
		return null;
	}

	/**
	 * TODO
	 * 
	 * @param c
	 *            - TODO
	 * @param params
	 *            - TODO
	 * @return - TODO
	 */
	private IMethod getMethod(IClass c, List<String> params) {
		for (IMethod m : c.getIMethods()) {
			// System.out.println("\t" + m.getName() + " ---- " +
			// this.methodName);
			if (m.getName().equals(this.methodName)) {
				int counter = -1;
				boolean hasFound = true;
				for (String args : m.getArguments()) {
					counter++;
					if (!args.equals(params.get(counter))) {
						System.out.println(ERROR_NO_EXISTING_ARGS);
						hasFound = false;
						break;
					}
					
				}
				if (hasFound && (params.size() == (counter + 1))) {
					return m;
				}
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
