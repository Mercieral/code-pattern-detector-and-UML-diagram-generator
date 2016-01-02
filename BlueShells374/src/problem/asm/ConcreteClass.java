package problem.asm;

import java.util.ArrayList;
import java.util.Collection;

public class ConcreteClass implements IClass {
	private Collection<IMethod> methodList;
	private Collection<IField> fieldList;
	private int accessLevel;
	private String signature;
	private String name;
	private double version;
	private IClass superClass;
	private Collection<String> interfaceList;
	private String extension;

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
		this.superClass = null;
		this.interfaceList = new ArrayList<>();
		this.extension = "";
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
	public IClass getSuperName() {
		return this.superClass;
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
	public void setSuperName(IClass name) {
		this.superClass = name;
	}

	@Override
	public void addInterface(String inter) {
		this.interfaceList.add(inter);
	}
	
	@Override 
	public void setExtension(String extension){
		this.extension = extension;
	}
}
