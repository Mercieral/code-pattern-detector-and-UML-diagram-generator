package problem.visitor;

import java.util.ArrayList;
import java.util.List;

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
public class ComponentVisitor implements IInvoker {

	private IVisitor visitor;

	private List<IClass> possibleComponents;
	private List<IClass> possibleLeafComposite;

	public ComponentVisitor() {
		this.possibleComponents = new ArrayList<>();
		this.possibleLeafComposite = new ArrayList<>();
		this.visitor = new Visitor();
		this.preVisitClass();
		this.visitField();
		this.visitMethods();
	}

	private void visitMethods() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class,
				(ITraverser t) -> {
					// TODO
				});

	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			// TODO
		});

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void preVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, IClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					
				});
	}

}
