package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

public class myMethodVisitor extends MethodVisitor {
	private IClass currentClass;
	private String[] classes;

	public myMethodVisitor(int api, MethodVisitor mv, IClass currentClass, String[] classes) {
		super(api, mv);
		this.currentClass = currentClass;
		this.classes = classes;
		
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		for (String Class : this.classes){
			String ClassName = Class.replace(".", "");
			String descName = desc.substring(1).replace("/", "").replace(";", "");
			if (ClassName.equals(descName)){
				IArrow arrow = new ArrowHas();
				arrow.setFromObject(currentClass.getClassName());
				arrow.setToObject(desc.replace(";", "").substring(1));
				currentClass.addArrow(arrow);
			}
		}

	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		for (String Class : this.classes){
			String ClassName = Class.replace(".", "");
			String ownerName = owner.replace("/", "");
			if (ClassName.equals(ownerName)){
				if (ClassName.equals(currentClass.getClassName().replace("/", ""))){
					return;
				}
				IArrow arrow = new ArrowUses();
				arrow.setFromObject(currentClass.getClassName());
				arrow.setToObject(owner);
				currentClass.addArrow(arrow);
			}
		}
	}

	
	

	

}
