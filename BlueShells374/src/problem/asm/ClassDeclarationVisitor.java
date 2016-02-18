package problem.asm;

import org.objectweb.asm.ClassVisitor;

import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.InterfaceRelation;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass currentClass;
	private String[] classes;
	private IModel model;
	
	public ClassDeclarationVisitor(int api, IClass currentClass, String[] args, IModel m){
		super(api);
		this.currentClass = currentClass;
		this.classes = args;
		this.model = m;
		
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		// HELP: delete the line below
		// HELP: construct an internal representation of the class for later use by decorators
		currentClass.setClassName(name);
		currentClass.setAccessLevel(access);
		currentClass.setExtension(superName);
		if (superName != null){
			for (String className : this.classes){
				 if (className.replace(".", "").equals(superName.replace("/", ""))){
					IRelation relation = new ExtensionRelation();
					relation.setFromObject(currentClass.getClassName());
					relation.setToObject(superName);
					this.model.addRelation(relation);
					break;
				 }
			}
		}
		
		currentClass.setSignature(signature);
		currentClass.setClassVersion((double) version); 
		for(String inter : interfaces){
			for (String className : this.classes){
				if (className.replace(".", "").equals(inter.replace("/", ""))){
					currentClass.addInterface(inter);
					IRelation relation = new InterfaceRelation();
					relation.setFromObject(currentClass.getClassName());
					relation.setToObject(inter);
					this.model.addRelation(relation);
					break;
				}
			}

		}
		
		super.visit(version, access, name, signature, superName, interfaces);
		
	}

}
