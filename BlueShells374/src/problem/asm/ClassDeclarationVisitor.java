package problem.asm;

import org.objectweb.asm.ClassVisitor;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ArrowExtension;
import problem.javaClasses.ArrowInterface;
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
		//System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
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
//					IArrow arrow1 = new ArrowExtension();
//					arrow1.setFromObject(currentClass.getClassName());
//					arrow1.setToObject(superName);
//					currentClass.addArrow(arrow1);
					break;
				 }
			}
		}
		
		//System.out.println("----- " + superName);
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
//					IArrow arrow2 = new ArrowInterface();
//					arrow2.setFromObject(currentClass.getClassName());
//					arrow2.setToObject(inter);
//					currentClass.addArrow(arrow2);
					break;
				}
			}

		}
		
		super.visit(version, access, name, signature, superName, interfaces);
		
	}

}
