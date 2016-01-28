package problem.visitor;

import java.util.ArrayList;
import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.patterns.AdapterPattern;

public class AdapterVisitor implements IInvoker {

	private IVisitor visitor;
	private List<IClass> classList;

	public AdapterVisitor() {
		this.visitor = new Visitor();
		this.classList = new ArrayList<>();
		this.setupPreVisitClass();
		this.visitHasRelation();
		this.postVisitModel();

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void visitHasRelation() {
		this.visitor.addVisit(VisitType.Visit, ConcreteClass.class,
				(ITraverser t) -> {
					
				});
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					if (c.getInterface().size() == 1) {
						// Detecting adapter
						if (c.getExtension().equals("java/lang/Object")) {
							// Doesn't have an extension
							if (c.getIField().size() == 1) {
								// Only 1 field
								this.classList.add(c);
								c.addPattern(
										new AdapterPattern(c.getClassName(),
												"\\<\\<adapter\\>\\>"));
							}
						}
					}
				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					for (IClass c0 : this.classList) {
						String fieldType = ((List<IField>) c0.getIField())
								.get(0).getDesc();
						String interfaceName = ((List<String>) c0
								.getInterface()).get(0);
						for (IClass c1 : m.getClasses()) {
							if (c1.getClassName().equals(fieldType)
									|| c1.getClassName().replace("/", ".")
											.equals(fieldType)) {
								c1.addPattern(
										new AdapterPattern(c1.getClassName(),
												"\\<\\<adaptee\\>\\>"));
								continue; // If this one, not the interface
							}
							if (c1.getClassName().equals(interfaceName)) {
								c1.addPattern(
										new AdapterPattern(c1.getClassName(),
												"\\<\\<target\\>\\>"));
							}
						}
						
						for (IRelation r : m.getRelations()){
							
						}
					}

				});
	}
}
