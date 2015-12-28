package problem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Date;

public class DirectoryChangeLogger implements IDirectoryListener {
	private Path path;

	public DirectoryChangeLogger(Path p) {
		this.path = p;
	}

	@Override
	public void directoryChanged(DirectoryEvent e) {
		// No need to recursively self report change in this log file
		if(path.equals(e.getFile()))
			return;
		
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.path.toString(), true)))) {
		    out.format("[%s] - %s - %s%n", new Date(), e.getEventType(), e.getFile());
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
}
