package project.patterns;

import project.interfaces.IPattern;

public class SingletonPattern implements IPattern {
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	private String patternName;

	public SingletonPattern(String className) {
		this.className = className;
		this.UMLproperty = "color=blue,";
		this.UMLlabel = "\\<\\<Singleton\\>\\>";
		this.patternName = "Singleton Pattern";
	}

	@Override
	public String getProperty() {
		return this.UMLproperty;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public String getLabel() {
		return this.UMLlabel;
	}

	@Override
	public String getType() {
		return patternName;
	}
}
