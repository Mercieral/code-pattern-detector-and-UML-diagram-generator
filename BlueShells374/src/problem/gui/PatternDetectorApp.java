package problem.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class PatternDetectorApp {
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Design Parser");
		
		RunnerPanel run = new RunnerPanel(args, mainFrame);
		run.setPreferredSize(new Dimension(500, 500));
		mainFrame.setContentPane(run);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}

}
