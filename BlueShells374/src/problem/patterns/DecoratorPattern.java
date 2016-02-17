package problem.patterns;

import problem.interfaces.IPattern;

public class DecoratorPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	private String patternName;
	//need something with relations
	
	public DecoratorPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=green, style=filled,";
		this.UMLlabel = type; //type means decorator or component and will be provided from the visitor
		this.patternName = "Decorator Pattern";
	}
	
	@Override
	public String getProperty() {
		return this.UMLproperty;
	}

	@Override
	public String getLabel() {
		return this.UMLlabel;
	}
	
	@Override
	public String getClassName(){
		return this.className;
	}

	@Override
	public String getType() {
		return patternName;
	}

}
