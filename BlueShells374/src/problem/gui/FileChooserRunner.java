package problem.gui;

public class FileChooserRunner {

	public static void main(String[] args) {
		ConfigParser cp = new ConfigParser();
		System.out.println("Empty string" + cp.getFileLocation()); // FIXME
		ConfigChooser cc = new ConfigChooser();
		cc.createFrame();
		while(cp.getFileLocation().equals("") && cc.isActive()){
			
		} 
		System.out.println("Location : "+ cp.getFileLocation()); // FIXME
		
	}

}
