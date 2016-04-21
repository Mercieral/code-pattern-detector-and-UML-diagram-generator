package project.visitor;

import java.awt.Color;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IModel;
import project.interfaces.IPattern;
import project.interfaces.IPhase;
import project.patterns.ConfigPattern;

public class ConfigDetector implements IPhase{


	@Override
	public void execute(Config config, IModel model) {
		for(IClass c: model.getClasses()){
			if (c.getClassName().contains("Config")){
				System.out.println("setting pattern");
				IPattern p = new ConfigPattern(c.getClassName(), null);
				c.addPattern(p);
				
			}
		}

	}

}
