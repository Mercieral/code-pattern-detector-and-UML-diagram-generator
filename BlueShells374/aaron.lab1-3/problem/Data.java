package problem;

import java.nio.file.Path;

public class Data implements IData {
	public String eventName;
	public Path filePath;

	public Data(String eventName, Path file) {
		this.eventName = eventName;
		this.filePath = file;
	}

	public Path getFile(){
		return this.filePath;
	}
	
	public String getEvent(){
		return this.eventName;
	}
	
	public String getExtension(){
		return filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
	}
}
