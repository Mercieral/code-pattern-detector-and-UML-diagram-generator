package problem.visitor;

import java.util.ArrayList;
import java.util.List;

import problem.interfaces.IModel;

public class AdapterVisitor implements IInvoker {

	private IVisitor visitor;
//	private List<String> 


	public AdapterVisitor() {
		// TODO Auto-generated constructor stub
		this.visitor = new Visitor();

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void setupPreVisitClass() {

	}

}
