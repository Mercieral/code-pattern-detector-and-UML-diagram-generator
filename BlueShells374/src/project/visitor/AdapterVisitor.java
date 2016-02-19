package project.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.interfaces.IRelation;
import project.javaClasses.ConcreteClass;
import project.javaClasses.Method;
import project.patterns.AdapterPattern;

public class AdapterVisitor implements IPhase {

	private IVisitor visitor;
	private List<IClass> classList;
	private int maxMethods;
	private boolean posAdap;
	private Map<String[], Integer> adapteeCalls;
	
	public AdapterVisitor() {
		this.visitor = new Visitor();
		this.classList = new ArrayList<>();
		this.adapteeCalls = new HashMap<>();
		this.setupPreVisitClass();
		this.visitMethod();
		this.postVisitModel();

	}
	
	@Override
	public void execute(Config config, IModel model) {
		this.maxMethods = config.adapterMethodDelegation;
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					this.posAdap = false;
					if (c.getInterface().size() == 1) {
						// Detecting adapter
						if (c.getExtension().equals("java/lang/Object")
								|| c.getExtension().equals("")) {
							// Doesn't have an extension
							if (c.getIField().size() == 1) {
								// Only 1 field
								this.classList.add(c);
								// Adds it when all three parts exist
								this.posAdap = true;
							}
						}
					}
				});
	}

	private void visitMethod(){
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			if (!this.posAdap){
				return;
			}
			IMethod m = (IMethod) t;
			for (int i = 0; i < m.getInnerCalls().size(); i++){
				String[] temp = {m.getInnerCalls().get(i).getGoingFromClass(), m.getInnerCalls().get(i).getGoingToClass()};
				if (this.getFromMap(m.getInnerCalls().get(i).getGoingFromClass(), m.getInnerCalls().get(i).getGoingToClass()) == -1){
					this.adapteeCalls.put(temp, 1);
				}
				else {
					int calls = this.getFromMap(m.getInnerCalls().get(i).getGoingFromClass(), m.getInnerCalls().get(i).getGoingToClass());
					this.adapteeCalls.put(temp, calls++);

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
						if (adaptee != null && target != null){
							String adapteeName = adaptee.getClassName();
							String adapterName = c0.getClassName();						
							if (isAdaptee && isTarget && this.getFromMap(adapterName, adapteeName) >= this.maxMethods) {
								adaptee.addPattern(
										new AdapterPattern(adaptee.getClassName(),
												"\\<\\<adaptee\\>\\>"));
								target.addPattern(
										new AdapterPattern(target.getClassName(),
												"\\<\\<target\\>\\>"));
								c0.addPattern(new AdapterPattern(c0.getClassName(),
										"\\<\\<adapter\\>\\>"));
								for (IRelation r : m.getRelations()) {
									if (r.getFromObject()
											.equals(c0.getClassName().replace("/", ""))
											&& r.getToObject().equals(
													fieldType.replace(".", ""))) {
										r.addProperty("xlabel=\"\\<\\<adapts\\>\\>\"");
									}
								}
							}
						}
					}

				});
	}
	
	private int getFromMap(String from, String to){
		for (String[] a : this.adapteeCalls.keySet()){
			if (a[0].equals(from) && a[1].equals(to)){
				return this.adapteeCalls.get(a);
			}
		}
		return -1;
	}
}
