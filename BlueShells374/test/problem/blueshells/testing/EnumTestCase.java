package problem.blueshells.testing;

import static org.junit.Assert.*;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.EClassAccess;
import problem.asm.EFieldAccess;
import problem.asm.EMethodAccess;
import problem.asm.EParameterAccess;

/**
 * Used to test the Enum classes {@link EClassAccess}, {@link EFieldAccess},
 * {@link EMethodAccess}, {@link EParameterAccess} are all working with respect
 * to the {@link Opcodes} from ASM
 * 
 * @author gateslm
 *
 */
public class EnumTestCase {

	@Test
	public void compareClassAccessValues() {
		assertEquals(EClassAccess.ACC_PUBLIC.getAccessFlags(),
				Opcodes.ACC_PUBLIC);
		assertEquals(EClassAccess.ACC_PRIVATE.getAccessFlags(),
				Opcodes.ACC_PRIVATE);
		assertEquals(EClassAccess.ACC_PROTECTED.getAccessFlags(),
				Opcodes.ACC_PROTECTED);
		assertEquals(EClassAccess.ACC_FINAL.getAccessFlags(),
				Opcodes.ACC_FINAL);
		assertEquals(EClassAccess.ACC_SUPER.getAccessFlags(),
				Opcodes.ACC_SUPER);
		assertEquals(EClassAccess.ACC_INTERFACE.getAccessFlags(),
				Opcodes.ACC_INTERFACE);
		assertEquals(EClassAccess.ACC_ABSTRACT.getAccessFlags(),
				Opcodes.ACC_ABSTRACT);
		assertEquals(EClassAccess.ACC_SYNTHETIC.getAccessFlags(),
				Opcodes.ACC_SYNTHETIC);
		assertEquals(EClassAccess.ACC_ANNOTATION.getAccessFlags(),
				Opcodes.ACC_ANNOTATION);
		assertEquals(EClassAccess.ACC_ENUM.getAccessFlags(), Opcodes.ACC_ENUM);
	}

	@Test
	public void compareFieldAccessValues() {
		assertEquals(EFieldAccess.ACC_PUBLIC.getAccessFlags(),
				Opcodes.ACC_PUBLIC);
		assertEquals(EFieldAccess.ACC_PRIVATE.getAccessFlags(),
				Opcodes.ACC_PRIVATE);
		assertEquals(EFieldAccess.ACC_PROTECTED.getAccessFlags(),
				Opcodes.ACC_PROTECTED);
		assertEquals(EFieldAccess.ACC_STATIC.getAccessFlags(),
				Opcodes.ACC_STATIC);
		assertEquals(EFieldAccess.ACC_FINAL.getAccessFlags(),
				Opcodes.ACC_FINAL);
		assertEquals(EFieldAccess.ACC_VOLATILE.getAccessFlags(),
				Opcodes.ACC_VOLATILE);
		assertEquals(EFieldAccess.ACC_TRANSIENT.getAccessFlags(),
				Opcodes.ACC_TRANSIENT);
		assertEquals(EFieldAccess.ACC_SYNTHETIC.getAccessFlags(),
				Opcodes.ACC_SYNTHETIC);
		assertEquals(EFieldAccess.ACC_ENUM.getAccessFlags(), Opcodes.ACC_ENUM);
	}

	@Test
	public void compareMethodAccessValues() {
		assertEquals(EMethodAccess.ACC_PUBLIC.getAccessFlags(),
				Opcodes.ACC_PUBLIC);
		assertEquals(EMethodAccess.ACC_PRIVATE.getAccessFlags(),
				Opcodes.ACC_PRIVATE);
		assertEquals(EMethodAccess.ACC_PROTECTED.getAccessFlags(),
				Opcodes.ACC_PROTECTED);
		assertEquals(EMethodAccess.ACC_STATIC.getAccessFlags(),
				Opcodes.ACC_STATIC);
		assertEquals(EMethodAccess.ACC_FINAL.getAccessFlags(),
				Opcodes.ACC_FINAL);
		assertEquals(EMethodAccess.ACC_SYNCHRONIZED.getAccessFlags(),
				Opcodes.ACC_SYNCHRONIZED);
		assertEquals(EMethodAccess.ACC_BRIDGE.getAccessFlags(),
				Opcodes.ACC_BRIDGE);
		assertEquals(EMethodAccess.ACC_VARARGS.getAccessFlags(),
				Opcodes.ACC_VARARGS);
		assertEquals(EMethodAccess.ACC_NATIVE.getAccessFlags(),
				Opcodes.ACC_NATIVE);
		assertEquals(EMethodAccess.ACC_ABSTRACT.getAccessFlags(),
				Opcodes.ACC_ABSTRACT);
		assertEquals(EMethodAccess.ACC_STRICT.getAccessFlags(),
				Opcodes.ACC_STRICT);
		assertEquals(EMethodAccess.ACC_SYNTHETIC.getAccessFlags(),
				Opcodes.ACC_SYNTHETIC);
	}

	@Test
	public void compareParameterAccessValues() {
		assertEquals(EParameterAccess.ACC_MANDATED.getAccessFlags(),
				Opcodes.ACC_MANDATED);
		assertEquals(EParameterAccess.ACC_FINAL.getAccessFlags(),
				Opcodes.ACC_FINAL);
		assertEquals(EParameterAccess.ACC_SYNTHETIC.getAccessFlags(),
				Opcodes.ACC_SYNTHETIC);
	}

}
