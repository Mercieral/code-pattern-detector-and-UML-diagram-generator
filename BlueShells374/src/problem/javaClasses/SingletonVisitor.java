package problem.javaClasses;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IVisitor;

public class SingletonVisitor implements IVisitor {

	public SingletonVisitor(IModel model) {
		// Singleton Visitor constructor
	}

	@Override
	public void visit(IModel model) {
		// TODO Singleton model visit

	}

	@Override
	public void visit(IClass Class) {
		// TODO Singleton class visit

	}

	@Override
	public void visit(IMethod method) {
		// TODO Singleton method visit

	}

	@Override
	public void visit(IField field) {
		// TODO Auto-generated field stub

	}

	@Override
	public void visit(IArrow arrow) {
		// TODO Auto-generated arrow stub

	}

}
