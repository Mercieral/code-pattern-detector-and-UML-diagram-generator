package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private Config cfg;

	public UMLAnalyzePanel(JFrame f) {
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel taskLabel = new JLabel();
		JProgressBar loadingBar = new JProgressBar();
		loadingBar.setMinimum(0);
		
		JButton loadButton = new JButton("Load Config");
		JButton analyzeButton = new JButton("Analyze");
		JButton backButton = new JButton("Back to main menu");
		loadButton.addActionListener(new load(taskLabel));
		analyzeButton.addActionListener(new analyze(loadingBar, taskLabel, f));
		backButton.addActionListener(new back(f));
		
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
		
		public back(JFrame f){
			this.frame = f;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			MainMenuPanel main = new MainMenuPanel(frame);
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
			JFrame chooserFrame = new JFrame();
			File folder = new File("config\\");
			File[] listOfFiles = folder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".cfg");
				}
			});
			chooserFrame.setTitle("Configuration File Chooser");
			chooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JComboBox<File> fileList = new JComboBox<>(listOfFiles);
			fileList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String filepath = fileList.getSelectedItem().toString();
					task.setForeground(Color.BLACK);
					task.setText("loaded configuration at " + filepath);
					chooserFrame.dispose();
					cfg = ConfigLoader.loadFile(filepath);
				}
			});
			JLabel chooseLabel = new JLabel("Choose Configuration File: ");
			JPanel chooser = new JPanel();
			chooser.add(chooseLabel);
			chooser.add(fileList);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			chooserFrame.setLocation(dim.width / 2 - chooserFrame.getSize().width / 2,
					dim.height / 2 - chooserFrame.getSize().height / 2);
			chooserFrame.add(chooser);
			chooserFrame.pack();
			chooserFrame.setVisible(true);
			
		}

	}
	
	private class analyze implements ActionListener{
		private JFrame frame;
		private JProgressBar loading;
		private JLabel task;
		
		public analyze(JProgressBar load, JLabel task, JFrame f){
			this.loading = load;
			this.task = task;
			this.frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (cfg == null){
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
						IModel model = DesignParser.parse(cfg, loading, task);
						
						JPanel panel = new JPanel();
						panel.setLayout(new BorderLayout());
						
						
						ImageProxy image = new ImageProxy(cfg.outDir + "/graph.png");
						CheckboxPanel cbpane = new CheckboxPanel(panel, cfg, model, image);
						JScrollPane imageScrollPane = new JScrollPane(new JLabel(image));
						
						JButton backButton = new JButton("Back to runner panel");
						backButton.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								JPanel runner = new UMLAnalyzePanel(frame);
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
						e1.printStackTrace();
					}
				}
				
			});
			t.start();

		}
		
	}
	
}
