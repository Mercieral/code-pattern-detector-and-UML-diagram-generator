package problem.interfaces;

/**
 * Interface to standardize the output of the drawing files
 * 
 * @author gateslm
 *
 */
public interface IGenerator {

	/**
	 * Draws the desired output graph
	 */
	public void execute();

	/**
	 * Returns the name of the of the generator type
	 * 
	 * @return - String value of the returning type, such as UML, Sequence
	 *         Diagram
	 */
	public String getName();
}
