package problem.asm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import problem.interfaces.IClass;
import problem.interfaces.IGenerator;
import problem.interfaces.IModel;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Model;
import problem.visitor.IStream;
import problem.visitor.SequenceOutputStream;
import problem.visitor.UMLOutputStream;

public class DesignParser {

	private static final String INPUT_CLASS_NAME = "Input Class Name:>";
	private static final String INPUT_METHOD_NAME = "Input Method Name:>";
	private static final String INPUT_PARAMETERS = "Input Parameters "
			+ "(split by commas):>";
	private static final String INPUT_CALL_DEPTH = "Input CallDepth "
			+ "(optional enter skip for default, default is 5)";
	private static final String SUPPORT_OPERATIONS = "Supported operations:"
			+ " Generator, Help, Quit \n" + "Input command:> ";
	private static final String HELP_SUPPORT = "Help not yet implemented";
	private static final String INPUT_GENERATOR = "Generators:"
			+ " Supported generators - UML, Sequence \n" + "Input generator:> ";
	private static final String NO_SUPPORT = "Unsupported operation";
	private static final String REFRESH_SUPPORT = "Generated graph, "
			+ "please refresh the input_output folder";
	private static final String GENERATOR_NOT_SUPPORTED = "Generator not supported";

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 * 
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		parser(args);
	}

	/**
	 * Used primarily for testing, allows to be run for arguments outside of
	 * main
	 * 
	 * @param args
	 *            - Array of arguments, which are separated by spaces. All
	 *            classes inside of a package for ASM to modify
	 * @throws IOException
	 *             - Exception if unable to read file
	 */
	public static void parser(String[] args) throws IOException {
		IModel model = new Model();
		IClass currentClass = null;

		for (String className : args) {
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);
			currentClass = new ConcreteClass();

			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					currentClass, args);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, currentClass, args);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, currentClass, args);

			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			// Add the class to the model
			model.addClass(currentClass);
		}

		HashMap<String, IStream> streams = new HashMap<>();
		streams.put("sequence", new SequenceOutputStream(
				new FileOutputStream("input_output/diagram.sd")));
		streams.put("uml", new UMLOutputStream(new FileOutputStream("input_output/diagram.sd")));
		
		

		commandConsole(model, streams);
	}

	/**
	 * A nice feature to allow cycling output and easy to see output.
	 * 
	 * @param model
	 *            - Model object to create information of diagram
	 * @param generators
	 *            - Types of {@link IGenerator} objects that can build graphs
	 */
	private static void commandConsole(IModel model, HashMap<String, IStream> streams) throws IOException {
		boolean quit = false;
		Scanner scanner = new Scanner(System.in);

		while (!quit) {
			System.out.print(SUPPORT_OPERATIONS);
			String line = scanner.nextLine();
			line = line.toLowerCase().trim();

			if (line.equals("quit")) {
				quit = true;
				continue;
			}

			else if (line.equals("help")) {
				System.out.println(HELP_SUPPORT);
			}

			else if (line.equals("generator")) {
				System.out.print(INPUT_GENERATOR);
				line = scanner.nextLine();
				line = line.toLowerCase().trim();

				if (!streams.containsKey(line)) {
					System.out.println(GENERATOR_NOT_SUPPORTED);
					continue;
				}

				IStream stream = streams.get(line);

				if (line.equals("sequence")) {
					SDLogic(line, scanner, stream);
					stream.write(model);
				}

				stream.write(model);
				System.out.println(REFRESH_SUPPORT);
			}

			else {
				System.out.println(NO_SUPPORT);
			}
		}

		scanner.close();
	}

	private static void SDLogic(String line, Scanner scanner, IStream stream) {
		System.out.print(INPUT_CLASS_NAME);
		line = scanner.nextLine();
		line = line.trim();
		String className = line;
		System.out.print(INPUT_METHOD_NAME);
		line = scanner.nextLine();
		line = line.trim();
		String methodName = line;
		System.out.print(INPUT_PARAMETERS);
		line = scanner.nextLine();
		line = line.trim();
		String desc = line;
		String[] args = line.split(",");
		System.out.print(INPUT_CALL_DEPTH);
		line = scanner.nextLine();
		line = line.toLowerCase().trim();
		int callDepth;
		if (line.equals("skip") || line.equals("")) {
			callDepth = 5;
		} else {
			callDepth = Integer.parseInt(line);
		}

		List<String> params = new ArrayList<String>();
		for (String arg : args) {
			params.add(arg);
		}
		SequenceOutputStream s = (SequenceOutputStream)stream;
		s.initializeStrings(className, methodName, desc, callDepth);
	}


}
