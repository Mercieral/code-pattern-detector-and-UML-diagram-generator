package problem.visitor;

public interface IVisitMethod {

	/**
	 * Core logic behind each visit
	 * 
	 * @param t
	 *            - Object being visited
	 */
	public void execute(ITraverser t);

}
