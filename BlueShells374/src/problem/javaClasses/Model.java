package problem.javaClasses;

import java.util.ArrayList;
import java.util.List;


import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.ITraverser;
import problem.interfaces.IVisitor;

/**
 * {@link IModel} object used to create file for GraphViz tool
 * 
 * @author gateslm, daniellm, mercieal
 *
 */
public class Model implements IModel, ITraverser {

	private List<IClass> classes;

	public Model() {
		this.classes = new ArrayList<IClass>();
	}

	@Override
	public void addClass(IClass currentClass) {
		classes.add(currentClass);
	}

	@Override
	public List<IClass> getClasses() {
		return classes;
	}

	@Override
	public void accept(IVisitor v) {
		// TODO Fill in Model accept
	}

}
