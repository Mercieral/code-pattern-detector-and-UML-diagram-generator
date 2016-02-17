package problem.gui;

public class FileChooserRunner {

	public static void main(String[] args) {
		ConfigParser cp = new ConfigParser();
		System.out.println("Empty string" + cp.getFileLocation());
		ConfigChooser cc = new ConfigChooser(cp);
		cc.createFrame();
		System.out.println("File selected" + cp.getFileLocation());
		
	}

}
