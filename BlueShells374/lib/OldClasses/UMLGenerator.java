package problem.javaClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IGenerator;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;

public class UMLGenerator implements IGenerator {
	private IModel model;
	private String name;

	@Override
	public void execute() {
		try {
			generateGraph();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
					"Something went wrong while trying to generate the graph");
		}
	}

	public UMLGenerator(IModel model) {
		this.model = model;
		this.name = "UMLGenerator";
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Generates the UML graph
	 * 
	 * @throws IOException
	 */
	private void generateGraph() throws IOException {
		System.out.println("generating graph file");
		List<IClass> classes = this.model.getClasses();

		String OUTPUT_FILE = "input_output/graph.gv";
		byte[] FIRST_LINE = "digraph G {\t rankdir=BT; \n".getBytes();
		byte[] ADDITIONAL_SETTINGS = "splines=\"ortho\";\n".getBytes();
		byte[] LAST_LINE = "\n}".getBytes();
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		out.write(FIRST_LINE);
		out.write(ADDITIONAL_SETTINGS);

		// write all of the class Boxes
		for (IClass Class : classes) {
			// String classString = "\"" + Class.getClassName() + "\";";
			String classString = generateBoxObjects(Class);
			out.write(classString.getBytes());
		}

		// write all of the Class Relations
		for (IClass Class : classes) {
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
		String endBrace = "\t]; \n";
		String box = "\t\tshape = \"record\",\n";
		String labelStart = "\t\tlabel = \n\t\t\t\"{ ";
		String labelEnd = "\t\t\t}\" \n";
		StringBuilder builder = new StringBuilder();
		String className = "\t" + obj.getClassName().replace("/", "") + " ";

		builder.append(className + beginBrace + box);
		builder.append(labelStart);
		if (obj.getAcessLevel() == Opcodes.ACC_INTERFACE) {
			builder.append("<<interface>>\n");
		}
		builder.append(obj.getClassName() + "\n\t\t\t|\n");
		// trimValue(obj.getClassName(), "/")

		builder.append(addFields(obj.getIField()));
		// removed \\l to make it look nicer on the picture
		builder.append("\t\t\t| \n ");

		builder.append(addMethods(obj.getIMethods()));

		builder.append(labelEnd);
		builder.append(endBrace);
		return builder.toString();
	}

	/**
	 * Generates the string representing the arrow relations in graphviz format
	 * for the given class object.
	 * 
	 * @param obj
	 *            - Class object to generate arrows of
	 * @return String representation of relations
	 */
	private String generateArrows(IClass obj) {
		StringBuilder builder = new StringBuilder();
		List<String> hasClassNames = new ArrayList<>();
		Map<String, IArrow> useArrowList = new HashMap<>();

		for (IArrow arrow : obj.getArrows()) {
			String pointerClass = parsePointerClass(arrow.getToObject());
			String arrowType = parseArrowType(arrow.getClass().toString());

			switch (arrowType) {
			case "ArrowHas":
				if (!hasClassNames.contains(pointerClass)) {
					hasClassNames.add(pointerClass);
					builder.append(arrow.drawArrow());
					if (useArrowList.containsKey(pointerClass)) {
						useArrowList.remove(pointerClass);
					}
				}
				break;
			case "ArrowUses":
				if (!useArrowList.containsKey(pointerClass)
						&& !hasClassNames.contains(pointerClass)) {
					useArrowList.put(pointerClass, arrow);
				}
				break;
			case "ArrowInterface":
				builder.append(arrow.drawArrow());
				break;
			case "ArrowExtension":
				builder.append(arrow.drawArrow());
				break;
			}
		}

		for (IArrow tempArrow : useArrowList.values()) {
			builder.append(tempArrow.drawArrow());
		}

		return builder.toString();
	}

	private String parsePointerClass(String classPath) {
		String parsedClass = trimValue(classPath, "/");
		return parsedClass;
	}

	private String parseArrowType(String arrowPath) {
		String parsedArrow = trimValue(arrowPath, ".");
		return parsedArrow;
	}

	/**
	 * Will add methods to box objects for classes
	 * 
	 * @param collection
	 *            - {@link Collection} of {@link IMethod} objects to be used to
	 *            add info for class boxes
	 * @return - String to be added to output file
	 */
	private String addMethods(Collection<IMethod> collection) {
		StringBuilder build = new StringBuilder();
		for (IMethod method : collection) {
			if (!method.getName().equals("<init>")) {
				build.append("\t\t\t" + printMethod(method) + " \\l\n");
			}
		}
		return build.toString();
	}

	/**
	 * Used to print the methods nicely for the output file. Will handle making
	 * the arguments look nice and the return value
	 * 
	 * @param method
	 *            - {@link IMethod} object that needs to be parsed for graph
	 * @return - String of the parsed graph
	 */
	private String printMethod(IMethod method) {
		StringBuilder build = new StringBuilder();
		build.append(method.getAccessLevel() + " ");
		build.append(method.getName());
		build.append("(");
		for (String args : method.getArguments()) {
			build.append(args + ", ");
			// sep[0]
		}
		String result = build.toString();
		if (!method.getArguments().isEmpty()) {
			result = result.substring(0, build.length() - 2);
		}
		result = result + ") : ";
		return result + trimValue(method.getReturnType(), ".");
		// method.getReturnType();
	}

	/**
	 * Will add fields to box objects
	 * 
	 * @param collection
	 *            -list of {@link IField} objects for a given {@link IClass} obj
	 * @return -String of the parsed graph
	 */
	private String addFields(Collection<IField> collection) {
		StringBuilder build = new StringBuilder();
		for (IField field : collection) {
			build.append("\t\t\t" + printFields(field) + " \\l\n");
		}
		return build.toString();

	}

	/**
	 * Uses the given IField and parses the desired information for the graph
	 * 
	 * @param field
	 *            - prints the {@link IMethod} that needs to be parsed
	 * @return - String of parsed graph
	 */
	private String printFields(IField field) {
		StringBuilder build = new StringBuilder();
		build.append(field.getAccessLevel() + " ");

		// remove the path information from the description in order to give the
		// string a cleaner look
		if (field.getSignature().equals(""))
			build.append(trimValue(field.getDesc(), ".") + " ");

		else {
			build.append(trimValue(field.getDesc(), ".") + "[");
			build.append(trimValue(field.getSignature(), ".") + "] ");
		}
		build.append(field.getName());
		return build.toString();
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
			// // Used for if a type is given to a list
			// if (initial.indexOf("<") != -1){
			// if(initial.indexOf(delimiter) > initial.indexOf("<")){
			// return initial;
			// }
			// }
			initial = initial.substring(initial.indexOf(delimiter) + 1);
		}
		return initial;
	}

}
