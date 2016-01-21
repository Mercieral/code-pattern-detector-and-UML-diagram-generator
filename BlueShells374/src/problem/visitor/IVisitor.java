package problem.visitor;

public interface IVisitor {
	/**
	 * Initial steps in first visit
	 * 
	 * @param c
	 *            - Object being visited
	 */
	public void preVisit(ITraverser c);

	/**
	 * Repetitive step in visiting object
	 * 
	 * @param c
	 *            - Object being visited
	 */
	public void visit(ITraverser c);

	/**
	 * Ending steps in visit
	 * 
	 * @param c
	 *            - Object being visited
	 */
	public void postVisit(ITraverser c);

	/**
	 * Adds the visit method to the visitor
	 * 
	 * @param visitType
	 *            - {@link VisitType} enum is used to denote type of visit
	 * @param clazz
	 *            - Class object working with
	 * @param m
	 *            - Method that is executed when visited
	 */
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m);

	/**
	 * Removes the visit method from the visitor
	 * 
	 * @param visitType
	 *            - {@link VisitType} enum is used to denote type of visit
	 * @param clazz
	 *            - Class object working with
	 */
	public void removeVisit(VisitType visitType, Class<?> clazz);

}
