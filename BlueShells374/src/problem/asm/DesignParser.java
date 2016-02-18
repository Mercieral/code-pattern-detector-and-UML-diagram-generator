package problem.asm;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import problem.interfaces.IModel;
import problem.interfaces.IPhase;
import problem.javaClasses.Model;


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
//	public static void main(String[] args) throws IOException {
//		DesignParser parser = new DesignParser();
//		parser.parse(args);
//	}
	
	public static IModel parse(Config config, JProgressBar loading, JLabel task) throws IOException {
		
		
		loading.setMaximum(config.phases.size());
		task.setText("initializing");
		loading.setValue(loading.getValue() + 1);
		IModel model = new Model();
		for (String phaseName: config.phases){
			task.setText("running " + phaseName);
			IPhase phase;
			try {
				phase = (IPhase)PhaseFactory.phases.get(phaseName).getConstructor().newInstance();
				if (phase != null){
					phase.execute(config, model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loading.setValue(loading.getValue() + 1);
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
	public static IModel parse(Config config) throws IOException {
		IModel model = new Model();
		for (String phaseName: config.phases){
			IPhase phase;
			try {
				phase = (IPhase)PhaseFactory.phases.get(phaseName).getConstructor().newInstance();
				if (phase != null){
					phase.execute(config, model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return model;
	}

}