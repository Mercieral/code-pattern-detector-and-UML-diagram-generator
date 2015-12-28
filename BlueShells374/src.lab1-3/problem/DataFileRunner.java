package problem;

import java.io.IOException;
import java.nio.file.Path;

public class DataFileRunner extends ProcessRunner {

	public DataFileRunner(String command) {
		super(command);
	}

	@Override
	public void execute(Path p) throws IOException {
		System.out.format("Launching %s ...%n", command);
		ProcessBuilder processBuilder = new ProcessBuilder(command, p.toString());
		this.processes.add(processBuilder.start());
	}
}
