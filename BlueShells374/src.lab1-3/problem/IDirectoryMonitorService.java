package problem;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A {@link DirectoryMonitorService} will monitor a supplied directory for
 * any changes in the directory. In particular there are three kinds of changes
 * that a registered listener ({@link IDirectoryListener}) gets notified with.
 * <ol>
 * 	<li>ENTRY_CREATE - When a file/folder gets created.</li>
 * 	<li>ENTRY_DELETE - When a file/folder gets deleted.</li>
 * 	<li>ENTRY_MODIFY - When a file/folder gets modified.</li>
 * </ol>
 * 
 * @see {@link IDirectoryListener}
 * @see {@link IDirectoryEvent}
 * @author Chandan R. Rupakheti (chandan.rupakheti@rose-hulman.edu)
 */
public interface IDirectoryMonitorService extends Runnable {
	/**
	 * Returns the directory being monitored.
	 */
	public Path getDirectory();
	
	/**
	 * Returns true if the monitor service is running, otherwise false.
	 */	
	public boolean isRunning();
	
	/**
	 * This method tries to stop the monitor service gracefully by interrupting
	 * the service.
	 * 
	 * @throws IOException
	 */
	public void stopGracefully() throws IOException;

	/**
	 * Registers the supplied listener to receive directory change
	 * notifications.
	 */
	public void addListener(IDirectoryListener l);
	
	/**
	 * Unregisters the supplied listener.
	 */
	public void removeListener(IDirectoryListener l);
}
