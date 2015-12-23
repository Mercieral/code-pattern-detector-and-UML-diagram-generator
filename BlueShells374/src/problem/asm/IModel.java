package problem.asm;

import java.io.IOException;
import java.util.ArrayList;

public interface IModel {

	/**
	 * TODO
	 */
	public void generateGraph() throws IOException;
	
	/**
	 * TODO
	 */
	public void addClass(IClass currentClass);
	
	/**
	 * TODO
	 * @return
	 */
	public ArrayList<IClass> getClasses();

}
