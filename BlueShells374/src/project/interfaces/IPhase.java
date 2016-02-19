package project.interfaces;

import project.asm.Config;

public interface IPhase {
	
	public void execute(Config config, IModel model);

}
