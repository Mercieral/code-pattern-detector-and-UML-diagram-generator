package problem.interfaces;

import java.util.List;

public interface IModel {
	
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

}
