package problem.visitor;

import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IModel;

public class ComponentVisitor implements IInvoker {

	private IVisitor visitor;
	private List<IClass> classList;

	public ComponentVisitor() {
		// TODO Auto-generated constructor stub
		this.visitor = new Visitor();
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

}
