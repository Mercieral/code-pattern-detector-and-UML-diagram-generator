package problem.asm;

import java.util.Map;

import problem.interfaces.IPhase;
import problem.visitor.AdapterVisitor;
import problem.visitor.BruteForceAdapterDetector;
import problem.visitor.CompositeVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.SingletonVisitor;

public class PhaseFactory {
	
	private static Map<String, IPhase> phases;
	
	// Pre-populate phases here
	static{
		phases.put("Class-Loading", null);
		phases.put("Decorator-Detection", new DecoratorVisitor());
		phases.put("Singleton-Detection", new SingletonVisitor());
		phases.put("Adapter-Detection", new AdapterVisitor());
		phases.put("Brute-Force-Adapter-Dectection", new BruteForceAdapterDetector());
		phases.put("Composite-Detection", new CompositeVisitor());
		//phases.put("UML-Generation", new UMLOutputStream());
		//phases.put("Sequence-Generation", new SequenceOutputStream());
	}
	

}
