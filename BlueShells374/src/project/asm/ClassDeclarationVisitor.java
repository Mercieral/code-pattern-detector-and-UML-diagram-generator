package project.asm;

import java.util.List;

import org.objectweb.asm.ClassVisitor;

import project.interfaces.IClass;
import project.interfaces.IModel;
import project.interfaces.IRelation;
import project.javaClasses.ExtensionRelation;
import project.javaClasses.InterfaceRelation;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass currentClass;
	private List<String> classes;
	private IModel model;
	
	public ClassDeclarationVisitor(int api, IClass currentClass, List<String> args, IModel m){
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
