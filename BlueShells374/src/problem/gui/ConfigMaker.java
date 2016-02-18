package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import problem.asm.PhaseFactory;

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

	private JFrame startFrame;
	
	private List<String> selectedFrames;

	public ConfigMaker(JFrame startFrame) {
		JPanel titlePanel = new JPanel();
		this.startFrame = startFrame;
		JLabel title = new JLabel("Configuration Panel");
		this.inputLocation = "";
		this.outputLocation = "";
		this.exeLocation = "";
		this.phases = "";
		this.additionalClasses = "";
		this.additionalSettings = "";
		this.selectedFrames = new ArrayList<>();
		titlePanel.add(title);
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startFrame.setContentPane(new MainMenuPanel(startFrame));
				startFrame.repaint();
				startFrame.revalidate();
				startFrame.pack();
			}
		});
		titlePanel.add(button);
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

	/**
	 * Adds the panel that contains saving the file
	 */
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
				writeFile(fileName.getText());
			}
		});
		panel.add(button);
		panel.add(fileName);
		this.add(panel);
	}

	/**
	 * Adds the additional settings bar to the Configuration maker.
	 */
	private void setSettings() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Set Additional Settings");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = JOptionPane.showInputDialog(
						"AdditionalSettings \n" + "Single setting at a time");
				if (additionalSettings != "") {
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

	/**
	 * Adds the phases button to the panel to allow for name of framework's
	 * execution phases
	 */
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

	/**
	 * Adds panel to the Configuration maker that allows choosing path to
	 * execution
	 */
	private void setDotPath() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
		JLabel label = new JLabel("Set Dot Path");
		JButton button = new JButton("Find Dot EXE");
		JLabel locationLabel = new JLabel("Folder location");
		if (new File("C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe")
				.exists()) {
			locationLabel.setText(
					"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
		}
		ExecutableActionListener eal = new ExecutableActionListener(
				locationLabel, this, true, this.startFrame);
		button.addActionListener(eal);

		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	/**
	 * Adds the output name bar to the Configuration Panel and stored the output
	 * name chosen by the user
	 */
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

	/**
	 * Adds the class option panel to the Configuration Panel
	 */
	private void addClassOptions() {
		JPanel panel = new JPanel();
		List<JCheckBox> listCheck = new ArrayList<>();
		for(String value: PhaseFactory.phases.keySet()){ // FIXME
			JCheckBox classBox = new JCheckBox(value);
			classBox.setSelected(false);
			classBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(classBox.isSelected()){
						if(!selectedFrames.contains(classBox.getText())){
							selectedFrames.add(classBox.getText());
							System.out.println("Add");
						}
					} else {
						if(selectedFrames.contains(classBox.getText())){
							selectedFrames.remove(classBox.getText());
							System.out.println("Remove");
						}
					}
				}
			});
			listCheck.add(classBox);
			panel.add(classBox);
		}
//		JButton button = new JButton("Add Additional Classes");
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				additionalClasses = JOptionPane.showInputDialog(
//						"Add additional classes \n" + "Separated by comma",
//						additionalClasses);
//			}
//		});
//		panel.add(button);
		this.add(panel);
	}

	/**
	 * Adds the output name bar to the Configuration Panel and stored the output
	 * folder chosen by the user
	 */
	private void findOutputWindow() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
		JLabel label = new JLabel("Output Path");
		JButton button = new JButton("Find Output Path");
		JLabel locationLabel = new JLabel("Folder location");
		button.addActionListener(new FileFinderActionListener(locationLabel,
				this, false, this.startFrame));
		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	/**
	 * Adds the input location bar to the Configuration Panel for the user to
	 * show where an input folder is
	 */
	private void findInputWindow() {
		JPanel panelButton = new JPanel();
		panelButton.setBackground(Color.GRAY);
		JLabel label = new JLabel("Find input path");
		JButton button = new JButton("Find main path");
		JLabel locationLabel = new JLabel("Folder location");
		FileFinderActionListener ffal = new FileFinderActionListener(
				locationLabel, this, true, this.startFrame);
		button.addActionListener(ffal);

		panelButton.add(label);
		panelButton.add(button);
		panelButton.add(locationLabel);
		this.add(panelButton);
	}

	/**
	 * Set the input folder location
	 * 
	 * @param dirLocation
	 *            - the dirLocation to set
	 */
	public void setInputLocation(String dirLocation) {
		this.inputLocation = dirLocation;
	}

	/**
	 * Sets the executable location
	 * 
	 * @param exeLocation
	 *            - the exeLocation to set
	 */
	public void setExeLocation(String exeLocation) {
		this.exeLocation = exeLocation;
	}

	/**
	 * Writes the configuration file after checking if all inputs are valid for
	 * the field
	 * 
	 * @param fileName
	 *            - File name to save the file as
	 */
	private void writeFile(String fileName) {
		// if(false){ // Bypasses safety testing
		if ((this.outputLocation == "" || this.exeLocation == ""
				|| this.additionalSettings == "" || fileName == "")
				&& (this.inputLocation == "" && this.additionalClasses == "")) {
			JOptionPane.showMessageDialog(null,
					"Not all fields filled out. \nEnsure that there is an input directory, "
							+ "output directory, executable location, \n"
							+ "and additional " + "settings are filled out");
		} else {
			StringBuffer sb = new StringBuffer();
			this.inputLocation = this.inputLocation.replace("\\", "\\\\");
			sb.append("Input-Folder: " + this.inputLocation);
			sb.append("\n");
			sb.append("Input-Classes: " + this.additionalClasses);
			sb.append("\n");
			this.outputLocation = this.outputLocation.replace("\\", "\\\\");
			sb.append("Output-Directory: " + this.outputLocation);
			this.exeLocation = this.exeLocation.replace("\\", "\\\\");
			sb.append("\n");
			sb.append("Dot-Path: " + this.exeLocation);
			sb.append("\n");
			if (this.phases == null) {
				this.phases = "";
			}
			sb.append("Phases: " + this.phases);
			sb.append("\n");
			sb.append("" + this.additionalSettings);
			try {
				PrintWriter writer = new PrintWriter(
						"config/" + fileName + ".cfg", "UTF-8");
				writer.write(sb.toString());
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,
					"File saved to config/" + fileName);
			startFrame.setContentPane(new MainMenuPanel(startFrame));
			startFrame.repaint();
			startFrame.revalidate();
			startFrame.pack();
		}

	}

	/**
	 * Sets the output folder location
	 * 
	 * @param outputLocation
	 *            the outputLocation to set
	 */
	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

}
