package problem;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryMonitorService implements IDirectoryMonitorService {
	private static final String TEMP_FILE = ".temp"; 

	private final Path dir;
	private volatile boolean stop;
	private final WatchService watcher;

	private final List<IDirectoryListener> listeners;

	/**
	 * Creates a monitor service for the supplied directory.
	 * 
	 * @param dir The {@link Path} to the directory being monitored.
	 * @throws IOException
	 */
	public DirectoryMonitorService(Path dir) throws IOException {
		this.dir = dir;
		this.stop = true;

		this.watcher = FileSystems.getDefault().newWatchService();
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

		this.listeners = Collections.synchronizedList(new ArrayList<IDirectoryListener>());
	}

	@Override
	public Path getDirectory() {
		return this.dir;
	}

	@Override
	public void run() {
		System.out.format("The directory monitoring service started watching the %s directory ...%n", dir);
		
		this.stop = false;
		while(!stop) {
			// Wait for key to be signaled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			// Context for directory entry event is the file name of entry
			List<WatchEvent<?>> events = key.pollEvents();
			if(!events.isEmpty()) {
				@SuppressWarnings("unchecked")
				WatchEvent<Path> event = (WatchEvent<Path>)events.get(0);
				Path name = event.context();
				Path child = dir.resolve(name);
				
				// Call the handler method
				if(!child.endsWith(TEMP_FILE)) {
					DirectoryEvent e = new DirectoryEvent(event.kind().name(), child, this);
					this.notifyListeners(e);
				}
			}

			// Reset key and remove from set if directory no longer accessible
			if (!key.reset()) {
				break;
			}
		}

		// We gracefully stopped the service now, let's delete the temp file
		try {
			this.clearEverything();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.format("The directory monitoring service stopped watching the %s directory ...%n", dir);
	}
	
	@Override
	public boolean isRunning() {
		return !stop;
	}

	@Override
	public void stopGracefully() throws IOException {
		this.stop = true;
		
		File file = new File(dir + "/" + TEMP_FILE);

		// Let's force the while loop in the run method to come out of the blocking watcher.take() call here
		// You can also create a directory by calling file.mkdir() to get the same effect
		file.createNewFile();
	}
	
	/**
	 * This method is for internal use to delete the temporary file created by
	 * the {@link #stopGracefully()} method. 
	 * @throws IOException 
	 */
	void clearEverything() throws IOException {
		Files.deleteIfExists(Paths.get(dir + "/" + TEMP_FILE));
	}	

	@Override
	public void addListener(IDirectoryListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeListener(IDirectoryListener l) {
		this.listeners.remove(l);
	}
	
	/**
	 * For internal use. Notifies all of the registered listeners to handle the directory
	 * change event.
	 */
	void notifyListeners(DirectoryEvent e) {
		synchronized(this.listeners) {
			for(IDirectoryListener l : this.listeners) {
				l.directoryChanged(e);
			}
		}
	}	
}
