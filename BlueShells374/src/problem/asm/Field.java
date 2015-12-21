package problem.asm;

/**
 * TODO
 * 
 * @author gateslm
 *
 */
public class Field implements IField {
	
	private String name;
	
	private String desc;
	
	private String signature;
	
	private Object value;
	
	private EFieldAccess accessLevel;
	
	/**
	 * TODO
	 */
	public Field() {
		this.name = null;
		this.desc = null;
		this.signature = null;
		this.value = null;
		this.accessLevel = null;
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
	public String getDesc() {
		return this.desc;
	}

	@Override
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	@Override
	public void setSignature(String sign) {
		this.signature = sign;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public EFieldAccess getAccessLevel() {
		return this.accessLevel;
	}

	@Override
	public void setAccessLevel(EFieldAccess access) {
		this.accessLevel = access;
	}

}
