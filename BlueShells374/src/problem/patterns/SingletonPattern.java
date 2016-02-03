package problem.patterns;

import problem.interfaces.IPattern;

public class SingletonPattern implements IPattern {
	private String className;
	private String UMLproperty;
	private String UMLlabel;

	public SingletonPattern(String className) {
		this.className = className;
		this.UMLproperty = "color=blue,";
		this.UMLlabel = "\\<\\<Singleton\\>\\>";
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
}
