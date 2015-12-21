package problem.asm;

public interface IMethod {
	
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
	public EMethodAccess getAccessLevel();
	
	/**
	 * TODO
	 * 
	 * @param access - TODO
	 */
	public void setAccessLevel(EMethodAccess access);
	
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
	public String[] getExceptions();
	
	/**
	 * TODO
	 * 
	 * @param exceptions - TODO
	 */
	public void setExceptions(String[] exceptions);
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String toString();

}
