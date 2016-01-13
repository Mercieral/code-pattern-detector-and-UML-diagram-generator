package problem.javaClasses;

import java.util.List;

import problem.interfaces.IGenerator;
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

	public SequenceGenerator(IModel model, String className, String methodName,
			List<String> parameters, Integer maximumCallDepth) {
		this.model = model;
		this.name = GENERATOR_NAME;
		this.className = className;
		this.methodName = methodName;
		this.parameters = parameters;
		this.callDepth = (maximumCallDepth == null) ? 5
				: (int) maximumCallDepth;
	}

	@Override
	public void execute() {
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
