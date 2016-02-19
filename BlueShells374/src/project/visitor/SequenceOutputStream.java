package project.visitor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IMethod;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.javaClasses.MethodContainer;
import project.javaClasses.Model;

public class SequenceOutputStream implements IPhase {
	
	public static final String GENERATOR_NAME = "SequenceGenerator";
	@SuppressWarnings("unused")
	private static final String ERROR_ITEM_NOT_FOUND = "ERROR: The specified class or method could not be found";
	@SuppressWarnings("unused")
	private static final String ERROR_EXCEPTION = "ERROR: Something went wrong while trying to generate the diagram";
	@SuppressWarnings("unused")
	private static final String SYSTEM_OUTPUT_FILE = "input_output/diagram.sd";
	
	private final Visitor visitor;
	private int callDepth;
	private String methodName;
	private String className;
	private String parameters;
	private int counter;
	private Map<String, String> variables;
	private List<String> instances;
	private List<String> classList;
	private List<String> methodList;
	private boolean first;
	private FileOutputStream out;

	public SequenceOutputStream() {
		try {
			out = new FileOutputStream("input_output/diagram.sd");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.visitor = new Visitor();
		counter = 0;
		this.first = true;
		this.setUpVisitModel();
	}

	
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
	
	public void setUpVisitModel(){
		this.visitor.addVisit(VisitType.Visit, Model.class, (ITraverser t) -> {
			IModel m = (IModel) t;
			this.variables = new HashMap<String, String>();
			this.instances = new ArrayList<String>();
			this.classList = new ArrayList<>();
			this.methodList = new ArrayList<>();
			recursiveGenerateGraph(this.className, "arg0", this.methodName, this.parameters, this.callDepth, m);
		
			try {
				for (String clazz : this.classList) {
					out.write(clazz.getBytes());
				}
				out.write("\n".getBytes());
				for (String methods : this.methodList) {
					out.write(methods.getBytes());
				}
				Runtime rt = Runtime.getRuntime();
				rt.exec("lib\\sdedit-4.01.exe -o input_output\\diagram.png -t"
						+ " png input_output\\diagram.sd");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
	}
	
	/**
	 * Finds the {@link IClass} of a given string and returns the class object
	 * 
	 * @return - {@link IClass} object
	 */
	private IClass getModelClass(String name, IModel m) {
		for (IClass c : m.getClasses()) {
			if (c.getClassName().replace("/", "")
					.equals(name.replace(".", ""))) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Recursive version of generateGraph
	 * 
	 * @param Class
	 *            - Class object
	 * @param varName
	 *            - Instance name
	 * @param method
	 *            - Starting method to generate graph of
	 * @param desc
	 *            - String that holds parameters and return type
	 * @param depth
	 *            - The number of levels the sequence diagram has left to be
	 *            drawn
	 */
	private void recursiveGenerateGraph(String Class, String varName,
			String method, String desc, int depth, IModel model) {
		if (depth == 0) {
			return;
		}
		IClass c = this.getModelClass(Class.replace("/", ""), model);
		if (c != null) {
			if (desc != "") {
				Type[] args = Type.getArgumentTypes(desc);
				List<String> argStringArray = new ArrayList<String>();
				for (int i = 0; i < args.length; i++) {
					argStringArray.add(args[i].getClassName());
				}
				IMethod m = this.getMethod(c, argStringArray, method);
				if (m != null) {
					if (this.first){
						String name = "arg" + counter;
						String line1 = "" + name + ":"
								+ Class.replace(".", "")
								+ "[a]\n";
						this.classList.add(line1);
						variables.put(Class.replace(".", "/"), name);
						instances.add(Class.replace(".", "/"));
						this.first = false;
					}
					recursiveMethodGenerator(m, varName, depth, model);
				}
			}
		}

	}
	
	/**
	 * Recursively goes through to create different depths of the Sequence
	 * Diagram
	 * 
	 * @param m
	 *            - {@link IMethod} object used to retrieve information about
	 *            the method
	 * @param varName
	 *            - Instance name
	 * @param depth
	 *            - Depth the recursive call must stop at
	 */
	private void recursiveMethodGenerator(IMethod m, String varName,
			int depth, IModel model) {
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
				String fromName = variables.get(innerCall.getGoingFromClass());
				String line2 = fromName + ":" + name + ".new\n";
				this.methodList.add(line2);
			} else {
				if (!instances.contains(innerCall.getGoingToClass())) {
					counter++;
					String name = "arg" + counter;
					variables.put(innerCall.getGoingToClass(), name);
					// FIXME: Repetitive 
					instances.add(innerCall.getGoingToClass());
					String line1 = "" + name + ":"
							+ innerCall.getGoingToClass().replace("/", "")
							+ "[a]\n";
					this.classList.add(line1);
				}
				
				String argString = getArgs(innerCall);
				getReturnType(innerCall);
				String name = variables.get(innerCall.getGoingToClass());
				String fromName = variables.get(innerCall.getGoingFromClass());
				if (!innerCall.getGoingToMethod().equals("<init>")) {
					String line2 = fromName + ":" + name + "."
							+ innerCall.getGoingToMethod() + "(" + argString
							+ ")\n";
					this.methodList.add(line2);
					recursiveGenerateGraph(innerCall.getGoingToClass(), name,
							innerCall.getGoingToMethod(), innerCall.getDesc(),
							depth - 1, model);
				}
			}
		}
	}
	
	/**
	 * Retrieves the method from a class and that matches the list of parameters
	 * 
	 * @param c
	 *            - {@link IClass} object, holding information about the method
	 *            that is being searched for
	 * @param params
	 *            - List of strings that match the parameters in the method the
	 *            sequence diagram is being drawn for
	 * @return - {@link IMethod} object, holding information about the method
	 *         searched for originally
	 */
	private IMethod getMethod(IClass c, List<String> params, String method) {
		for (IMethod m : c.getIMethods()) {
			if (m.getName().equals(method)) {
				int check = -1;
				boolean hasFound = true;
				for (String args : m.getArguments()) {
					check++;
					if (!args.equals(params.get(check))) {
						hasFound = false;
						break;
					}
				}
				if (hasFound && (params.size() == (check + 1))) return m;
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the return type from the method container
	 * 
	 * @param innerCall
	 *            - {@link MethodContainer} object holding information
	 * @return - String value of the return type for the {@link MethodContainer}
	 */
	private String getReturnType(MethodContainer innerCall) {
		if (innerCall.getDesc() != "") {
			Type args = Type.getReturnType(innerCall.getDesc());
			String argtype = args.getClassName().replace(".", "");
			return argtype;

		}
		return "";
	}
	
	/**
	 * Retrieve the arguments from the {@link MethodContainer}
	 * 
	 * @param innerCall
	 *            - {@link MethodContainer} object holding information
	 * @return - Retrieve the arguments, in the form of a string
	 */
	private String getArgs(MethodContainer innerCall) {
		String argString = "";
		if (innerCall.getDesc() != "") {
			Type[] args = Type.getArgumentTypes(innerCall.getDesc());
			for (int i = 0; i < args.length; i++) {
				if (i == args.length - 1) {
					argString = argString + args[i].getClassName().replace("", "");
				} else {
					argString = argString + args[i].getClassName().replace("", "")
							+ ", ";
				}
			}
		}
		return argString;
	}


	@Override
	public void execute(Config config, IModel model) {
		this.className = config.SDclass;
		this.methodName = config.SDmethod;
		this.parameters = config.SDdesc;
		this.callDepth = config.SDcallDepth;
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}


}
