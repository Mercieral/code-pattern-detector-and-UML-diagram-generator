package problem.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	JFrame frame;
	String[] args;
	
	public MainMenuPanel(JFrame mainframe, String[] args){
		this.frame = mainframe;
		this.args = args;
		this.setLayout(new GridBagLayout());
		
		JButton UMLrunner = new JButton("Generate UML diagram");
		JButton SDrunner = new JButton("Generate sequence diagram");
		JButton config = new JButton("Config maker");
		JButton help = new JButton("Help");
		
		UMLrunner.addActionListener(new umlAction());
		SDrunner.addActionListener(new sdAction());
		config.addActionListener(new configAction());
		help.addActionListener(new helpAction());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=0; c.insets = new Insets(20,20,20,20);
		this.add(UMLrunner, c);
		c.gridy=1;
		this.add(SDrunner, c);
		c.gridy=2;
		this.add(config, c);
		c.gridy=3;
		this.add(help, c);
		
		
	}
	
	private class umlAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel runner = new UMLAnalyzePanel(args, frame);
			frame.setContentPane(runner);
			frame.repaint();
			frame.revalidate();
		}
	}
	
	private class sdAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("not yet implemented in GUI");
		}
	}
	
	private class configAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel configPanel = new ConfigMaker();
			frame.setContentPane(configPanel);
			frame.repaint();
			frame.revalidate();
		}
	}
	
	private class helpAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("not yet implemented in GUI");
		}
	}

}
