package problem.javaClasses;

import java.util.ArrayList;
import java.util.List;


import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

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
		v.preVisit(this);
		v.visit(this);
		for(IClass c : this.classes){
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.postVisit(this);
		
	}

}
