package problem.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfigChooser extends JFrame {
	
	private String file;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigChooser() {
		File folder = new File("config\\");
		File[] listOfFiles = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cfg");
			}
		});
		this.setTitle("Configuration File Chooser");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComboBox<File> fileList = new JComboBox<>(listOfFiles);
		fileList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				file = fileList.getSelectedItem().toString();
				System.out.println("File selected: " + file); // FIXME
				ConfigChooser.this.dispose();
				System.out.println("Done"); // FIXME
			}
		});
		JLabel chooseLabel = new JLabel("Choose Configuration File: ");
		JPanel chooser = new JPanel();
		chooser.add(chooseLabel);
		chooser.add(fileList);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2,
				dim.height / 2 - this.getSize().height / 2);
		this.add(chooser);
	}

	public void createFrame() {
		this.setVisible(true);
		this.pack();
	}

}
