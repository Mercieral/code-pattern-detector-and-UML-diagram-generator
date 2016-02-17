package problem.gui;

public class ConfigParser {
	
	private String fileLocation;
	
	public ConfigParser(){
		this.fileLocation = "";
	}
	
	public void setFileLocation(String location){
		this.fileLocation = location;
	}
	
	public String getFileLocation(){
		return this.fileLocation;
	}

}
