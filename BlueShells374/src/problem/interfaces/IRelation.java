package problem.interfaces;

import problem.visitor.ITraverser;

public interface IRelation extends ITraverser {


	/**
	 * Provides a string value to add the correct format needed to draw a
	 * relation object
	 * 
	 * @return - String value to connect the values
	 */
	public String drawRelation();

	/**
	 * Sets the object the relation starts at
	 * 
	 * @param startObject
	 *            - Name of start object
	 */
	public void setFromObject(String startObject);

	/**
	 * Sets the object the relation ends at
	 * 
	 * @param endObject
	 *            - Name of end object
	 */
	public void setToObject(String endObject);

	/**
	 * Retrieves the object the relation comes from
	 * 
	 * @return - String name of start object
	 */
	public String getFromObject();

	/**
	 * Retrieves the object the relation goes to
	 * 
	 * @return - String name of the end object
	 */
	public String getToObject();
	
	public void addLabel(String label);
	

}
