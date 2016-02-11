package problem.gui;

import javax.swing.JFrame;

public class PatternDetectorApp {
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Design Parser");
		mainFrame.setSize(500, 500);
		
		RunPanel run = new RunPanel(args);
		mainFrame.add(run);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setVisible(true);
		
	}

}
