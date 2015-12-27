package problem.asm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.objectweb.asm.Opcodes;

public class Model implements IModel {

	public Model() {
		this.classes = new ArrayList<IClass>();
	}

	private ArrayList<IClass> classes;

	@Override
	public void generateGraph() throws IOException {
		// TODO Auto-generated method stub

		System.out.println("generating graph file");

		String OUTPUT_FILE = "input_output/graph.gv";
		byte[] FIRST_LINE = "digraph G {  rankdir=BT; ".getBytes();
		byte[] LAST_LINE = "}".getBytes();
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		out.write(FIRST_LINE);

		// write all of the class Boxes
		for (IClass Class : classes) {
			// String classString = "\"" + Class.getClassName() + "\";";
			String classString = generateBoxObjects(Class);
			out.write(classString.getBytes());
		}
		
		// write all of the Class Relations
		for (IClass Class : classes){
			String relation = generateArrows(Class);
			out.write(relation.getBytes());
		}

		out.write(LAST_LINE);
		out.close();
		// System.out.println(new File("").getAbsoluteFile().toString());

		// run graphviz dot.exe with the new graph.gv file
		Runtime rt = Runtime.getRuntime();
		rt.exec("\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe\" "
				+ "-Tpng input_output\\graph.gv -o input_output\\graph.png");

	}

	/**
	 * Generates the class box for the diagram
	 * 
	 * @param obj
	 *            - Takes in {@link IClass} object to generate the class object
	 * @return - String to build the object with GraphBiz
	 */
	private String generateBoxObjects(IClass obj) {
		String beginBrace = "[ \n";
		String endBrace = "];";
		String box = " shape = \"record\", ";
		String labelStart = " label = \"{ ";
		String labelEnd = " }\" ";
		StringBuilder builder = new StringBuilder();
		String className = " " + obj.getClassName().replace("/", "") + " ";

		builder.append(className + beginBrace + box);
		builder.append(labelStart);
		if (obj.getAcessLevel() == Opcodes.ACC_INTERFACE) {
			builder.append("<<interface>>\n");
		}
		builder.append(trimValue(obj.getClassName(), "/") + "|");

		// TODO: Add fields here
		builder.append("\\l| \n ");
		// TODO: Add methods here
		builder.append(addMethods(obj.getIMethods()));

		builder.append(labelEnd);
		builder.append(endBrace);
		return builder.toString();
	}
	
	/**
	 * Generates the string representing the arrow relations in graphviz format
	 * for the given class object.
	 * 
	 * @param obj - Class object to generate arrows of
	 * @return String representation of relations
	 */
	private String generateArrows(IClass obj){
		String arrow = " -> ";
		String interfaceArrow = " [arrowhead=\"onormal\", style=\"dashed\"];";
		String classArrow = " [arrowhead=\"onormal\"]; ";
		StringBuilder builder = new StringBuilder();
		
		//Interface arrows
		for (String inter : obj.getInterface()){
			builder.append(obj.getClassName().replace("/", ""));
			builder.append(arrow);
			builder.append(inter.replace("/", ""));
			builder.append(interfaceArrow);
		}
		
		//extension arrow
		if (!obj.getExtension().isEmpty() && !obj.getExtension().replace("/", "").equals("javalangObject")){
			builder.append(obj.getClassName().replace("/", ""));
			builder.append(arrow);
			builder.append(obj.getExtension().replace("/", ""));
			builder.append(classArrow);
		}
		
		return builder.toString();
	}

	/**
	 * TODO
	 * 
	 * @param collection
	 *            - TODO
	 * @return - TODO
	 */
	private String addMethods(Collection<IMethod> collection) {
		StringBuilder build = new StringBuilder();
		for (IMethod method : collection) {
			if (!method.getName().equals("<init>")) {
				build.append("\t\t" + printMethod(method) + " \\l\n");
			}
		}
		return build.toString();
	}

	/**
	 * TODO FIXME 
	 * Cuts off Method Name
	 * 
	 * @param method
	 *            - TODO
	 * @return - TODO
	 */
	private String printMethod(IMethod method) {
		StringBuilder build = new StringBuilder();
		build.append(method.getAccessLevel() + " ");
		build.append(method.getName());
		build.append("(");
		for (String args : method.getArguments()) {
			String[] sep = args.split(" ");
			build.append(trimValue(sep[0], ".") + " " + sep[1] + ", ");
		}
		String result = build.substring(0, build.length() - 2);
		result = result + ") : ";
		return result + trimValue(method.getReturnType(), ".");
	}

	/**
	 * Shortens the name of strings that have a long value of extra information
	 * 
	 * @param initial
	 *            - Initial value to shorten
	 * @param delimiter
	 *            - Value to use to remove unnecessary pieces
	 * @return - Shortened string to be used containing useful information
	 */
	private String trimValue(String initial, String delimiter) {
		while (initial.indexOf(delimiter) != -1) {
			initial = initial.substring(initial.indexOf(delimiter) + 1);
		}
		return initial;
	}

	@Override
	public void addClass(IClass currentClass) {
		classes.add(currentClass);
	}

	@Override
	public ArrayList<IClass> getClasses() {
		return classes;
	}

}
