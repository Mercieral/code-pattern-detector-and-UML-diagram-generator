package problem.javaClasses;

public class MethodContainer {

	/**
	 * True for instantiation, false for a method call
	 */
	private boolean instantiation;

	private String goingToClass;
	private String goingFromClass;
	private String goingToMethod;
	private String desc;

	public MethodContainer() {
		this.instantiation = false;
		this.goingFromClass = "";
		this.goingToClass = "";
		this.goingToMethod = "";
		this.desc = "";
	}

	/**
	 * Determines what type of method it the container class has
	 * 
	 * @return - True if instantiation, false if method call
	 */
	public boolean isInstantiation() {
		return instantiation;
	}

	/**
	 * Set what type of method it the container class has
	 * 
	 * @param instantiation
	 *            -True if instantiation, false if method call
	 */
	public void setInstantiation(boolean instantiation) {
		this.instantiation = instantiation;
	}

	/**
	 * Retrieve the class the object is pointing to
	 * 
	 * @return - String value the class is going to
	 */
	public String getGoingToClass() {
		return goingToClass;
	}

	/**
	 * Set the class the object is pointing to
	 * 
	 * @param goingToClass
	 *            - String value the class is going to
	 */
	public void setGoingToClass(String goingToClass) {
		this.goingToClass = goingToClass;
	}

	/**
	 * Retrieve the class the object is coming from
	 * 
	 * @return - String value the class is coming to
	 */
	public String getGoingFromClass() {
		return goingFromClass;
	}

	/**
	 * Set the class the object is coming from
	 * 
	 * @param goingFromClass
	 *            - String value the class is coming to
	 */
	public void setGoingFromClass(String goingFromClass) {
		this.goingFromClass = goingFromClass;
	}

	/**
	 * Retrieve the method the object is going to
	 * 
	 * @return - String of method the object is pointing to
	 */
	public String getGoingToMethod() {
		return this.goingToMethod;
	}

	/**
	 * Sets the method the object is going to
	 * 
	 * @param methodName
	 *            - String of method the object is pointing to
	 */
	public void setGoingToMethod(String methodName) {
		this.goingToMethod = methodName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDesc(){
		return this.desc;
	}
}
