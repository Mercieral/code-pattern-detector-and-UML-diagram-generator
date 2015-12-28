package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor {
	private IClass currentClass;
	
	public ClassMethodVisitor(int api){
		super(api);
	}
	
	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass currentClass) {
		super(api, decorated);
		this.currentClass = currentClass;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		// TODO: delete this line
		//System.out.println("	method " + name);
		
		

		// TODO: create an internal representation of the current method and pass it to the methods below
		IMethod currentMethod = new Method();
		currentMethod.setName(name);
		currentMethod.setDesc(desc);
		currentMethod.setExceptions(exceptions);

		addAccessLevel(access, currentMethod);
		addReturnType(desc, currentMethod);
		addArguments(desc, currentMethod);
		
	    // TODO: add the current method to your internal representation of the current class
		this.currentClass.addIMethod(currentMethod);
		// What is a good way for the code to remember what the current class is?

		return toDecorate;
	}
	
	void addAccessLevel(int access, IMethod currentMethod){
		String level="";
		
		//public
		if((access&Opcodes.ACC_PUBLIC)!=0){ level="+"; }
		
		//protected
		else if((access&Opcodes.ACC_PROTECTED)!=0){ level="#";} 
		
		//private
		else if((access&Opcodes.ACC_PRIVATE)!=0){ level="-";} 
		
		//default/package
		else{ level=""; }
		// TODO: delete the next line
		//System.out.println("		access level: "+level);
		// TODO: ADD this information to your representation of the current method.
		
		currentMethod.setAccessLevel(level);
	}
	
	void addReturnType(String desc, IMethod currentMethod){
		String returnType = Type.getReturnType(desc).getClassName();
		// TODO: delete the next line
		//System.out.println("		return type: " + returnType);
		// TODO: ADD this information to your representation of the current method.
		currentMethod.setReturnType(returnType);
	}
	
	void addArguments(String desc, IMethod currentMethod){
		Type[] args = Type.getArgumentTypes(desc);
	    for(int i=0; i< args.length; i++){
	    	String arg=args[i].getClassName() + " arg" + Integer.toString(i);
	    	// TODO: delete the next line
	    	//System.out.println("		arg "+i+": "+arg);
	    	// TODO: ADD this information to your representation of the current method.
	    	currentMethod.addArgument(arg);
	    }
	}
}
