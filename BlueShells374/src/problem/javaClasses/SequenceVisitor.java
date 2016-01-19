package problem.javaClasses;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IVisitor;

public class SequenceVisitor implements IVisitor {

	public SequenceVisitor(IModel model) {
		// TODO Sequence Visitor constructor
	}

	@Override
	public void visit(IModel model) {
		// TODO Sequence Model visit

	}

	@Override
	public void visit(IClass Class) {
		// TODO Sequence Class visit

	}

	@Override
	public void visit(IMethod method) {
		// TODO Sequence Method visit

	}

	@Override
	public void visit(IField field) {
		// TODO Sequence Field visit

	}

	@Override
	public void visit(IArrow arrow) {
		// TODO Sequence Arrow visit

	}

}
