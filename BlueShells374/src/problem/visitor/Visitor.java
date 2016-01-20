package problem.visitor;

import java.util.HashMap;
import java.util.Map;

import problem.interfaces.IModel;
import problem.javaClasses.Model;



public class Visitor implements IVisitor {
	Map<LookupKey, IVisitMethod> keyToMethodMap;

	public Visitor(){
		this.keyToMethodMap = new HashMap<>();
	}
	
	@Override
	public void preVisit(ITraverser c) {
		LookupKey key = new LookupKey(VisitType.PreVisit, c.getClass());
		IVisitMethod m = this.keyToMethodMap.get(key);
		if(m != null)
			m.execute(c);
	}

	@Override
	public void visit(ITraverser c) {
		LookupKey key = new LookupKey(VisitType.Visit, c.getClass());
		IVisitMethod m = this.keyToMethodMap.get(key);
		if(m != null)
			m.execute(c);
	}

	@Override
	public void postVisit(ITraverser c) {
		LookupKey key = new LookupKey(VisitType.PostVisit, c.getClass());
		IVisitMethod m = this.keyToMethodMap.get(key);
		if(m != null)
			m.execute(c);
	}

	@Override
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToMethodMap.put(key, m);
	}

	@Override
	public void removeVisit(VisitType visitType, Class<?> clazz) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToMethodMap.remove(key);
	}

}
