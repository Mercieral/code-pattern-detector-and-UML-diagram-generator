package problem.asm;

import java.util.Collection;

public interface IClass {
	
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
	 * Gets the {@link EFieldAccess} of the Class
	 * 
	 * @return - {@link EFieldAccess}
	 */
	public int getAcessLevel();
	
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
	 * Gets the {@link IClass} that is the superClass of the Class
	 * 
	 * @return - {@link IClass} for super class
	 */
	public IClass getSuperName();
	
	/**
	 * Gets a collection of strings that are interfaces for this class
	 * 
	 * @return - Collection<String>
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
	 * @param {@link IMethod} method
	 */
	public void addIMethod(IMethod method);
	
	/**
	 * Add a {@link IField}
	 * 
	 * @param {@link IField} field
	 */
	public void addIField(IField field);
	
	/**
	 * Set class access level
	 * 
	 * @param {@link EClassAccess} access
	 */
	public void setAccessLevel(int access);
	
	/**
	 * Set class signature
	 * 
	 * @param signature
	 */
	public void setSignature(String signature);
	
	/**
	 * Set class name
	 * 
	 * @param name
	 */
	public void setClassName(String name);
	
	/**
	 * Set Class version
	 * 
	 * @param version
	 */
	public void setClassVersion(double version);
	
	/**
	 * Set {@link IClass} super class
	 * 
	 * @param - {@link IClass} name
	 */
	public void setSuperName(IClass name);
	
	/**
	 * Add an interface 
	 * 
	 * @param inter
	 */
	public void addInterface(String inter);
	
	/**
	 * Set the class extension
	 * 
	 * @param extension
	 */
	public void setExtension(String extension);
	
	/**
	 * For debugging prints out contents of the class
	 * 
	 * @return
	 */
	public String toString();
}
