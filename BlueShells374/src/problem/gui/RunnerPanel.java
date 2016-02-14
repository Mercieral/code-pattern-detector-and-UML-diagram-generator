package problem.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import problem.asm.DesignParser;

@SuppressWarnings("serial")
public class RunnerPanel extends JPanel {

	public RunnerPanel(String[] args, JFrame f) {
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel taskLabel = new JLabel();
		JProgressBar loadingBar = new JProgressBar(0, 7 + args.length);
		
		JButton loadButton = new JButton("Load Config");
		JButton analyzeButton = new JButton("Analyze");
		loadButton.addActionListener(new load());
		analyzeButton.addActionListener(new analyze(args, loadingBar, taskLabel, f));
		
		c.gridx = 0; c.gridy = 0; c.insets = new Insets(0,0,30,20);
		this.add(loadButton, c);
		c.gridx = 1; c.gridy = 0;
		this.add(analyzeButton, c);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.insets = new Insets(0,0,10,20);
		this.add(taskLabel, c);
		c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
		this.add(loadingBar, c);
	}
	
	private class load implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("load");
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
			loading.setValue(0);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					DesignParser parser = new DesignParser();
					try {
						System.out.println("Analyzing");
						parser.parse(args, loading, task);
						JPanel panel = new JPanel();
						panel.setLayout(new BorderLayout());
						CheckboxPanel cbpane = new CheckboxPanel();
						ImagePanel imagepane = new ImagePanel();
						panel.add(cbpane, BorderLayout.WEST);
						panel.add(imagepane, BorderLayout.CENTER);
						frame.setContentPane(panel);
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
