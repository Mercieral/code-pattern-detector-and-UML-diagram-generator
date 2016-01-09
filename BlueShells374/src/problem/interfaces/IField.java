package problem.interfaces;

public interface IField {

	/**
	 * Gets the name of the field
	 * 
	 * @return - String for the name of field
	 */
	public String getName();

	/**
	 * Sets the name of the field
	 * 
	 * @param name
	 *            - name of field
	 */
	public void setName(String name);

	/**
	 * Get the description of the field
	 * 
	 * @return - String for the description of the field
	 */
	public String getDesc();

	/**
	 * Sets the description of the field, which is the type. 
	 * Use Type.getType(...).getClassName() to get the string value of the type
	 * 
	 * @param desc
	 *            - String for the description of the field, which is the type
	 */
	public void setDesc(String desc);

	/**
	 * Gets the signature for the field
	 * 
	 * @return - String for the signature of the field
	 */
	public String getSignature();

	/**
	 * Sets the signature for the field
	 * 
	 * @param sign
	 *            - String for the signature of the field
	 */
	public void setSignature(String sign);

	/**
	 * Retrieves the value from the field, in generic form. Casting required to
	 * get the object itself
	 * 
	 * @return - Generic object of the field
	 */
	public Object getValue();

	/**
	 * Sets the value for the field. A {@link Object} item is required.
	 * 
	 * @param value
	 *            - {@link Object} for field
	 */
	public void setValue(Object value);

	/**
	 * Uses {@link EFieldAccess} to determine the access level of the field
	 * 
	 * @return - {@link EFieldAccess} for the field
	 */
	public String getAccessLevel();

	/**
	 * Sets {@link EFieldAccess} for the field
	 * 
	 * @param access
	 *            - Uses {@link EFieldAccess} for values
	 */
	public void setAccessLevel(String access);

	/**
	 * Returns a value in human readable
	 * 
	 * @return - String for debugging
	 */
	public String toString();

}
