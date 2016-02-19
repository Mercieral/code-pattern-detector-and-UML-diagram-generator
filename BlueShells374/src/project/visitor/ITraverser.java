package project.visitor;

public interface ITraverser {

	/**
	 * Object that can be looked through
	 * 
	 * @param v - {@link IVisitor} object that can be looked through
	 */
	public void accept(IVisitor v);
}
