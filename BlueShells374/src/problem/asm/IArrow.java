package problem.asm;

public interface IArrow {

	/**
	 * Provides a drawn arrow object, with connections between 2 objects
	 * 
	 * @return - Formatted arrow object to be added to the figure to be drawn
	 */
	public String drawArrow();

	/**
	 * Sets the starting object for the arrow
	 * 
	 * @param startObject
	 *            - String object for the arrow
	 */
	public void setFromObject(String startObject);

	/**
	 * Set the object the arrow is pointing to.
	 * 
	 * @param endObject
	 *            - String object for the arrow
	 */
	public void setToObject(String endObject);

	/**
	 * Returns the object the arrow is pointing from in the arrow. 
	 * 
	 * @return - String object the arrow is pointing from.
	 */
	public String getFromObject();

	/**
	 * Returns the object the arrow is pointing to in the arrow.
	 * 
	 * @return - String object the arrow was pointing to.
	 */
	public String getToObject();

}
