package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor{
	
	private IClass currentClass;
	private String[] classes;

	public ClassFieldVisitor(int api){
		super(api);
	}
	
	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass currentClass, String[] args) {
		super(api, decorated);
		this.currentClass = currentClass;
		this.classes = args;
	}
	
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		// WARNING: delete this line *waiting*
		//System.out.println("	"+type+" "+ name);
		// DONE: add this field to your internal representation of the current class.
		//System.out.println(name + "here is sig " + signature + "now for desc " + desc);
		String sigType = "";
		if (signature != null){
			sigType = Type.getType(signature).getClassName();
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
					System.out.println("hit");
					IArrow arrow = new ArrowHas();
					arrow.setFromObject(currentClass.getClassName());
					arrow.setToObject(field);
					currentClass.addArrow(arrow);
				}
			}
			else {
				if (className.replace(".", "").equals(type.replace(".", ""))){
					System.out.println("hit");
					IArrow arrow = new ArrowHas();
					arrow.setFromObject(currentClass.getClassName());
					arrow.setToObject(type);
					currentClass.addArrow(arrow);
				}
			}
		}
		currentClass.addIField(currentField);
		
		// What is a good way to know what the current class is?
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
