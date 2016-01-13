package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	}

	@Override
	public void execute() {
		try {
			generateGraph();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"ERROR: Something went wrong while trying to generate the diagram");
		}
		
	}
	
	private void generateGraph() throws IOException {
		System.out.println("generating sequence diagram file");
		String OUTPUT_FILE = "input_output/diagram.sd";
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		for (IClass c : this.model.getClasses()){
			if (c.getClassName().equals(this.className)){
				for (IMethod m : c.getIMethods()){
					if (m.getName().equals(this.methodName)){
						this.startMethod = m;
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
		
		
		String temp = generateClassBoxes();
	}

	
	private String generateClassBoxes(){
		
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
