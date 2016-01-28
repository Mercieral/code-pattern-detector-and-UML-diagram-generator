package problem.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.DecorateRelation;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.Field;
import problem.patterns.DecoratorPattern;

public class DecoratorVisitor implements IInvoker {
	private Visitor visitor;	
	private List<String> decoratorList;
	private List<String> concreteDecorators;
	private Collection<String> tempInterfaces;
	private List<String> componentList;
	private String tempExtension;
	private IClass tempClass;
	private boolean notAbstract;
	private boolean isDecorator;
	
	public DecoratorVisitor() {
		this.visitor = new Visitor();
		this.decoratorList = new ArrayList<>();
		this.concreteDecorators = new ArrayList<>();
		this.componentList = new ArrayList<>();
		
		this.setupPreVisitClass();
		this.visitField();
		this.visitExtensionRelation();
		this.postVisitModel();
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
	
	private void setupPreVisitClass(){ //search for a class that could potentially be the decorator and add it to a list
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class, (ITraverser t) ->{
			this.tempClass = (IClass) t;
			this.tempInterfaces = new ArrayList<>();
			this.tempExtension = "";
			
			this.notAbstract = false;
			this.isDecorator = false;
//			if (this.tempClass.getAcessLevel() != 1057){ //if the class is not abstract it cannot be a decorator
//				this.notAbstract = true;
//				return;
//			}
			
			this.tempInterfaces = this.tempClass.getInterface();
			this.tempExtension = this.tempClass.getExtension();
		});
	}
	
	private void visitField(){
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) ->{
			if (this.notAbstract) //if already determined to be a decorator or not return to avoid wasted computation
				return;
			
			IField f = (IField) t;
			String desc = f.getDesc().replace(".", "/");
			if (desc.equals("java/lang/Object")){
				return;
			}
			
			if (desc.equals(this.tempExtension) || this.tempInterfaces.contains(desc)){
				this.componentList.add(desc);
				if (!this.isDecorator){
					this.decoratorList.add(this.tempClass.getClassName());
					this.tempClass.addPattern(new DecoratorPattern(this.tempClass.getClassName(), "\\<\\<decorator\\>\\>"));
					this.isDecorator = true;
				}
			}
		});
	}
	
	private void visitExtensionRelation(){
		this.visitor.addVisit(VisitType.Visit, ExtensionRelation.class, (ITraverser t) -> {
			IRelation ext = (IRelation) t;
			for (int i = 0; i < this.decoratorList.size(); i++){
				String decorator = this.decoratorList.get(i);
				decorator = decorator.replace("/", "");
				if (ext.getToObject().equals(decorator)){ //once the concrete decorator is found add its name to a list of concrete decorators
					this.concreteDecorators.add(ext.getFromObject());
					break;
				}
			}
		});
	}
	
	private void postVisitModel(){
		this.visitor.addVisit(VisitType.PostVisit, IModel.class, (ITraverser t) -> {
			IModel m = (IModel) t;
			
			List<IClass> classList = m.getClasses();
			for (String tempComponent : this.componentList){ //find all component classes and add a decorator pattern object to it
				for (IClass tempClass : classList){
					if (tempClass.getClassName().equals(tempComponent)){
						tempClass.addPattern(new DecoratorPattern(tempClass.getClassName(), "\\<\\<component\\>\\>"));
					}
				}
			}
			
			for (String tempConcrete : this.concreteDecorators){ //find all concrete decorators and add a decorator pattern object
				for (IClass tempClass : classList){
					if (tempClass.getClassName().replace("/", "").equals(tempConcrete)){
						System.out.println("concrete" + tempConcrete);
						System.out.println("class" + tempClass.getClassName());
						tempClass.addPattern(new DecoratorPattern(tempClass.getClassName(), "\\<\\<decorator\\>\\>"));
					}
				}
			}
			
			for (int i = 0; i < this.decoratorList.size(); i++){
				IRelation decorator = new DecorateRelation();
				decorator.setFromObject(this.decoratorList.get(i));
				decorator.setToObject(this.componentList.get(i));
				m.addRelation(decorator);
			}
		});
	}
}
