package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import problem.asm.DesignParser;

@SuppressWarnings("serial")
public class runPanel extends JPanel {

	public runPanel(String[] args) {
		super();
		JButton loadButton = new JButton("Load Config");
		JButton analyzeButton = new JButton("Analyze");
		
		loadButton.addActionListener(new load());
		analyzeButton.addActionListener(new analyze(args));
		
		this.add(loadButton);
		this.add(analyzeButton);
		
	}
	
	private class load implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load");
		}
	}
	
	private class analyze implements ActionListener{
		private String[] args;
		
		public analyze(String[] args){
			this.args = args;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DesignParser parser = new DesignParser();
			try {
				parser.parse(args);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	

}
