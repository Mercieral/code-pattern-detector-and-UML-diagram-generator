package problem.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;

/**
 * Refer to:<br/>
 * ~ <a>https://en.wikipedia.org/wiki/Composite_pattern</a> <br/>
 * Rules: <br/>
 * ~ <strong>Component</strong>: <br/>
 * ~~Interface / Abstract Class <br/>
 * ~ <strong>Composite</strong>: <br/>
 * ~~ Extends component <br/>
 * ~~ List / Collection of components <br/>
 * ~~ Only Single has-a relation to component <br/>
 * ~~ Anything extending composite is a composite <br/>
 * ~ <strong>Leaf</strong> <br/>
 * ~~ Extends Component <br/>
 * ~~ No relations <br/>
 * 
 * @author gateslm
 *
 */
public class CompositeVisitor implements IInvoker {

	private IVisitor visitor;
	private List<IClass> interfaces;
	private Map<IClass, List<String>> abstractToInterface;
	private List<IClass> possibleComposites;
	private boolean notPossibleComposite;

	public CompositeVisitor() {
		this.visitor = new Visitor();
		this.interfaces = new ArrayList<>();
		this.abstractToInterface = new HashMap<>();
		this.possibleComposites = new ArrayList<>();
		this.preVisitModel();
		this.preVisitClass();
		this.visitField();
		this.visitMethods();
	}

	private void preVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class, (ITraverser t) -> {
			IModel m = (IModel) t;
			for (IClass c : m.getClasses()){
				if (c.getAcessLevel() == 1537){
					this.interfaces.add(c);
				}
				else if (c.getAcessLevel() == 1057) {
					if (c.getInterface().size() > 0){
						List<String> interfaces = new ArrayList<>();
						for (String interfaze : c.getInterface()){
							interfaces.add(interfaze);
						}
						this.abstractToInterface.put(c, interfaces);
					}
					else {
						List<String> noInterface = new ArrayList<>();
						this.abstractToInterface.put(c, noInterface);
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
					if (c.getInterface().size() == 0 && c.getExtension().equals(null)){
						this.notPossibleComposite = true;
					}
				});
	}
	

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField f = (IField) t;
			if (this.notPossibleComposite){
				return;
			}
			
			if (f.getSignature().equals("")){
				return;
			}
			System.out.println("sig " + f.getSignature());
			System.out.println("class name" + this.interfaces.get(0).getClassName().toString());
		});

	}
	
	private void visitMethods() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class,
				(ITraverser t) -> {
					if (this.notPossibleComposite){
						return;
					}
				});

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
}
