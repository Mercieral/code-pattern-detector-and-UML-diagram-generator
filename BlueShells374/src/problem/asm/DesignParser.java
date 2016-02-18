package problem.asm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IPhase;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Model;
import problem.visitor.AdapterVisitor;
import problem.visitor.BruteForceAdapterDetector;
import problem.visitor.ClassLoading;
import problem.visitor.CompositeVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.SequenceOutputStream;
import problem.visitor.SingletonVisitor;
import problem.visitor.UMLGenerator;

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
//	public static void main(String[] args) throws IOException {
//		DesignParser parser = new DesignParser();
//		parser.parse(args);
//	}
	
	public static IModel parse(Config config, String[] args, JProgressBar loading, JLabel task) throws IOException {
		task.setText("initializing");
		loading.setValue(loading.getValue() + 1);
		IModel model = new Model();
		for (String phaseName: config.phases){
			IPhase phase = PhaseFactory.phases.get(phaseName);
			if (phase != null){
				phase.execute(config, model);
			}
		}
		return model;
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
	public static IModel parse(Config config, String[] args) throws IOException {
		IModel model = new Model();
		ClassLoading loader = new ClassLoading();
		loader.execute(config,  model);

		SingletonVisitor singletonVisitor = new SingletonVisitor();
		singletonVisitor.execute(config, model);
		DecoratorVisitor decoratorVisitor = new DecoratorVisitor();
		decoratorVisitor.execute(config, model);
		AdapterVisitor adapterVisitor = new AdapterVisitor();
		adapterVisitor.execute(config, model);
		CompositeVisitor compositeVisitor = new CompositeVisitor();
		compositeVisitor.execute(config, model);
//		BruteForceAdapterDetector adapterVisitor = new BruteForceAdapterDetector(model);
//		adapterVisitor.adapterDetect();
		
		
		//Comment out to use console input (in for GUI)
		IPhase UMLGenerator = new UMLGenerator();
		UMLGenerator.execute(config, model);
		
		return model;

		//Uncomment to use console input (out for GUI)
		//commandConsole(model, streams);
	}

	/**
	 * A nice feature to allow cycling output and easy to see output.
	 * 
	 * @param model
	 *            - Model object to create information of diagram
	 * @param generators
	 *            - Types of {@link IGenerator} objects that can build graphs
	 */
	private static void commandConsole(Config config, IModel model, HashMap<String, IPhase> streams) throws IOException {
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

				IPhase stream = streams.get(line);

				if (line.equals("sequence")) {
					SDLogic(line, scanner, stream);
					stream.execute(config,model);
				}
				else {
					stream.execute(config, model);
				}
				System.out.println(REFRESH_SUPPORT);
			}

			else {
				System.out.println(NO_SUPPORT);
			}
		}

		scanner.close();
	}

	private static void SDLogic(String line, Scanner scanner, IPhase stream) {
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
