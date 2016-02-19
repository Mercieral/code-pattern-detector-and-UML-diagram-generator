package project.interfaces;

import java.util.Collection;
import java.util.List;

import project.visitor.ITraverser;

public interface IClass extends ITraverser {

	/**
	 * Gets a collection of type {@link IMethod}
	 * 
	 * @return - Collection<IMethod>
	 */
	public Collection<IMethod> getIMethods();

	/**
	 * Gets a collection of type {@link IField}
	 * 
	 * @return - Collection<IField>
	 */
	public Collection<IField> getIField();

	/**
	 * Gets the access level of the Class
	 * 
	 * @return - Access level of class
	 */
	public int getAccessLevel();

	/**
	 * Gets the signature of the Class
	 * 
	 * @return - String for signature
	 */
	public String getSignature();

	/**
	 * Gets the class name of the Class
	 * 
	 * @return - String for name
	 */
	public String getClassName();

	/**
	 * Gets the version of the Class
	 * 
	 * @return - double for class version
	 */
	public double getClassVersion();

	/**
	 * Gets a collection of strings that are interfaces for this class
	 * 
	 * @return - Collection of strings representing interfaces of the class
	 */
	public Collection<String> getInterface();

	/**
	 * Gets the name of the class that is extended on the this class
	 * 
	 * @return - String for extension
	 */
	public String getExtension();

	/**
	 * Add a {@link IMethod}
	 * 
	 * @param {@link
	 * 			IMethod} method - Method in the class
	 */
	public void addIMethod(IMethod method);

	/**
	 * Add a {@link IField}
	 * 
	 * @param {@link
	 * 			IField} field - Field in the class
	 */
	public void addIField(IField field);

	/**
	 * Set the access level of this object
	 * 
	 * @param access
	 *            - Access level of class
	 */
	public void setAccessLevel(int access);

	/**
	 * Set class signature
	 * 
	 * @param signature
	 *            - signature of class
	 */
	public void setSignature(String signature);

	/**
	 * Set class name
	 * 
	 * @param name
	 *            - Name of the class
	 */
	public void setClassName(String name);

	/**
	 * Set class version
	 * 
	 * @param version
	 *            - Version of class
	 */
	public void setClassVersion(double version);

	/**
	 * Add an interface
	 * 
	 * @param inter
	 *            - Interface this object uses
	 */
	public void addInterface(String inter);

	/**
	 * Set the class extension. Only one extension for a class in Java.
	 * 
	 * @param extension
	 *            - Extension for class
	 */
	public void setExtension(String extension);

	/**
	 * For debugging prints out contents of the class
	 * 
	 * @return - Needed if special string was used
	 */
	public String toString();

	/**
	 * Adds an {@link IPattern} object to the collection in the IClass object.
	 * 
	 * @param pattern
	 *            - Pattern object that needs to be held by this object
	 */
	public void addPattern(IPattern pattern);

	/**
	 * Provides the collection of {@link IPattern} object in this object.
	 * 
	 * @return - Collection of IPattern objects
	 */
	public List<IPattern> getPatterns();
}
