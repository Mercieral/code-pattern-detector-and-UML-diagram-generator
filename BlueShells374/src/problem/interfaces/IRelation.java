package problem.interfaces;

import problem.visitor.ITraverser;

public interface IRelation extends ITraverser{
	
	public String drawRelation();
	public void setFromObject(String startObject);
	public void setToObject(String endObject);
	public String getFromObject();
	public String getToObject();

}
