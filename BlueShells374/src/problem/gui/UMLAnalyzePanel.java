package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import problem.asm.Config;
import problem.asm.DesignParser;
import problem.interfaces.IModel;

@SuppressWarnings("serial")
public class UMLAnalyzePanel extends JPanel {
	
	private Config config;

	public UMLAnalyzePanel(String[] args, JFrame f) {
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		config = null;
		
		JLabel taskLabel = new JLabel();
		JProgressBar loadingBar = new JProgressBar(0, 7 + args.length);
		
		JButton loadButton = new JButton("Load Config");
		JButton analyzeButton = new JButton("Analyze");
		JButton backButton = new JButton("Back to main menu");
		loadButton.addActionListener(new load(taskLabel));
		analyzeButton.addActionListener(new analyze(args, loadingBar, taskLabel, f));
		backButton.addActionListener(new back(f, args));
		
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(0,0,30,20); c.gridwidth = 1;
		this.add(loadButton, c);
		c.gridx = 1; c.gridy = 0;
		this.add(analyzeButton, c);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.insets = new Insets(0,0,10,20);
		this.add(taskLabel, c);
		c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
		this.add(loadingBar, c);
		c.gridx = 0; c.gridy = 3; c.gridwidth = 2; c.insets = new Insets(0,0,20,20);
		this.add(backButton, c);
	}
	
	private class back implements ActionListener{
		JFrame frame;
		String[] args;
		
		public back(JFrame f, String[] args){
			this.frame = f;
			this.args = args;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			MainMenuPanel main = new MainMenuPanel(frame, args);
			frame.setContentPane(main);
			frame.pack();
			frame.repaint();
			frame.revalidate();
		}
	}
	
	
	private class load implements ActionListener{
		private JLabel task;
		
		public load(JLabel task){
			this.task = task;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("Select Config file");
			File defaultDir = new File("/config");
			fileChooser.setCurrentDirectory(defaultDir);
			int value = fileChooser.showOpenDialog(null);
			if (value == JFileChooser.APPROVE_OPTION) {
				File configFile = fileChooser.getSelectedFile();
				task.setForeground(Color.BLACK);
				task.setText("Loading configuration " + configFile.getName());
			}
		}
	}
	
	private class analyze implements ActionListener{
		private String[] args;
		private JFrame frame;
		private JProgressBar loading;
		private JLabel task;
		
		public analyze(String[] args, JProgressBar load, JLabel task, JFrame f){
			this.args = args;
			this.loading = load;
			this.task = task;
			this.frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (config == null){
				task.setForeground(Color.RED);
				task.setText("no configuration was selected");
				return;
			}
			task.setForeground(Color.BLACK);
			loading.setValue(0);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Analyzing");
						IModel model = DesignParser.parse(args, loading, task);
						
						JPanel panel = new JPanel();
						panel.setLayout(new BorderLayout());
						
						
						ImageProxy image = new ImageProxy("input_output/graph.png");
						CheckboxPanel cbpane = new CheckboxPanel(panel, model, image);
						JScrollPane imageScrollPane = new JScrollPane(new JLabel(image));
						
						JButton backButton = new JButton("Back to runner panel");
						backButton.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								JPanel runner = new UMLAnalyzePanel(args, frame);
								frame.setContentPane(runner);
								frame.repaint();
								frame.revalidate();
							}
							
						});
						cbpane.addToPanel(backButton);
						
						panel.add(cbpane, BorderLayout.WEST);
						panel.add(imageScrollPane, BorderLayout.CENTER);
						
						frame.setContentPane(panel);
						frame.setPreferredSize(new Dimension(1000, 800));
						frame.repaint();
						frame.pack();
						frame.revalidate();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			t.start();

		}
		
	}
	
	

}
