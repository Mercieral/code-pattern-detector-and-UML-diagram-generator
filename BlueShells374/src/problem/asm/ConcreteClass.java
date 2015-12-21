package problem.asm;

import java.util.ArrayList;
import java.util.Collection;

public class ConcreteClass implements IClass {
	private Collection<IMethod> methodList;
	private Collection<IField> fieldList;
	private EClassAccess accessLevel;
	private String signature;
	private String name;
	private double version;
	private IClass superClass;
	private Collection<Interface> interfaceList;

	public ConcreteClass(){
		this.methodList = new ArrayList<>();
		this.fieldList = new ArrayList<>();
		this.accessLevel = null;
		this.signature = "";
		this.name = "";
		this.version = 0.00;
		this.superClass = null;
		this.interfaceList = new ArrayList<>();
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
	public EClassAccess getAcessLevel() {
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
	public Collection<Interface> getInterface() {
		return this.interfaceList;
	}

	@Override
	public void addIMethods(IMethod method) {
		this.methodList.add(method);
	}

	@Override
	public void addIField(IField field) {
		this.fieldList.add(field);
	}

	@Override
	public void setAccessLevel(EClassAccess access) {
		this.accessLevel = access;
		
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
	public void addInterface(Interface inter) {
		this.interfaceList.add(inter);
	}

}
