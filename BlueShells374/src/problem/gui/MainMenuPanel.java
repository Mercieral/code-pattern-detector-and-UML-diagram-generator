package problem.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	String[] args;

	private String[] URLS = { "http://xkcd.com/612/", "http://xkcd.com/349/",
			"http://xkcd.com/619/", "http://xkcd.com/1638/",
			"http://xkcd.com/1296/", "http://xkcd.com/1586/",
			"http://xkcd.com/297/", "http://xkcd.com/323/",
			"http://xkcd.com/754/", "http://xkcd.com/583/",
			"http://xkcd.com/303/", "http://xkcd.com/379/",
			"http://xkcd.com/1409/", "http://xkcd.com/1168/",
			"http://xkcd.com/664/", "http://xkcd.com/1367/",
			"http://xkcd.com/912/", "http://xkcd.com/1172/",
			"http://xkcd.com/859/", "http://xkcd.com/1323/",
			"http://xkcd.com/1275/", "http://xkcd.com/156/",
			"http://xkcd.com/297/", "http://xkcd.com/801/",
			"http://xkcd.com/1188/", "http://xkcd.com/257/",
			"http://xkcd.com/138/", "http://xkcd.com/371/",
			"http://xkcd.com/974/", "http://xkcd.com/953/",
			"http://xkcd.com/292/", "http://xkcd.com/761/",
			"http://xkcd.com/737/", "http://xkcd.com/518/",
			"http://xkcd.com/505/", "http://xkcd.com/399/",
			"http://xkcd.com/233/", "http://xkcd.com/632/",
			"http://xkcd.com/810/", "https://xkcd.com/1406/",
			"https://xkcd.com/979/", "https://xkcd.com/1597/" };

	public MainMenuPanel(JFrame mainframe, String[] args) {
		this.frame = mainframe;
		this.args = args;
		this.setLayout(new GridBagLayout());

		JButton UMLrunner = new JButton("Generate UML diagram");
		JButton SDrunner = new JButton("Generate sequence diagram");
		JButton config = new JButton("Config maker");
		JButton help = new JButton("Help");
		JButton openRandomPicture = new JButton("CS Related Picture");

		UMLrunner.addActionListener(new UMLAction());
		SDrunner.addActionListener(new SDAction());
		config.addActionListener(new ConfigAction());
		help.addActionListener(new HelpAction());
		openRandomPicture.addActionListener(new RandomPicture());

		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.insets = new Insets(20, 20, 20, 20);
		this.add(UMLrunner, c);
		c.gridy = 1;
		this.add(SDrunner, c);
		c.gridy = 2;
		this.add(config, c);
		c.gridy = 3;
		this.add(help, c);
		c.gridy = 4;
		this.add(openRandomPicture, c);

	}

	private class UMLAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel runner = new UMLAnalyzePanel(args, frame);
			frame.setContentPane(runner);
			frame.setPreferredSize(new Dimension(400, 400));
			frame.pack();
			frame.repaint();
			frame.revalidate();
		}
	}

	private class RandomPicture implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!Desktop.isDesktopSupported()) {
				JOptionPane.showMessageDialog(null,
						"Problem with desktop. Good luck");
			} else {
				try {
					Desktop.getDesktop().browse(new URI(chooseRandomURL()));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		}

		private String chooseRandomURL() {
			int length = URLS.length;
			Random r = new Random();
			return URLS[r.nextInt(length)];
		}

	}

	private class SDAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("not yet implemented in GUI");
		}
	}

	private class ConfigAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel configPanel = new ConfigMaker(frame);
			frame.setContentPane(configPanel);
			frame.pack();
			frame.repaint();
			frame.revalidate();
		}
	}

	private class HelpAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("not yet implemented in GUI");
		}
	}

}
