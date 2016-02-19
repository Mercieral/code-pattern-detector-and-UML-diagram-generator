package project.patterns;

import project.interfaces.IPattern;

public class CompositePattern implements IPattern {

	private String className;
	private String UMLProperty;
	private String UMLlabel;
	private String patternName;

	/**
	 * Container class for holding information about creating representation of
	 * the Component Pattern
	 * 
	 * @param className
	 *            - name of class the pattern is being added to
	 * @param type
	 *            - The label for the class name: Component (interface for all
	 *            objects in the composition), Composite (Behavior of the
	 *            components having children and to store child component), Leaf
	 *            (Behavior for the elements in the composition)
	 */
	public CompositePattern(String className, String type) {
		this.className = className;
		this.UMLProperty = "fillcolor=yellow, style=filled, ";
		this.UMLlabel = type;
		this.patternName = "Composite Pattern";
	}

	@Override
	public String getProperty() {
		return this.UMLProperty;
	}

	@Override
	public String getLabel() {
		return this.UMLlabel;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public String getType() {
		return patternName;
	}

}
