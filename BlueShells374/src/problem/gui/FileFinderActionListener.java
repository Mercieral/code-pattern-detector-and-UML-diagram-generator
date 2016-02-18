package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class FileFinderActionListener implements ActionListener {

	private JLabel label;
	private ConfigMaker panel;
	private String location;
	private boolean status;

	public FileFinderActionListener(JLabel label, ConfigMaker rootPanel,
			boolean locationStatus) {
		this.label = label;
		this.panel = rootPanel;
		this.location = "";
		this.status = locationStatus;
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
			if (this.status) {
				this.panel.setInputLocation(this.location);
			} else {
				this.panel.setOutputLocation(this.location);
			}
		}
	}

}
