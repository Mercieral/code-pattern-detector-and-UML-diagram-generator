package problem.interfaces;

import java.util.List;

import problem.visitor.ITraverser;

public interface IModel extends ITraverser{

	/**
	 * Add a {@link IClass} object to a the model
	 */
	public void addClass(IClass currentClass);

	/**
	 * Returns a list of {@link IClass} objects
	 * 
	 * @return - {@link List}
	 */
	public List<IClass> getClasses();

	/**
	 * Retrieves the list of {@link IRelation} objects that connect classes in
	 * the model
	 * 
	 * @return - List of {@link IRelation} objects
	 */
	public List<IRelation> getRelations();

	/**
	 * Add a {@link IRelation} between classes
	 * 
	 * @param relation
	 *            - {@link IRelation} object that represents relations between
	 *            the 2 objects
	 */
	public void addRelation(IRelation relation);

}
