package problem.asm;

import java.util.List;

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
	public String getAccessLevel();
	
	/**
	 * TODO
	 * 
	 * @param access - TODO
	 */
	public void setAccessLevel(String access);
	
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
	 * 
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String getReturnType();
	
	/**
	 * 
	 * TODO
	 * 
	 * @param type - TODO
	 */
	public void setReturnType(String type);
	
	
	/**
	 * 
	 * TODO
	 * 
	 * @param arg - TODO
	 */
	public void addArgument(String arg);
	
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public List<String> getArguments();
	
	/**
	 * TODO
	 * 
	 * @return - TODO
	 */
	public String toString();
	

}
