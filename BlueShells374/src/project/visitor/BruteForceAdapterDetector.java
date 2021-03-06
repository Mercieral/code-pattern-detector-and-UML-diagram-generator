package project.visitor;

import java.util.ArrayList;
import java.util.List;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.interfaces.IRelation;
import project.patterns.AdapterPattern;

public class BruteForceAdapterDetector implements IPhase{
	private List<IClass> classList;

	public BruteForceAdapterDetector(){
		this.classList = new ArrayList<>();
	}
	
	@Override
	public void execute(Config config, IModel model){
		for(IClass c: model.getClasses()){
			if (c.getInterface().size() == 1) {
				// Detecting adapter
				if (c.getExtension().equals("java/lang/Object")
						|| c.getExtension().equals("")) {
					// Doesn't have an extension
					if (c.getIField().size() == 1) {
						// Only 1 field
						this.classList.add(c);
						// Adds it when all three parts exist
					}
				}
			}
		}
		for (IClass c0 : this.classList) {
			boolean isAdaptee = false;
			IClass adaptee = null;
			boolean isTarget = false;
			IClass target = null;
			String fieldType = ((List<IField>) c0.getIField())
					.get(0).getDesc();
			String interfaceName = ((List<String>) c0
					.getInterface()).get(0);
			for (IClass c1 : model.getClasses()) {
				if (c1.getClassName().equals(fieldType)
						|| c1.getClassName().replace("/", ".")
								.equals(fieldType)) {
					isAdaptee = true;
					adaptee = c1;
					continue; // If this one, not the interface
				}
				if (c1.getClassName().equals(interfaceName)) {
					isTarget = true;
					target = c1;
				}
				if (target != null && adaptee != null) {
					break;
				}
			}
			if (isAdaptee && isTarget) {
				adaptee.addPattern(
						new AdapterPattern(adaptee.getClassName(),
								"\\<\\<adaptee\\>\\>"));
				target.addPattern(
						new AdapterPattern(target.getClassName(),
								"\\<\\<target\\>\\>"));
				c0.addPattern(new AdapterPattern(c0.getClassName(),
						"\\<\\<adapter\\>\\>"));
			}
			for (IRelation r : model.getRelations()) {
				if (r.getFromObject()
						.equals(c0.getClassName().replace("/", ""))
						&& r.getToObject().equals(
								fieldType.replace(".", ""))) {
					r.addProperty("xlabel=\"\\<\\<adapts\\>\\>\"");
				}
			}
		}
	}
}
