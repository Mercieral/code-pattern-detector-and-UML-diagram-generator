package problem;

import java.io.IOException;
import java.nio.file.Path;

public class PNG implements IAppTypes {
	private String command = "mspaint";

	@Override
	public String getCommand() {
		return this.command;
	}

	@Override
	public Process getProcess(Path file) throws IOException {
		return new ProcessBuilder(command, file.toString()).start();
	}
	
}
