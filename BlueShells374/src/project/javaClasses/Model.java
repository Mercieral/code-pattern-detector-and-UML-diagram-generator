package project.javaClasses;

import java.util.ArrayList;
import java.util.List;

import project.interfaces.IClass;
import project.interfaces.IModel;
import project.interfaces.IRelation;
import project.visitor.ITraverser;
import project.visitor.IVisitor;

/**
 * {@link IModel} object used to create file for GraphViz tool
 * 
 * @author gateslm, daniellm, mercieal
 *
 */
public class Model implements IModel {

	private List<IClass> classes;
	private List<IRelation> relations;

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new ArrayList<IRelation>();
	}

	@Override
	public void addClass(IClass currentClass) {
		classes.add(currentClass);
	}

	@Override
	public List<IClass> getClasses() {
		return classes;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for(IClass c : this.classes){
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		
		for(IRelation r : this.relations){
			ITraverser t = (ITraverser) r;
			t.accept(v);
		}
		v.postVisit(this);
		
	}
	
	@Override
	public void addRelation(IRelation relation){
		this.relations.add(relation);
	}
	
	@Override
	public List<IRelation> getRelations(){
		return this.relations;
	}

}
