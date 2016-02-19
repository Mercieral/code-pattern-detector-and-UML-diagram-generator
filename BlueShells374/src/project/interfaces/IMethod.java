package project.interfaces;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import project.javaClasses.MethodContainer;
import project.visitor.ITraverser;

/**
 * Object used to hold information about methods
 * 
 * @author gateslm
 *
 */
public interface IMethod extends ITraverser {

	/**
	 * Retrieves name of the method
	 * 
	 * @return - String object for method, without the parenthesis at the end
	 */
	public String getName();

	/**
	 * Sets the name of the name method
	 * 
	 * @param name
	 *            - String object that is the name of the method
	 */
	public void setName(String name);

	/**
	 * Access level of the object, should follow {@link Opcodes} access levels
	 * 
	 * @return - String object of access level for object
	 */
	public String getAccessLevel();

	/**
	 * Sets the access level of the method, follows the {@link Opcodes} access
	 * levels
	 * 
	 * @param access
	 *            - Access level for method
	 */
	public void setAccessLevel(String access);

	/**
	 * Gets the description of the method, according to {@link ClassVisitor}
	 * visit for methods
	 * 
	 * @return - Description of method
	 */
	public String getDesc();

	/**
	 * Sets the description of the method, according to {@link ClassVisitor}
	 * visit for methods
	 * 
	 * @param name
	 *            - String object: description of method
	 */
	public void setDesc(String desc);

	/**
	 * Gets the exceptions for the method
	 * 
	 * @return - Array of Strings will all exceptions of the method
	 */
	public String[] getExceptions();

	/**
	 * Set the exceptions of the methods
	 * 
	 * @param exceptions
	 *            - Array of strings for each exception the method needs
	 */
	public void setExceptions(String[] exceptions);

	/**
	 * Get the return type of the method
	 * 
	 * @return - String object: Method return type
	 */
	public String getReturnType();

	/**
	 * Sets the return type of the method
	 * 
	 * @param type
	 *            - String object: Method return type
	 */
	public void setReturnType(String type);

	/**
	 * Adds a single argument to the method, separation between the type and
	 * object name
	 * 
	 * @param arg
	 *            - String object: Argument to the method
	 */
	public void addArgument(String arg);

	/**
	 * Gets the list of arguments of the method
	 * 
	 * @return - {@link List} of strings: Contains all arguments given to the
	 *         method
	 */
	public List<String> getArguments();

	/**
	 * Debug method to help see what values are in the object, and to make it
	 * human readable
	 * 
	 * @return - String object: Help to debug problems and make object human
	 *         readable
	 */
	public String toString();

	/**
	 * Gets the list which contains all of the inner calls from the current
	 * method in the order they happen
	 * 
	 * @return
	 */
	public List<MethodContainer> getInnerCalls();

	/**
	 * Add an inner call to the inner call list inside of method
	 * 
	 * @param innerCall
	 *            - {@link MethodContainer} container class that holds info
	 *            about calls made in the methods
	 */
	public void addInnerCall(MethodContainer innerCall);

}
