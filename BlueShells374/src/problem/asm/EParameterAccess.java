package problem.asm;

/**
 * Used for keeping easier track of values belonging to Parameter values
 * 
 * @author gateslm
 *
 */
public enum EParameterAccess implements IAccessFlags {

	ACC_MANDATED(0x8000), ACC_FINAL(0x0010), ACC_SYNTHETIC(0x1000);

	private int accessFlags;

	private EParameterAccess(int accessFlags) {
		this.accessFlags = accessFlags;
	}

	@Override
	public int getAccessFlags() {
		return this.accessFlags;
	}

}
