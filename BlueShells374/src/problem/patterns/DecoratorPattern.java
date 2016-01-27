package problem.patterns;

import problem.interfaces.IPattern;

public class DecoratorPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	//need something with relations
	
	public DecoratorPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=green, style=filled,";
		this.UMLlabel = type; //type means decorator or component and will be provided from the visitor
	}
	
	@Override
	public String UMLproperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String UMLlabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
