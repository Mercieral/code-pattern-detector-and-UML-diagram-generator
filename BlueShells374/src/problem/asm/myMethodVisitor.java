package problem.asm;

import org.objectweb.asm.MethodVisitor;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.javaClasses.ArrowHas;
import problem.javaClasses.ArrowUses;

public class myMethodVisitor extends MethodVisitor {
	private IClass currentClass;
	private String[] classes;
	private IMethod currentMethod;

	public myMethodVisitor(int api, MethodVisitor mv, IClass currentClass,
			String[] classes, IMethod currentMethod) {
		super(api, mv);
		this.currentClass = currentClass;
		this.classes = classes;
		this.currentMethod = currentMethod;

	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		System.out.println("-- visitMethodInsn --");
		System.out.println("Opcode: " + opcode + "  Owner: " + owner
				+ "  Name: " + name + "  Desc: " + desc + "  itf: " + itf);
		for (String Class : this.classes) {
			String ClassName = Class.replace(".", "");
			String ownerName = owner.replace("/", "");
			if (ClassName.equals(ownerName)) {
				if (ClassName
						.equals(currentClass.getClassName().replace("/", ""))) {
					return;
				}
				IArrow arrow = new ArrowUses();
				arrow.setFromObject(currentClass.getClassName());
				arrow.setToObject(owner);
				currentClass.addArrow(arrow);
			}
		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		System.out.println("-- visitFieldInsn --");
		System.out.println("Opcode: " + opcode + "  Owner: " + owner
				+ "  name: " + name + "  desc: " + desc);
		for (String Class : this.classes) {
			String ClassName = Class.replace(".", "");
			String descName = desc.substring(1).replace("/", "").replace(";",
					"");
			if (ClassName.equals(descName)) {
				IArrow arrow = new ArrowHas();
				arrow.setFromObject(currentClass.getClassName());
				arrow.setToObject(desc.replace(";", "").substring(1));
				currentClass.addArrow(arrow);
			}
		}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		// TODO Auto-generated method stub
		super.visitTypeInsn(opcode, type);
		System.out.println("-- visitTypeInsn --");
		System.out.println("Opcode: " + opcode + "  Type " + type);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		// TODO Auto-generated method stub
		super.visitVarInsn(opcode, var);
		System.out.println("-- visitVarInsn --");
		System.out.println("Opcode: " + opcode + "  Var: " + var);
	}

}
