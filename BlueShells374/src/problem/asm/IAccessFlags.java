package problem.asm;

import org.objectweb.asm.Opcodes;

public interface IAccessFlags {

	/**
	 * Gives the access flag value from the {@link Opcodes} class
	 * 
	 * @return
	 */
	public int getAccessFlags();
}
