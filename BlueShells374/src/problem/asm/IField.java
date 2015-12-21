package problem.asm;

public interface IField {
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * TODO
	 * 
	 * @param name - TODO
	 */
	public void setName(String name);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String getDesc();
	
	/**
	 * TODO
	 * 
	 * @param name - TODO
	 */
	public void setDesc(String desc);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String getSignature();
	
	/**
	 * TODO
	 * 
	 * @param sign - TODO
	 */
	public void setSignature(String sign);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public Object getValue();
	
	/**
	 * TODO
	 * 
	 * @param value - TODO
	 */
	public void setValue(Object value);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public EFieldAccess getAccessLevel();
	
	/**
	 * TODO
	 * 
	 * @param access - TODO
	 */
	public void setAccessLevel(EFieldAccess access);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String toString();

}
