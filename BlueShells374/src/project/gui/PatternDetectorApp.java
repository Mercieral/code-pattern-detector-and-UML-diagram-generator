package project.gui;

import javax.swing.JFrame;

public class PatternDetectorApp {
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Design Parser");
		
//		RunnerPanel run = new RunnerPanel(args, mainFrame);
//		run.setPreferredSize(new Dimension(500, 500));
//		mainFrame.setContentPane(run);
		MainMenuPanel main = new MainMenuPanel(mainFrame);
		mainFrame.setContentPane(main);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}

}
