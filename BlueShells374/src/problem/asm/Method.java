package problem.asm;

/**
 * TODO
 * 
 * @author gateslm
 *
 */
public class Method implements IMethod {
	
	private String name;
	
	private EMethodAccess accessLevel;
	
	private String desc;
	
	private String[] exceptions;
	
	/**
	 * TODO
	 */
	public Method() {
		this.name = null;
		this.accessLevel = null;
		this.desc = null;
		this.exceptions = null;
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
	public EMethodAccess getAccessLevel() {
		return this.accessLevel;
	}

	@Override
	public void setAccessLevel(EMethodAccess access) {
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

}
