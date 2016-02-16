package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfigMaker extends JPanel {

	/**
	 * Added by default
	 */
	private static final long serialVersionUID = 1L;

	private String dirLocation;

	public ConfigMaker() {
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Configuration Panel");
		dirLocation = "";
		titlePanel.add(title);
		this.setLayout(new GridLayout(7, 3));
		this.add(titlePanel);
		findInputWindow();
		addClassOptions();
		findOutputWindow();
		saveOutputName();
	}

	private void saveOutputName() {
		JPanel panelButton = new JPanel();
		panelButton.setSize(new Dimension(this.getWidth(), this.getHeight()));
		JLabel outputFileName = new JLabel("Output File Name");
		JTextField fileName = new JTextField();
		fileName.setPreferredSize(new Dimension(250, (int) fileName.getPreferredSize().getHeight()));
		panelButton.add(outputFileName);
		panelButton.add(fileName);
		fileName.setText("defaultFileName");
		this.add(panelButton);
	}

	private void addClassOptions() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Specify Classes to Use");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dirLocation.equals("")) {
					System.out.println("Location not choosen");
				} else {
					System.out.println("Location choosen");
				}

			}
		});
		panel.add(button);
		this.add(panel);
	}

	private void findOutputWindow() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Output Path");
		JButton button = new JButton("Find Output Path");
		JLabel locationLabel = new JLabel("Folder location");
		button.addActionListener(
				new FileFinderActionListener(locationLabel, this, false));
		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	private void findInputWindow() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
		JLabel label = new JLabel("Find input path");
		JButton button = new JButton("Find main path");
		JLabel locationLabel = new JLabel("Folder location");
		FileFinderActionListener ffal = new FileFinderActionListener(
				locationLabel, this, true);
		button.addActionListener(ffal);

		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	/**
	 * @return the dirLocation
	 */
	public String getDirLocation() {
		return dirLocation;
	}

	/**
	 * @param dirLocation
	 *            the dirLocation to set
	 */
	public void setDirLocation(String dirLocation) {
		this.dirLocation = dirLocation;
	}

}
