package problem.patterns;

import problem.interfaces.IPattern;

public class SingletonPattern implements IPattern {
	private String className;
	private String UMLString;
	
	public SingletonPattern(String className) {
		this.className = className;
		this.UMLString = "color=blue,";
	}
	
	@Override
	public String UMLcommand() {
		return this.UMLString;
	}

	public String getClassName(){
		return this.className;
	}
}
