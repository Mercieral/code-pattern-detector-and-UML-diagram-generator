package problem.visitor;

import problem.interfaces.IModel;

/**
 * Refer to:<br/>
 * ~ <a>https://en.wikipedia.org/wiki/Composite_pattern</a> <br/>
 * Important Information: <br/>
 * ~ Composite has children component <br/>
 * ~ Component is an interface / abstract class <br/>
 * ~ Leaf just uses all of component's methods <br/>
 * ~ Composite methods to manipulate children <br/>
 * ~ Has-a relation between component and leaf / composite <br/>
 * ~ Composite has a collection of children <br/>
 * 
 * @author gateslm
 *
 */
public class ComponentVisitor implements IInvoker {

	private IVisitor visitor;

	// private List<IRelation> hasRelations; // According to Wikipedia

	public ComponentVisitor() {
		this.visitor = new Visitor();
		this.preVisitClasses();
		this.postVisitHasARelation();
		this.postVisitClassesFindLeaf();
		this.postVisitFindComposites();
	}

	/**
	 * Finds all the classes that are composite. Good luck
	 */
	private void postVisitFindComposites() {
		// TODO Auto-generated method stub

	}

	/**
	 * Finds the classes that should be the leaf nodes. FIXME: Not sure if it
	 * should be post visit
	 */
	private void postVisitClassesFindLeaf() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	/**
	 * Checks the relations between the
	 */
	private void postVisitHasARelation() {

	}

	/**
	 * Visits all the classes to find the Components of the Component Pattern.
	 */
	private void preVisitClasses() {

	}

}
