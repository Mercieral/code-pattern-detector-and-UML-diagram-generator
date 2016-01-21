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

public class SingletonVisitor implements IPatternVisitor {
	private Visitor visitor;
	private IClass currentClass;
	private boolean hasSingleton;
	
	public SingletonVisitor(){
		this.visitor = new Visitor();
		this.hasSingleton = false;
		
		setupPreVisitClass();
		visitField();
		visitMethod();
	}
	
	private void setupPreVisitClass(){
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class, (ITraverser t) ->{
			this.currentClass = (IClass) t;
		});
	}
	
	private void visitField(){
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			IField f = (IField) t;
			if (f.getDesc().equals(currentClass.getClassName())){
				currentClass.addPattern(new SingletonPattern(currentClass.getClassName()));
				hasSingleton = true;
			}
		});
	}

	private void visitMethod(){
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			if (hasSingleton){
				return;
			}
			
			IMethod m = (IMethod) t;
			Type arg = Type.getReturnType(m.getDesc());
			if (arg.toString().equals(currentClass.getClassName())){
				currentClass.addPattern(new SingletonPattern(currentClass.getClassName()));
			}
		});
	}
	
	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);		
	}
}
