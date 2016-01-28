package problem.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.MethodContainer;

public class AdapterVisitor implements IInvoker {

	private IVisitor visitor;
	private Map<String, Boolean> fieldMap;
	private List<IClass> classList;

	public AdapterVisitor() {
		this.visitor = new Visitor();
		this.fieldMap = new HashMap<>();

		this.setupPreVisitModel();
		this.setupPreVisitClass();
		this.visitField();
		this.visitMethod();
		this.visitExtensionRelation();
		this.visitHasRelation();
		this.postVisitModel();

	}

	private void setupPreVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					this.classList = m.getClasses();
				});
	}

	private void visitHasRelation() {

	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {

				});
	}

	private void visitMethod() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class,
				(ITraverser t) -> {
					IMethod m = (IMethod) t;
					boolean inMethod = false;
					boolean inClass = false;
					for(IClass c: this.classList){
						if(c.getIMethods().contains(m)){
							
						}
					}
					
					
					if (true) { // FIXME: Check if method is in interface
						for (String field : this.fieldMap.keySet()) {
							inMethod = false;
							for (MethodContainer mc : m.getInnerCalls()) {
								if (mc.getGoingFromClass().equals(field)) {
									inMethod = true;
								}
							}
							if (this.fieldMap.get(field) && !inMethod) {
								this.fieldMap.replace(field, inMethod);
							}
						}
					}

				});
	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField f = (IField) t;
			this.fieldMap.put(f.getName(), true);
		});
	}

	private void visitExtensionRelation() {
		this.visitor.addVisit(VisitType.Visit, ExtensionRelation.class,
				(ITraverser t) -> {

				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {

				});
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

}
