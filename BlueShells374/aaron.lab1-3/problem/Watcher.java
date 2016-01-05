package problem;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Watcher extends Thread implements IWatcher{


	private boolean stop;
	private WatchService watcher;
	private final Path dir;
	private List<Process> processes;
	private List<IObserver> observers;
	private IData data;
	
	
	public Watcher(Path dir) throws IOException {
		this.stop = true;
		this.dir = dir;
		this.processes = Collections.synchronizedList(new ArrayList<Process>());
		this.observers = Collections.synchronizedList(new ArrayList<IObserver>());
		this.watcher = FileSystems.getDefault().newWatchService();
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}
	
	/**
	 * Process all events for keys queued to the watcher
	 */
	public void run() {
		this.stop = false;
		while(!stop) {
			// Wait for key to be signalled
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
				IData data = new Data(event.kind().name(), child);

				// Call the handler method
				this.setData(data);
			}

			// Reset key and remove from set if directory no longer accessible
			if (!key.reset()) {
				break;
			}
		}

		// We gracefully stopped the service now, let's delete the temp file
		this.clearEverything();
	}

	@Override
	public void addObserver(IObserver s) {
		this.observers.add(s);
		
	}

	@Override
	public void removeObserver(IObserver s) {
		this.observers.remove(s);
		
	}

	@Override
	public void setData(IData data) {
		this.data = data;
		this.notifyObservers();
	}

	public void notifyObservers() {
		synchronized (this.observers){
			for(IObserver sub: this.observers) {
				sub.handleDirectoryEvent(this);
			}
		}
		
	}

	@Override
	public IData getData() {
		return this.data;
	}

	@Override
	public void stopGracefully() throws IOException {
		this.stop = true;
		File file = new File(dir.toFile() + "/.temp");

		// Let's force the while loop in the run method to compe out of the blocking watcher.take() call here
		// You can also create a directory by calling file.mkdir()
		file.createNewFile();
		
	}
	
	/**
	 * This method is for internal use to delete the temporary file created by
	 * the {@link #clearEverything()} method. As well as to kill all of the newly
	 * created process.
	 */
	protected void clearEverything() {
		File file = new File(dir.toFile() + "/.temp");
		file.delete();
		
		for(Process p: this.processes) {
			p.destroy();
		}
	}

	@Override
	public void addProcess(Process p) {
		this.processes.add(p);
	}
	
	/**
	 * Returns the number of applications launched so far by the launcher.
	 */
	public int getApplicationsCount() {
		return this.processes.size();
	}
	
	/**
	 * Returns true if the launcher is running, otherwise false.
	 */
	public boolean isRunning() {
		return !stop;
	}
	

}
