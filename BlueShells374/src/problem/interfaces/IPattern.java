package problem.interfaces;

public interface IPattern {

	/**
	 * Property needed for creating visual representation
	 * 
	 * @return - Property needed to illustrate in visual representation
	 */
	public String getProperty();

	/**
	 * Label to display on to visual representation
	 * 
	 * @return - Label string for visual representation
	 */
	public String getLabel();

	/**
	 * Name of the class the visual representation is being used for
	 * 
	 * @return - Name for visual representation
	 */
	public String getClassName();
}
