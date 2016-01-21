package problem.patterns;

import problem.interfaces.IPattern;

public class SingletonPattern implements IPattern {
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	
	public SingletonPattern(String className) {
		this.className = className;
		this.UMLproperty = "color=blue,";
		this.UMLlabel = "<<Singleton>>";
	}
	
	@Override
	public String UMLproperty() {
		return this.UMLproperty;
	}

	public String getClassName(){
		return this.className;
	}

	@Override
	public String UMLlabel() {
		return this.UMLlabel;
	}
}
