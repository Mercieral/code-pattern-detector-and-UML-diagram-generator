package problem.asm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import problem.visitor.ClassLoading;
import problem.visitor.AdapterVisitor;
import problem.visitor.BruteForceAdapterDetector;
import problem.visitor.CompositeVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.SingletonVisitor;
import problem.visitor.UMLGenerator;

public class PhaseFactory {
	
	/**
	 * Maps the phase name as a string to the class of the phase. You can 
	 * use reflection to instantiate the class and cast as IPhase.
	 */
	public static Map<String, Class<?>> phases = new LinkedHashMap<String, Class<?>>();
	
	/**
	 * Maps the pattern's name to it's corresponding Phase name
	 */
	public static Map<String, String> patternToPhaseName = new HashMap<String, String>();
	
	// Pre-populate phases here
	static{
		phases.put("Class-Loading", ClassLoading.class);
		phases.put("Decorator-Detection", DecoratorVisitor.class);
		phases.put("Singleton-Detection", SingletonVisitor.class);
		phases.put("Adapter-Detection", AdapterVisitor.class);
		phases.put("Brute-Force-Adapter-Dectection", BruteForceAdapterDetector.class);
		phases.put("Composite-Detection", CompositeVisitor.class);
		phases.put("UML-Generation", UMLGenerator.class);
		//phases.put("Sequence-Generation", new SequenceOutputStream());
		
		patternToPhaseName.put("Adapter Pattern", "Adapter-Detection");
		patternToPhaseName.put("Composite Pattern", "Composite-Detection");
		patternToPhaseName.put("Decorator Pattern", "Decorator-Detection");
		patternToPhaseName.put("Singleton Pattern", "Singleton-Detection");
	}	

}
