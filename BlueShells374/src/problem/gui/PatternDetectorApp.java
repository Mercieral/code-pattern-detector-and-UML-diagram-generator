package problem.gui;

import javax.swing.JFrame;

public class PatternDetectorApp {
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Design Parser");
		mainFrame.setSize(500, 500);
		
		runPanel run = new runPanel(args);
		mainFrame.add(run);
		
		mainFrame.setVisible(true);
		
	}

}
