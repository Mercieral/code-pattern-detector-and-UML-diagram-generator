package problem.asm;

public class ArrowInterface implements IArrow {

	private String start;

	private String end;

	private String details;

	private final String ARROW = " -> ";

	/**
	 * TODO
	 */
	public ArrowInterface() {
		this.start = null;
		this.end = null;
		this.details = "\n\t\t[arrowhead=\"onormal\", style=\"dashed\"];\n";
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
