package problem.asm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.interfaces.IClass;
import problem.interfaces.IGenerator;
import problem.interfaces.IModel;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Model;
import problem.javaClasses.SequenceGenerator;
import problem.javaClasses.UMLGenerator;

public class DesignParser {

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
		System.out.println("Start"); // FIXME
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

			// TODO: add more DECORATORS here in later milestones to accomplish
			// specific tasks

			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			// Add the class to the model
			model.addClass(currentClass);
		}

		// UMLGenerator uml = new UMLGenerator(model);
		// uml.execute();
		HashMap<String, IGenerator> generators = new HashMap<>();
		generators.put("uml", new UMLGenerator(model));
		//generators.put("sequence", new SequenceGenerator(model));

		commandConsole(model, generators);
	}

	/**
	 * A nice feature to allow cycling output and easy to see output.
	 * 
	 * @param model
	 *            - Model object to create information of diagram
	 * @param generators
	 *            - Types of {@link IGenerator} objects that can build graphs
	 */
	private static void commandConsole(IModel model,
			HashMap<String, IGenerator> generators) {
		boolean quit = false;
		Scanner scanner = new Scanner(System.in);

		while (!quit) {
			System.out.print("Supported operations: Generator, Help, Quit \n"
					+ "Input command:> ");
			String line = scanner.nextLine();
			line = line.toLowerCase().trim();

			if (line.equals("quit")) {
				quit = true;
				continue;
			}

			else if (line.equals("help")) {
				System.out.println("Help not yet implemented");
			}

			else if (line.equals("generator")) {
				System.out
						.print("Generators: Supported generators - UML, Sequence \n"
								+ "Input generator:> ");
				line = scanner.nextLine();
				line = line.toLowerCase().trim();

				if (!generators.containsKey(line)) {
					System.out.println("Generator not supported");
					continue;
				}

				IGenerator generator = generators.get(line);
				generator.execute();
				System.out.println(
						"Generated graph, please refresh the input_output folder");
			}

			else {
				System.out.println("Unsupported operation");
			}
		}

		scanner.close();
	}
}
