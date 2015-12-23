package problem.asm;

import java.util.ArrayList;


/**
 * TODO
 * 
 * @author gateslm
 *
 */
public class Method implements IMethod {

	private String name;

	private String accessLevel;

	private String desc;

	private String[] exceptions;
	
	private String returnType;
	
	private ArrayList<String> arguments;

	/**
	 * TODO
	 */
	public Method() {
		this.name = null;
		this.accessLevel = null;
		this.desc = null;
		this.exceptions = null;
		this.returnType = null;
		this.arguments = null;
		this.arguments = new ArrayList<String>();
	}

	/**
	 * TODO
	 * 
	 * @param name
	 *            - TODO
	 * @param accessLevel
	 *            - TODO
	 * @param desc
	 *            - TODO
	 * @param exceptions
	 *            - TODO
	 */
	public Method(String name, String accessLevel, String desc,
			String[] exceptions) {
		super();
		this.name = name;
		this.accessLevel = accessLevel;
		this.desc = desc;
		this.exceptions = exceptions;
		this.arguments = new ArrayList<String>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAccessLevel() {
		return this.accessLevel;
	}

	@Override
	public void setAccessLevel(String access) {
		this.accessLevel = access;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String[] getExceptions() {
		return this.exceptions;
	}

	@Override
	public void setExceptions(String[] exceptions) {
		this.exceptions = exceptions;
	}
	
	@Override
	public String getReturnType() {
		return this.returnType;
	}
	
	@Override
	public void setReturnType(String type){
		this.returnType = type;
	}

	@Override
	public void addArgument(String arg) {
		this.arguments.add(arg);
		
	}

}
