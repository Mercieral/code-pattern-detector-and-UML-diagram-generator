package problem.asm;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.Field;
import problem.javaClasses.HasRelation;

public class ClassFieldVisitor extends ClassVisitor{
	
	private IClass currentClass;
	private List<String> classes;
	private IModel model;

	public ClassFieldVisitor(int api){
		super(api);
	}
	
	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass currentClass, List<String> args, IModel m) {
		super(api, decorated);
		this.currentClass = currentClass;
		this.classes = args;
		this.model = m;
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		// WARNING: delete this line *waiting*
		// DONE: add this field to your internal representation of the current class.
		String sigType = "";
		if (signature != null){
			sigType = Type.getType(signature).getClassName().replace(">", "\\>" );
		}
		
		IField currentField = new Field();
		
		currentField.setName(name);
		currentField.setDesc(type);
		currentField.setSignature(sigType);
		currentField.setValue(value);
		addAccessLevel(access, currentField);
		for (String className : this.classes){
			if (signature != null) {
				String[] temp = sigType.split("<");
				String field = temp[1].substring(1).replace(".","");
				if (className.replace(".", "").equals(field)){
					IRelation relation = new HasRelation();
					relation.setFromObject(currentClass.getClassName());
					relation.setToObject(field.replace(".", ""));
					this.model.addRelation(relation);
				}
			}
			if (className.replace(".", "").equals(type.replace(".", ""))){
				IRelation relation = new HasRelation();
				relation.setFromObject(currentClass.getClassName());
				relation.setToObject(type.replace(".", ""));
				model.addRelation(relation);
			}
		}
		currentClass.addIField(currentField);
		
		return toDecorate;
	};
	
	/**
	 * Adds the access level to the current field
	 * 
	 * @param access - access integer given by asm
	 * @param currentField - field to add the access to
	 */
	void addAccessLevel(int access, IField currentField){
		String level="";
		
		//public
		if((access&Opcodes.ACC_PUBLIC)!=0){ level="+"; }
		
		//protected
		else if((access&Opcodes.ACC_PROTECTED)!=0){ level="#";} 
		
		//private
		else if((access&Opcodes.ACC_PRIVATE)!=0){ level="-";} 
		
		//default/package
		else{ level=""; }
		
		currentField.setAccessLevel(level);
	}

}
