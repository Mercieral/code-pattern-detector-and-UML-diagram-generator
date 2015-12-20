package problem.asm;

/**
 * Used for keeping easier track of values belonging to Method values
 * 
 * @author gateslm
 *
 */
public enum EMethodAccess implements IAccessFlags {

	ACC_PUBLIC(0x0001), ACC_PRIVATE(0x0002), ACC_PROTECTED(0x0004), ACC_STATIC(
			0x0008), ACC_FINAL(0x0010), ACC_SYNCHRONIZED(0x0020), ACC_BRIDGE(
					0x0040), ACC_VARARGS(0x0080), ACC_NATIVE(
							0x0100), ACC_ABSTRACT(0x0400), ACC_STRICT(
									0x0800), ACC_SYNTHETIC(0x1000);

	private int accessFlag;

	private EMethodAccess(int accessFlag) {
		this.accessFlag = accessFlag;
	}

	@Override
	public int getAccessFlags() {
		return this.accessFlag;
	}

}
