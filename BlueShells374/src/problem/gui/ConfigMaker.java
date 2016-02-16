package problem.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfigMaker extends JPanel {

	/**
	 * Added by default
	 */
	private static final long serialVersionUID = 1L;

	private String inputLocation;

	public ConfigMaker() {
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Configuration Panel");
		titlePanel.add(title);
		this.setLayout(new GridLayout(7, 6));
		this.add(titlePanel);
		findInputWindow();
		addClassOptions();
		findOutputWindow();
	}

	private void addClassOptions() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Choose location");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputLocation.equals("")){
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
				new FileFinderActionListener(locationLabel, panelButton));
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
				locationLabel, panelButton);
		button.addActionListener(ffal);
		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

}
