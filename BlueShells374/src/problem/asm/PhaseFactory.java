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
	
	public static Map<String, Class<?>> phases = new HashMap<String, Class<?>>();
	
	// Pre-populate phases here
	static{
		phases = new LinkedHashMap<>();
		phases.put("Class-Loading", ClassLoading.class);
		phases.put("Decorator-Detection", DecoratorVisitor.class);
		phases.put("Singleton-Detection", SingletonVisitor.class);
		phases.put("Adapter-Detection", AdapterVisitor.class);
		phases.put("Brute-Force-Adapter-Dectection", BruteForceAdapterDetector.class);
		phases.put("Composite-Detection", CompositeVisitor.class);
		phases.put("UML-Generation", UMLGenerator.class);
		//phases.put("Sequence-Generation", new SequenceOutputStream());
	}	

}
