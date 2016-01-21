package problem.visitor;

public class SingletonVisitor implements IPatternVisitor {
	private Visitor visitor;
	
	public SingletonVisitor(){
		this.visitor = new Visitor();
	}
}
