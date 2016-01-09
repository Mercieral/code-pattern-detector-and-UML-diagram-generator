package problem.interfaces;

import java.io.IOException;
import java.util.List;

public interface IModel {

	/**
	 * Generates a graph to to visual of ASM
	 */
	public void generateGraph() throws IOException;
	
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
