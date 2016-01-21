package problem.asm;

import org.objectweb.asm.MethodVisitor;

import problem.interfaces.IClass;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.MethodContainer;
import problem.javaClasses.UsesRelation;

public class MyMethodVisitor extends MethodVisitor {
	private IClass currentClass;
	private String[] classes;
	private IMethod currentMethod;
	private IModel model;

	public MyMethodVisitor(int api, MethodVisitor mv, IClass currentClass,
			String[] classes, IMethod currentMethod, IModel m) {
		super(api, mv);
		this.currentClass = currentClass;
		this.classes = classes;
		this.currentMethod = currentMethod;
		this.model = m;

	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		for (String Class : this.classes) {
			String ClassName = Class.replace(".", "");
			String ownerName = owner.replace("/", "");
			if (ClassName.equals(ownerName)) {
				if (ClassName
						.equals(currentClass.getClassName().replace("/", ""))) {
					MethodContainer innerCall = new MethodContainer();
					innerCall.setInstantiation(false);
					innerCall.setGoingFromClass(this.currentClass.getClassName());
					innerCall.setGoingToClass(owner);
					innerCall.setGoingToMethod(name);
					innerCall.setDesc(desc);
					this.currentMethod.addInnerCall(innerCall);
					return;
				}
				IRelation relation = new UsesRelation();
				relation.setFromObject(currentClass.getClassName());
				relation.setToObject(owner);
				model.addRelation(relation);
//				IArrow arrow = new ArrowUses();
//				arrow.setFromObject(currentClass.getClassName());
//				arrow.setToObject(owner);
//				currentClass.addArrow(arrow);
			}
		}
		
		MethodContainer innerCall = new MethodContainer();
		innerCall.setInstantiation(false);
		innerCall.setGoingFromClass(this.currentClass.getClassName());
		innerCall.setGoingToClass(owner);
		innerCall.setGoingToMethod(name);
		innerCall.setDesc(desc);
		this.currentMethod.addInnerCall(innerCall);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
//		System.out.println("--FieldInsn--");
//		System.out.println(owner + name + desc);
		for (String Class : this.classes) {
			String ClassName = Class.replace(".", "");
			String descName = desc.substring(1).replace("/", "").replace(";",
					"");
			if (ClassName.equals(descName)) {
//				IRelation relation = new HasRelation();
//				relation.setToObject(currentClass.getClassName());
//				relation.setFromObject(desc.replace(";", "").substring(1));
//				model.addRelation(relation);
//				IArrow arrow = new ArrowHas();
//				arrow.setFromObject(currentClass.getClassName());
//				arrow.setToObject(desc.replace(";", "").substring(1));
//				currentClass.addArrow(arrow);
			}
		}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		
		MethodContainer innerCall = new MethodContainer();
		innerCall.setInstantiation(true);
		innerCall.setGoingFromClass(this.currentClass.getClassName());
		innerCall.setGoingToClass(type);
		this.currentMethod.addInnerCall(innerCall);
		
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		//System.out.println("-- visitVarInsn --");
		//System.out.println("Opcode: " + opcode + "  Var: " + var);
	}

}
