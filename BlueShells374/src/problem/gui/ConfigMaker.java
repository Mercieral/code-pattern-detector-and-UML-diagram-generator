package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfigMaker extends JPanel {

	/**
	 * Added by default
	 */
	private static final long serialVersionUID = 1L;

	private String inputLocation;

	private String outputLocation;

	private String exeLocation;

	private String additionalClasses;

	private String phases;

	private String additionalSettings;

	public ConfigMaker() {
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Configuration Panel");
		this.inputLocation = "";
		this.outputLocation = "";
		this.exeLocation = "";
		this.phases = "";
		this.additionalClasses = "";
		this.additionalSettings = "";
		titlePanel.add(title);
		this.setLayout(new GridLayout(9, 3));
		this.add(titlePanel);
		findInputWindow();
		addClassOptions();
		findOutputWindow();
		saveOutputName();
		setDotPath();
		setPhases();
		setSettings();
		saveFile();
	}

	private void saveFile() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Save file");
		JTextField fileName = new JTextField();
		fileName.setPreferredSize(new Dimension(250,
				(int) fileName.getPreferredSize().getHeight()));
		fileName.setText("defaultConfigName");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeFile();
			}
		});
		panel.add(button);
		panel.add(fileName);
		this.add(panel);
	}

	private void setSettings() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Set Additional Settings");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = JOptionPane.showInputDialog(
						"AdditionalSettings \n" + "Single setting at a time");
				if(additionalSettings != ""){
					additionalSettings += "\n" + temp;					
				} else { 
					additionalSettings = temp;
				}
			}
		});
		panel.add(button);
		JButton viewButton = new JButton("View Settings");
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Additional Settings: \n" + additionalSettings);
			}
		});
		panel.add(viewButton);
		JButton clear = new JButton("Clear Additional");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				additionalSettings = "";
				JOptionPane.showMessageDialog(null, "Cleared Settings");
			}
		});
		panel.setBackground(Color.GRAY);
		panel.add(clear);
		this.add(panel);
	}

	private void setPhases() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Set phases");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				phases = JOptionPane
						.showInputDialog("Framework's Execution Phases \n"
								+ "Separated by comma");
			}
		});
		panel.add(button);
		this.add(panel);
	}

	private void setDotPath() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
		JLabel label = new JLabel("Set Dot Path");
		JButton button = new JButton("Find Dot EXE");
		JLabel locationLabel = new JLabel("Folder location");
		ExecutableActionListener eal = new ExecutableActionListener(
				locationLabel, this, true);
		button.addActionListener(eal);

		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	private void saveOutputName() {
		JPanel panelButton = new JPanel();
		panelButton.setSize(new Dimension(this.getWidth(), this.getHeight()));
		JLabel outputFileName = new JLabel("Output File Name");
		JTextField fileName = new JTextField();
		fileName.setPreferredSize(new Dimension(250,
				(int) fileName.getPreferredSize().getHeight()));
		panelButton.add(outputFileName);
		panelButton.add(fileName);
		fileName.setText("defaultFileName");
		this.add(panelButton);
	}

	private void addClassOptions() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Add Additional Classes");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				additionalClasses = JOptionPane.showInputDialog(
						"Add additional classes \n" + "Separated by comma");
			}
		});
		panel.add(button);
		this.add(panel);
	}

	private void findOutputWindow() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
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
	public String getInputLocation() {
		return inputLocation;
	}

	/**
	 * @param dirLocation
	 *            the dirLocation to set
	 */
	public void setInputLocation(String dirLocation) {
		this.inputLocation = dirLocation;
	}

	/**
	 * @return the exeLocation
	 */
	public String getExeLocation() {
		return exeLocation;
	}

	/**
	 * @param exeLocation
	 *            - the exeLocation to set
	 */
	public void setExeLocation(String exeLocation) {
		this.exeLocation = exeLocation;
	}

	private void writeFile() {
		System.out.println("Saving file");
		if (this.inputLocation == "" || this.outputLocation == ""
				|| this.exeLocation == "" || this.additionalSettings == "") {
			JOptionPane.showMessageDialog(null,
					"Not all fields filled out. \nEnsure that there is an input directory, "
							+ "output directory, executable location, \n"
							+ "and additional " + "settings are filled out");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("Input-Folder: " + this.inputLocation);
			sb.append("\n");
			sb.append("Input-Classes: " + this.additionalClasses);
			sb.append("\n");
			sb.append("Output-Directory: " + this.outputLocation);
			sb.append("\n");
			sb.append("Dot-Path: " + this.exeLocation);
			sb.append("\n");
			sb.append("Phases: " + this.phases);
			sb.append("\n");
			sb.append("" + this.additionalSettings);
			System.out.println(sb.toString());

		}

	}

	/**
	 * @return the outputLocation
	 */
	public String getOutputLocation() {
		return outputLocation;
	}

	/**
	 * @param outputLocation
	 *            the outputLocation to set
	 */
	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

}
