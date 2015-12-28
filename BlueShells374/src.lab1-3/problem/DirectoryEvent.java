package problem;

import java.nio.file.Path;

public class DirectoryEvent {
	public static final String ENTRY_CREATE = "ENTRY_CREATE";
	public static final String ENTRY_DELETE = "ENTRY_DELETE";
	public static final String ENTRY_MODIFY = "ENTRY_MODIFY";
	
	private String eventType;
	private Path file;
	private IDirectoryMonitorService service;


	public DirectoryEvent(String eventType, Path file, IDirectoryMonitorService service) {
		this.eventType = eventType;
		this.file = file;
		this.service = service;
	}

	/**
	 * Returns one of the following event type:
	 * <ol>
	 * 	<li>{@link #ENTRY_CREATE} - When a file/folder gets created.</li>
	 * 	<li>{@link #ENTRY_DELETE} - When a file/folder gets deleted.</li>
	 * 	<li>{@link #ENTRY_MODIFY} - When a file/folder get modified.</li>
	 * </ol>	 
	 */
	public String getEventType() {
		return this.eventType;
	}

	
	/**
	 * Return the file that created the changes.
	 * @return
	 */
	public Path getFile() {
		return this.file;
	}

	/**
	 * Returns the {@link IDirectoryMonitorService} that generated this event.
	 */
	public IDirectoryMonitorService getSource() {
		return this.service;
	}

}
