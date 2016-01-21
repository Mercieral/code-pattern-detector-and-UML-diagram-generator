package problem.visitor;

import org.objectweb.asm.Type;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Field;
import problem.javaClasses.Method;
import problem.patterns.SingletonPattern;

public class SingletonVisitor implements IInvoker {
	private Visitor visitor;
	private IClass currentClass;
	private boolean hasFieldInstance;
	private boolean hasMethodInstance;
	
	public SingletonVisitor(){
		this.visitor = new Visitor();
		
		setupPreVisitClass();
		visitField();
		visitMethod();
		postVisitClass();
	}
	
	private void setupPreVisitClass(){
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class, (ITraverser t) ->{
			this.currentClass = (IClass) t;
			this.hasFieldInstance = false;
			this.hasMethodInstance = false;
		});
	}
	
	private void visitField(){
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			IField f = (IField) t;
			String desc = f.getDesc().replace(".", "/");
			if (desc.equals(currentClass.getClassName())){
				hasFieldInstance = true;
			}
		});
	}

	private void visitMethod(){
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			IMethod m = (IMethod) t;
			Type arg = Type.getReturnType(m.getDesc());
			String arg2 = arg.toString().substring(1).replace(";", "");
			if (arg2.equals(currentClass.getClassName())){
				hasMethodInstance = true;
			}
		});
	}
	
	private void postVisitClass(){
		this.visitor.addVisit(VisitType.PostVisit, ConcreteClass.class, (ITraverser t) -> {
			IClass c = (IClass) t;
			if (this.hasFieldInstance && this.hasMethodInstance){
				c.addPattern(new SingletonPattern(c.getClassName()));
			}
		});
	}
	
	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);		
	}
}
