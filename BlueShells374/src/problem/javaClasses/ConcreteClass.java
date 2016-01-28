package problem.javaClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IPattern;
import problem.visitor.ITraverser;
import problem.visitor.IVisitor;

public class ConcreteClass implements IClass {
	private Collection<IMethod> methodList;
	private Collection<IField> fieldList;
	private int accessLevel;
	private String signature;
	private String name;
	private double version;
	private Collection<String> interfaceList;
	private String extension;
	private List<IPattern> patternList;

	/**
	 * Constructor for ConcreteClass
	 */
	public ConcreteClass(){
		this.methodList = new ArrayList<>();
		this.fieldList = new ArrayList<>();
		this.accessLevel = 0;
		this.signature = "";
		this.name = "";
		this.version = 0.00;
		this.interfaceList = new ArrayList<>();
		this.extension = "";
		this.patternList = new ArrayList<>();
	}
	
	@Override
	public Collection<IMethod> getIMethods() {
		return methodList;
	}

	@Override
	public Collection<IField> getIField() {
		return this.fieldList;
	}

	@Override
	public int getAcessLevel() {
		return this.accessLevel;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	@Override
	public String getClassName() {
		return this.name;
	}

	@Override
	public double getClassVersion() {
		return this.version;
	}

	@Override
	public Collection<String> getInterface() {
		return this.interfaceList;
	}

	@Override
	public String getExtension() {
		return this.extension;
	}
	
	@Override
	public void addIMethod(IMethod method) {
		this.methodList.add(method);
	}

	@Override
	public void addIField(IField field) {
		this.fieldList.add(field);
	}

	@Override
	public void setAccessLevel(int access) {
		this.accessLevel =  access;
		
	}

	@Override
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public void setClassName(String name) {
		this.name = name;
	}

	@Override
	public void setClassVersion(double version) {
		this.version = version;
	}

	@Override
	public void addInterface(String inter) {
		if (this.interfaceList.contains(inter)){
			return;
		}
		this.interfaceList.add(inter);
	}
	
	@Override 
	public void setExtension(String extension){
		this.extension = extension;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for (IField f : this.fieldList){
			ITraverser t = (ITraverser)f;
			t.accept(v);
		}
		v.visit(this);
		for (IMethod m : this.methodList){
			ITraverser t = (ITraverser)m;
			t.accept(v);
		}
		v.postVisit(this);
		
	}
	
	public void addPattern(IPattern pattern){
		this.patternList.add(pattern);
	}
	
	public List<IPattern> getPatterns(){
		return this.patternList;
	}
}
