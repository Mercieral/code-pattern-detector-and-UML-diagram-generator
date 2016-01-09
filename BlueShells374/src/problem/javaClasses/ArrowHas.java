package problem.javaClasses;

import problem.interfaces.IArrow;

public class ArrowHas implements IArrow {
	
	private String start;

	private String end;

	private String details;

	private final String ARROW = " -> ";

	/**
	 * Arrow to represent to association
	 */
	public ArrowHas() {
		this.start = "";
		this.end = "";
		this.details = "\n\t\t[arrowhead=\"vee\"];\n";
	}

	@Override
	public String drawArrow() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(this.start);
		sb.append(ARROW);
		sb.append(this.end);
		sb.append(this.details);
		return sb.toString();
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

}
