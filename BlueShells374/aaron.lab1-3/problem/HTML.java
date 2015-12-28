package problem;

import java.io.IOException;
import java.nio.file.Path;

public class HTML implements IAppTypes{
	private String command = "explorer";

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return this.command;
	}

	@Override
	public Process getProcess(Path file) throws IOException {
		// TODO Auto-generated method stub
		return new ProcessBuilder(command, file.toString()).start();
	}
	
	

}
