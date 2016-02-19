package project.javaClasses;

import java.util.ArrayList;
import java.util.List;

import project.interfaces.IRelation;
import project.visitor.IVisitor;

public class ExtensionRelation implements IRelation {

	private String start;

	private String end;

	private String details;

	private final String ARROW = " -> ";
	
	private List<String> labels;

	/**
	 * Arrow to connect classes where one is a superclass
	 */
	public ExtensionRelation() {
		this.start = "";
		this.end = "";
		this.labels = new ArrayList<>();
	}

	@Override
	public String drawRelation() {
		StringBuilder labels = new StringBuilder();
		labels.append("");
		for (int i = 0; i < this.labels.size(); i++){
			labels.append(this.labels.get(i));
		}
		this.details = "\n\t\t[arrowhead=\"onormal\", " + labels.toString() + "];\n";
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(this.start);
		sb.append(ARROW);
		sb.append(this.end);
		sb.append(this.details);
		return sb.toString().replace("$", "");
	}
	
	@Override
	public boolean equals(Object obj) {
		IRelation inObj = (IRelation)obj;
		if(!start.equals(inObj.getFromObject()) || !end.equals(inObj.getToObject()) || !this.drawRelation().equals(inObj.drawRelation())){
			return false;
		}
		return true;
	}


	@Override
	public void setFromObject(String startObject) {
		this.start = startObject.replace("/", "");
	}

	@Override
	public void setToObject(String endObject) {
		this.end = endObject.replace("/", "");
	}

	@Override
	public String getFromObject() {
		return this.start;
	}

	@Override
	public String getToObject() {
		return this.end;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public void addProperty(String label) {
		this.labels.add(label);
	}

}
