package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileFinderActionListener implements ActionListener {

	private JLabel label;
	private JPanel panel;
	private String location;

	public FileFinderActionListener(JLabel label, JPanel rootPanel) {
		this.label = label;
		this.panel = rootPanel;
		this.location = "";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser;
		if (new File(label.getText()).isDirectory()) {
			fileChooser = new JFileChooser(label.getText());
		} else {
			fileChooser = new JFileChooser();
		}
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int value = fileChooser.showOpenDialog(panel);
		if (value == JFileChooser.APPROVE_OPTION) {
			this.location = fileChooser.getSelectedFile().toString();
			this.label.setText(fileChooser.getSelectedFile().toString());
			this.panel.updateUI();
		}
	}
	
	public String getLocation(){
		return this.location;
	}

}
