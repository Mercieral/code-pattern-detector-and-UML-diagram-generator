package problem.javaClasses;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IVisitor;

public class UMLVisitor implements IVisitor {

	public UMLVisitor(IModel model) {
		// TODO UML Visitor constructor
	}

	@Override
	public void visit(IModel model) {
		// TODO UML model visit

	}

	@Override
	public void visit(IClass Class) {
		// TODO UML Class visit

	}

	@Override
	public void visit(IMethod method) {
		// TODO UML Method visit
	}

	@Override
	public void visit(IField field) {
		// TODO UML Field visit

	}

	@Override
	public void visit(IArrow arrow) {
		// TODO UML arrow visit

	}

}
