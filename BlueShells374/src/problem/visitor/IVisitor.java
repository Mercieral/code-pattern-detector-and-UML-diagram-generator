package problem.visitor;

public interface IVisitor {
	public void preVisit(ITraverser c);
	public void visit(ITraverser c);
	public void postVisit(ITraverser c);
	
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m);
	public void removeVisit(VisitType visitType, Class<?> clazz);

}
