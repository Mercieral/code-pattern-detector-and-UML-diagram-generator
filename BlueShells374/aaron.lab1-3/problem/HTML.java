package problem;

import java.io.IOException;
import java.nio.file.Path;

public class HTML implements IAppTypes{
	private String command = "explorer";

	@Override
	public String getCommand() {
		return this.command;
	}

	@Override
	public Process getProcess(Path file) throws IOException {
		return new ProcessBuilder(command, file.toString()).start();
	}
	
	

}
