package problem.patterns;

import problem.interfaces.IPattern;

public class AdapterPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	
	public AdapterPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=red, style=filled,";
		this.UMLlabel = type; //type means target, adapter, or adaptee and will be provided from the visitor
	}
	
	@Override
	public String getProperty() {
		return UMLproperty;
	}

	@Override
	public String getLabel() {
		return UMLlabel;
	}
	
	public String getClassName(){
		return this.className;
	}

}
