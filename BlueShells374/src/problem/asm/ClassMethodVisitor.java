package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.javaClasses.Method;

public class ClassMethodVisitor extends ClassVisitor {
	private IClass currentClass;
	private String[] classes;

	public ClassMethodVisitor(int api) {
		super(api);
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated,
			IClass currentClass, String[] classes) {
		super(api, decorated);
		this.currentClass = currentClass;
		this.classes = classes;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc,
				signature, exceptions);

		IMethod currentMethod = new Method();
		currentMethod.setName(name);
		currentMethod.setDesc(desc);
		currentMethod.setExceptions(exceptions);

		addAccessLevel(access, currentMethod);
		addReturnType(desc, signature, currentMethod);
		addArguments(desc, signature, currentMethod);

		this.currentClass.addIMethod(currentMethod);

		MethodVisitor mine = new myMethodVisitor(Opcodes.ASM5, toDecorate,
				currentClass, classes, currentMethod);

		return mine;
	}

	/**
	 * Adds the access level to the current method
	 * 
	 * @param access
	 *            - access integer given from asm
	 * @param currentMethod
	 *            - method to add the access level to
	 */
	void addAccessLevel(int access, IMethod currentMethod) {
		String level = "";

		// public
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+";
		}

		// protected
		else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "#";
		}

		// private
		else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "-";
		}

		// default/package
		else {
			level = "";
		}

		currentMethod.setAccessLevel(level);
	}

	/**
	 * Adds the return type to the current method
	 * 
	 * @param desc
	 *            - the desc given from asm
	 * @param signature
	 * @param currentMethod
	 *            - current method to add the return type to
	 */
	void addReturnType(String desc, String signature, IMethod currentMethod) {
		String returnType = Type.getReturnType(desc).getClassName();
		currentMethod.setReturnType(returnType);
	}

	/**
	 * Adds the arguments to the current method
	 * 
	 * @param desc
	 *            - The desc given from asm
	 * @param signature
	 * @param currentMethod
	 *            - current method to add the arguments to
	 */
	void addArguments(String desc, String signature, IMethod currentMethod) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			currentMethod.addArgument(arg);
		}
	}
}
