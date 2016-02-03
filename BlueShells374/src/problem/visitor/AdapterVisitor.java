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
					// FIXME
				});
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					if (c.getInterface().size() == 1) {
						// Detecting adapter
						if (c.getExtension().equals("java/lang/Object")
								|| c.getExtension().equals("")) {
							// Doesn't have an extension
							if (c.getIField().size() == 1) {
								// Only 1 field
								this.classList.add(c);
								// Adds it when all three parts exist
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
						boolean isAdaptee = false;
						IClass adaptee = null;
						boolean isTarget = false;
						IClass target = null;
						String fieldType = ((List<IField>) c0.getIField())
								.get(0).getDesc();
						String interfaceName = ((List<String>) c0
								.getInterface()).get(0);
						for (IClass c1 : m.getClasses()) {
							if (c1.getClassName().equals(fieldType)
									|| c1.getClassName().replace("/", ".")
											.equals(fieldType)) {
								isAdaptee = true;
								adaptee = c1;
								continue; // If this one, not the interface
							}
							if (c1.getClassName().equals(interfaceName)) {
								isTarget = true;
								target = c1;
							}
							if (target != null && adaptee != null) {
								break;
							}
						}
						if (isAdaptee && isTarget) {
							adaptee.addPattern(
									new AdapterPattern(adaptee.getClassName(),
											"\\<\\<adaptee\\>\\>"));
							target.addPattern(
									new AdapterPattern(target.getClassName(),
											"\\<\\<target\\>\\>"));
							c0.addPattern(new AdapterPattern(c0.getClassName(),
									"\\<\\<adapter\\>\\>"));
						}
						for (IRelation r : m.getRelations()) {
							if (r.getFromObject()
									.equals(c0.getClassName().replace("/", ""))
									&& r.getToObject().equals(
											fieldType.replace(".", ""))) {
								r.addProperty("xlabel=\"\\<\\<adapts\\>\\>\"");
							}
						}
					}

				});
	}
}
