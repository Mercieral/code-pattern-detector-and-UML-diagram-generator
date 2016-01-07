package problem.asm;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass currentClass;
	
	public ClassDeclarationVisitor(int api, IClass currentClass){
		super(api);
		this.currentClass = currentClass;
		
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		// HELP: delete the line below
		//System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		// HELP: construct an internal representation of the class for later use by decorators
		currentClass.setClassName(name);
		currentClass.setAccessLevel(access);
		currentClass.setExtension(superName);
		if (superName != null){
			IArrow arrow1 = new ArrowExtension();
			arrow1.setFromObject(currentClass.getClassName());
			arrow1.setToObject(superName);
			currentClass.addArrow(arrow1);
		}
		
		//System.out.println("----- " + superName);
		currentClass.setSignature(signature);
		currentClass.setClassVersion((double) version); 
		for(String inter : interfaces){
			currentClass.addInterface(inter);
			IArrow arrow2 = new ArrowInterface();
			arrow2.setFromObject(currentClass.getClassName());
			arrow2.setToObject(inter);
			currentClass.addArrow(arrow2);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
		
	}

}
