package problem.asm;

import java.util.HashMap;
import java.util.Map;

import problem.visitor.ClassLoading;
import problem.interfaces.IPhase;
import problem.visitor.AdapterVisitor;
import problem.visitor.BruteForceAdapterDetector;
import problem.visitor.CompositeVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.SingletonVisitor;
import problem.visitor.UMLGenerator;

public class PhaseFactory {
	
	public static Map<String, IPhase> phases = new HashMap<String, IPhase>();
	
	// Pre-populate phases here
	static{
		phases = new HashMap<>();
		phases.put("Class-Loading", new ClassLoading());
		phases.put("Decorator-Detection", new DecoratorVisitor());
		phases.put("Singleton-Detection", new SingletonVisitor());
		phases.put("Adapter-Detection", new AdapterVisitor());
		phases.put("Brute-Force-Adapter-Dectection", new BruteForceAdapterDetector());
		phases.put("Composite-Detection", new CompositeVisitor());
		phases.put("UML-Generation", new UMLGenerator());
		//phases.put("Sequence-Generation", new SequenceOutputStream());
	}	

}
