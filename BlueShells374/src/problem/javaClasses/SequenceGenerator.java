package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public SequenceGenerator(IModel model){
		this.model = model;
		this.name = GENERATOR_NAME;
		this.startClass = null;
		this.startMethod = null;
		this.classList = new ArrayList<>();
		this.methodList = new ArrayList<>();
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
	}
	
	public void setClassName(String name){
		this.className = name;
	}
	
	public void setMethodName(String name){
		this.methodName = name;
	}
	
	public void setParameters(List<String> params){
		this.parameters = params;
	}
	
	public void setMaxCallDepth(int depth){
		this.callDepth = depth;
	}
	

	@Override
	public void execute() {
		System.out.println("class: " + this.className);
		System.out.println("method: " + this.methodName);
		System.out.println("params: " + this.parameters.toString());
		System.out.println("depth: " + this.callDepth);
		try {
			for (IClass c : this.model.getClasses()){
				System.out.println(c.getClassName() + " ---- " + this.className);
				if (c.getClassName().replace("/", "").equals(this.className.replace(".", ""))){
					//System.out.println("HITTTTTTT______________________________");
					this.startClass = c;
					for (IMethod m : c.getIMethods()){
						//System.out.println("\t" + m.getName() + " ---- " + this.methodName);
						if (m.getName().equals(this.methodName)){
							//System.out.println("\tHITTTTTTT______________________________");
							this.startMethod = m;
							int counter = 0;
							for (String args : m.getArguments()){
								System.out.println(args);
								if (!args.equals(this.parameters.get(counter))){
									System.out.println("ERROR: The provided arguments do match any existing arguments");
									return;
								}
									
							}
							break;
						}
					}
					break;
				}		
			}
			if (this.startMethod == null){
				System.out.println("ERROR: The specified class or method could not be found");
				return;
			}
			generateGraph();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"ERROR: Something went wrong while trying to generate the diagram");
		}
		
	}
	
	private void generateGraph() throws IOException {
		int counter = 0;
		System.out.println("generating sequence diagram file");
		String OUTPUT_FILE = "input_output/diagram.sd";
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		String firstLine = "arg0" + ":" +  this.startClass.getClassName().replace("/", "") + "[a]\n";
		out.write(firstLine.getBytes());
		ArrayList<String> instances = new ArrayList<String>();
		Map<String, String> variables = new HashMap<String, String>();
		instances.add(this.startClass.getClassName());
		variables.put(this.startClass.getClassName(), "arg0");
		for (MethodContainer innerCall : this.startMethod.getInnerCalls()){
				if(innerCall.isInstantiation()){
					counter++;
					String name = "arg" + counter;
					variables.put(innerCall.getGoingToClass(), name);
					instances.add(innerCall.getGoingToClass());
					String line1 =  "/" + name + ":" + innerCall.getGoingToClass().replace("/", "") + "[a]\n";
					this.classList.add(line1);
					String line2 = "arg0" + ":" + name + ".new\n";
					this.methodList.add(line2);
				}
				else{
					if (!instances.contains(innerCall.getGoingToClass())){
						counter++;
						String name = "arg" + counter;
						variables.put(innerCall.getGoingToClass(), name);
						instances.add(innerCall.getGoingToClass());
						String line1 = "/" + name + ":" + innerCall.getGoingToClass().replace("/", "") + "[a]\n";
						this.classList.add(line1);
						String line2 =  "arg0" + ":" + name + "." + innerCall.getGoingToMethod() + "()\n";
						this.methodList.add(line2);
					}
					else{
						String name = variables.get(innerCall.getGoingToClass());
						String line2 =  "arg0" + ":" + name + "." + innerCall.getGoingToMethod() + "()\n";
						this.methodList.add(line2);
					}
			}
		}
		
		for (String clazz : this.classList){
			out.write(clazz.getBytes());
		}
		out.write("\n".getBytes());
		
		for (String methods : this.methodList){
			out.write(methods.getBytes());
		}
		
		out.close();
		Runtime rt = Runtime.getRuntime();
		rt.exec("lib\\sdedit-4.01.exe -o input_output\\diagram.png -t png input_output\\diagram.sd");
		//String temp = generateClassBoxes();
	}

	
	private String generateClassBoxes(){
		
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
