package problem;

import java.io.IOException;
import java.nio.file.Path;

public interface IAppTypes {
	
	public String getCommand();
	public Process getProcess(Path p) throws IOException;

}
