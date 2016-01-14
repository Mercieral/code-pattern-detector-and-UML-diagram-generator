package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	public SequenceGenerator(IModel model){
		this.model = model;
		this.name = GENERATOR_NAME;
		this.startClass = null;
		this.startMethod = null;
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
		int counter = 1;
		System.out.println("generating sequence diagram file");
		String OUTPUT_FILE = "input_output/diagram.sd";
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		String firstLine = "arg0" + ":" +  this.startClass.getClassName().replace("/", "") + "[a]\n";
		out.write(firstLine.getBytes());
		for (MethodContainer innerCall : this.startMethod.getInnerCalls()){
				if(innerCall.isInstantiation()){
					String line1 = "/arg" + counter + ":" + innerCall.getGoingToClass().replace("/", "") + "[a]\n\n";
					String line2 = "arg0" + ":" + "arg" + counter + ".new\n";
					counter++;
					out.write(line1.getBytes());
					out.write(line2.getBytes());
				}
				else{
					String line1 = "/arg" + counter + ":" + innerCall.getGoingToClass().replace("/", "") + "[a]\n\n";
					String line2 =  "arg0" + ":" + "arg" + counter + "." + innerCall.getGoingToMethod() + "()\n";
					counter++;
					out.write(line1.getBytes());
					out.write(line2.getBytes());
				}
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
