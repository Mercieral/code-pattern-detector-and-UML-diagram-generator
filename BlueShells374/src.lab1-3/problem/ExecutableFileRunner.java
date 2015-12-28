package problem;

import java.io.IOException;
import java.nio.file.Path;

public class ExecutableFileRunner extends ProcessRunner {

	public ExecutableFileRunner(String command) {
		super(command);
	}

	@Override
	public void execute(Path p) throws IOException {
		System.out.format("Launching %s ...%n", p);
		ProcessBuilder processBuilder = new ProcessBuilder(p.toString());
		this.processes.add(processBuilder.start());
	}
}
