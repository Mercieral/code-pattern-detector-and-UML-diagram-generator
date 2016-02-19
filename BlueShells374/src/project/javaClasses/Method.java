package project.javaClasses;

import java.util.ArrayList;
import java.util.List;

import project.interfaces.IMethod;
import project.visitor.IVisitor;


/**
 * Concrete implementation of {@link IMethod} object
 * 
 * @author gateslm
 *
 */
public class Method implements IMethod {

	private String name;

	private String accessLevel;

	private String desc;

	private String[] exceptions;
	
	private String returnType;
	
	private List<String> arguments;
	
	private List<MethodContainer> innerCalls;

	/**
	 * Constructor to make a {@link IMethod} concrete object
	 */
	public Method() {
		this.name = null;
		this.accessLevel = null;
		this.desc = null;
		this.exceptions = null;
		this.returnType = null;
		this.arguments = null;
		this.arguments = new ArrayList<String>();
		this.innerCalls = new ArrayList<MethodContainer>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAccessLevel() {
		return this.accessLevel;
	}

	@Override
	public void setAccessLevel(String access) {
		this.accessLevel = access;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String[] getExceptions() {
		return this.exceptions;
	}

	@Override
	public void setExceptions(String[] exceptions) {
		this.exceptions = exceptions;
	}
	
	@Override
	public String getReturnType() {
		return this.returnType;
	}
	
	@Override
	public void setReturnType(String type){
		this.returnType = type;
	}

	@Override
	public void addArgument(String arg) {
		this.arguments.add(arg);
	}

	@Override
	public List<String> getArguments() {
		return this.arguments;
	}
	
	@Override
	public List<MethodContainer> getInnerCalls(){
		return this.innerCalls;
	}

	@Override
	public void addInnerCall(MethodContainer innerCall){
		this.innerCalls.add(innerCall);
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}
}
