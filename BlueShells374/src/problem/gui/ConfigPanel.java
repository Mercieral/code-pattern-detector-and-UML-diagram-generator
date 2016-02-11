package problem.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ConfigPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String inputFolder;
	
	private List<String> runClasses;
	
	private String outputDir;
	
	private String dotExecutable;
	
	private List<String> patterns;
	
	public ConfigPanel(){
		this.inputFolder = "";
		this.runClasses = new ArrayList<>();
		this.outputDir = "";
	}
	
	
	private boolean validPath(String path){
		
		return false;
	}
	

}
