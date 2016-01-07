package problem.asm;

public class ArrowHas implements IArrow {
	
	private String start;

	private String end;

	private String details;

	private final String ARROW = " -> ";

	/**
	 * TODO
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
		this.start = startObject;
	}

	@Override
	public void setToObject(String endObject) {
		this.end = endObject;
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
