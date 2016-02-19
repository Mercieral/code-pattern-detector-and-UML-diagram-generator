package project.visitor;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.javaClasses.ConcreteClass;
import project.javaClasses.Field;
import project.javaClasses.Method;
import project.javaClasses.Model;
import project.patterns.SingletonPattern;

public class SingletonVisitor implements IPhase {
	private Visitor visitor;
	private IClass currentClass;
	private boolean hasFieldInstance;
	private boolean hasMethodInstance;
	private boolean requireGetInstance;
	private List<String> singletonList;
	
	public SingletonVisitor(){
		this.visitor = new Visitor();
		this.singletonList = new ArrayList<>();
		setupPreVisitClass();
		visitField();
		visitMethod();
		postVisitClass();
		postVisitModel();
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
			if (this.requireGetInstance && !m.getName().equals("getInstance")){
				hasMethodInstance = false;
			}
		});
	}
	
	private void postVisitClass(){
		this.visitor.addVisit(VisitType.PostVisit, ConcreteClass.class, (ITraverser t) -> {
			IClass c = (IClass) t;
			if (this.hasFieldInstance && this.hasMethodInstance){
				c.addPattern(new SingletonPattern(c.getClassName()));
				this.singletonList.add(c.getClassName());
			}
		});
	}
	
	private void postVisitModel(){
		this.visitor.addVisit(VisitType.PostVisit, Model.class, (ITraverser t) -> {
			IModel m = (IModel) t;
			
			for (String s : this.singletonList){
				for (IClass c : m.getClasses()){
					if (s.equals(c.getExtension())){
						c.addPattern(new SingletonPattern(c.getClassName()));
					}
				}
			}
		});
	}

	@Override
	public void execute(Config config, IModel model) {
		this.requireGetInstance = config.singletonGetInstance;
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);		
	}
}
