package problem.interfaces;

public interface IVisitor {

	public void visit(IModel model);
	public void visit(IClass Class);
	public void visit(IMethod method);
	public void visit(IField field);
	public void visit(IArrow arrow);
}
