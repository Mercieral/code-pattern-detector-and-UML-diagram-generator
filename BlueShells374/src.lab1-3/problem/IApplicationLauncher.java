package problem;

import java.util.Collection;

public interface IApplicationLauncher extends IDirectoryListener {
	public Collection<ProcessRunner> getRunners();
	public void addRunner(String extension, ProcessRunner runner);
	public void shutDown();
}
