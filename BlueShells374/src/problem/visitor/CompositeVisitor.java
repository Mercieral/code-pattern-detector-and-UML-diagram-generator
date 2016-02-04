package problem.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.patterns.CompositePattern;

public class CompositeVisitor implements IInvoker {

	private IVisitor visitor;
	private Map<String, String> abstractToInterface; // extension, interface
	private List<IClass> possibleComposites;
	private List<String> possibleComponents;
	private boolean notPossibleComposite;
	private IClass currentClass;

	public CompositeVisitor() {
		this.visitor = new Visitor();
		this.abstractToInterface = new HashMap<>();
		this.possibleComposites = new ArrayList<>();
		this.possibleComponents = new ArrayList<>();
		this.preVisitModel();
		this.preVisitClass();
		this.visitField();
		this.visitMethods();
		this.postVisitModel();
	}

	private void preVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					for (IClass c : m.getClasses()) {
						if (c.getAccessLevel() == 1057) {
							if (c.getInterface().size() == 1) {
								String inter = "";
								for (String interfaze : c.getInterface()) {
									inter = interfaze;
								}
								this.abstractToInterface.put(c.getClassName(),
										inter);
							} else {
								this.abstractToInterface.put(c.getClassName(),
										"");
							}
						}
					}
				});
	}

	private void preVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, IClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					this.notPossibleComposite = false;
					if (c.getInterface().size() == 0
							&& c.getExtension().equals(null)) {
						this.notPossibleComposite = true;
					}
					this.currentClass = c;
				});
	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField f = (IField) t;
			if (this.notPossibleComposite) {
				return;
			}
			if (f.getSignature().equals("")) {
				return;
			}

			String[] sig = f.getSignature().split("<");
			String sigCheck = sig[sig.length - 1];
			sigCheck = sigCheck.replace(".", "/");
			sigCheck = sigCheck.substring(1);

			for (String s : this.currentClass.getInterface()) {
				if (s.equals(sigCheck)) {
					this.possibleComposites.add(this.currentClass);
					this.possibleComponents.add(sigCheck);
				}
			}

			if (this.currentClass.getExtension().equals(sigCheck)) {
				this.possibleComposites.add(this.currentClass);
				this.possibleComponents.add(sigCheck);
			}

			if (this.abstractToInterface
					.containsKey(this.currentClass.getExtension())
					&& this.abstractToInterface.containsValue(sigCheck)) {
				this.possibleComposites.add(this.currentClass);
				this.possibleComponents.add(sigCheck);
			}
		});

	}

	private void visitMethods() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class,
				(ITraverser t) -> {
					if (this.notPossibleComposite) {
						return;
					}
				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					List<String> comfComp = new ArrayList<>();
					List<String> comfComposite = new ArrayList<>();
					for (IClass c : m.getClasses()) {
						for (String s : this.possibleComponents) {
							if (c.getInterface().size() > 1
									|| c.getAccessLevel() == 1057) {
								break;
							}
							List<String> classInterface = (List<String>) c
									.getInterface();
							int iSize = classInterface.size();
							String cExt = c.getExtension();
							if ((iSize != 0 && classInterface.get(0).equals(s))
									|| cExt.equals(s)
									|| (this.abstractToInterface
											.get(cExt) != null
											&& this.abstractToInterface
													.get(cExt).equals(s))) {
								// detect maybe leaf check fields
								boolean hasComField = false;
								for (IField f : c.getIField()) {
									if (hasComField) {
										break;
									}
									String sigCheck = f.getSignature();
									if (!sigCheck.equals("")) {
										String[] sig = f.getSignature()
												.split("<");
										sigCheck = sig[sig.length - 1];
										sigCheck = sigCheck.replace(".", "/");
										sigCheck = sigCheck.substring(1);
									}

									if (sigCheck.equals(s)) {
										hasComField = true;
									}
								}
								if (!hasComField) {
									if (!comfComp.contains(s)) {
										comfComp.add(s);
									}
									c.addPattern(new CompositePattern(
											c.getClassName(),
											"\\<\\<Leaf\\>\\>"));
								}
							}
						}
					}

					for (String s : comfComp) {
						for (IClass c : m.getClasses()) {
							if (c.getClassName().equals(s)) {
								c.addPattern(
										new CompositePattern(c.getClassName(),
												"\\<\\<Component\\>\\>"));
							}
							if (this.abstractToInterface
									.get(c.getClassName()) != null
									&& this.abstractToInterface
											.get(c.getClassName()).equals(s)) {
								c.addPattern(
										new CompositePattern(c.getClassName(),
												"\\<\\<Component\\>\\>"));
							}
						}
					}

					for (IClass s2 : this.possibleComposites) {
						List<IField> classFields = (List<IField>) s2
								.getIField();
						for (IField f : classFields) {
							String sigCheck = f.getSignature();
							if (!sigCheck.equals("")) {
								String[] sig = f.getSignature().split("<");
								sigCheck = sig[sig.length - 1];
								sigCheck = sigCheck.replace(".", "/");
								sigCheck = sigCheck.substring(1);
							}
							if (comfComp.contains(sigCheck)) {
								s2.addPattern(
										new CompositePattern(s2.getClassName(),
												"\\<\\<Composite\\>\\>"));
								comfComposite.add(s2.getClassName());
							}
						}
					}

					for (IClass c2 : m.getClasses()) {
						for (String s3 : comfComposite) {
							if (c2.getExtension().equals(s3)) {
								c2.addPattern(
										new CompositePattern(c2.getClassName(),
												"\\<\\<Composite\\>\\>"));
							}
						}
					}
				});
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
}
