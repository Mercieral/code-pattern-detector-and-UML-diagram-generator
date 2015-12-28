package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Runs a process with supplied command and arguments.
 * 
 * @author Chandan R. Rupakheti (chandan.rupakheti@rose-hulman.edu)
 */
public abstract class ProcessRunner {
	protected volatile List<Process> processes;
	protected final String command;

	public ProcessRunner(String command) {
		this.processes = Collections.synchronizedList(new ArrayList<Process>());
		this.command = command;
	}

	public List<Process> getProcesses() {
		return Collections.unmodifiableList(this.processes);
	}

	public String getCommand() {
		return this.command;
	}

	/**
	 * Executes the process.
	 * 
	 * @throws {@link IOException}
	 */
	public abstract void execute(Path p) throws IOException;
}
