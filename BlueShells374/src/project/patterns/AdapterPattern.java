package project.patterns;

import java.awt.Color;

import project.interfaces.IPattern;

public class AdapterPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	private String patternName;
	public Color color = Color.RED;
	
	public AdapterPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=red, style=filled,";
		this.UMLlabel = type; //type means target, adapter, or adaptee and will be provided from the visitor
		this.patternName = "Adapter Pattern";
	}
	
	@Override
	public String getProperty() {
		return UMLproperty;
	}

	@Override
	public String getLabel() {
		return UMLlabel;
	}
	
	@Override
	public String getClassName(){
		return this.className;
	}

	@Override
	public String getType() {
		return patternName;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

}
