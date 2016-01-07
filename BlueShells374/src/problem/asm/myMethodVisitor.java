package problem.asm;

import org.objectweb.asm.MethodVisitor;

public class myMethodVisitor extends MethodVisitor {
	private IClass currentClass;

	public myMethodVisitor(int api, MethodVisitor mv, IClass currentClass) {
		super(api, mv);
		this.currentClass = currentClass;
		
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {

		IArrow arrow = new ArrowHas();
		arrow.setFromObject(currentClass.getClassName());
		arrow.setToObject(desc);
		currentClass.addArrow(arrow);
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
	}
	
	

	

}
