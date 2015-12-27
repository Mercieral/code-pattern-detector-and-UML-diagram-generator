package problem.asm;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass currentClass;
	
	public ClassDeclarationVisitor(int api, IClass currentClass){
		super(api);
		this.currentClass = currentClass;
		
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		// TODO: delete the line below
		System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		// TODO: construct an internal representation of the class for later use by decorators
		currentClass.setClassName(name);
		currentClass.setAccessLevel(access);
		currentClass.setExtension(superName);
		System.out.println("----- " + superName);
		currentClass.setSignature(signature);
		currentClass.setClassVersion((double) version); 
		for(String inter : interfaces){
			currentClass.addInterface(inter);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
		
	}

}
