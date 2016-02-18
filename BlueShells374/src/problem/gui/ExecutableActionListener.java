package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExecutableActionListener implements ActionListener {

	private JLabel label;
	private ConfigMaker panel;
	private String location;
	private boolean status;

	public ExecutableActionListener(JLabel label, ConfigMaker rootPanel,
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
		fileChooser.setFileFilter(
				new FileNameExtensionFilter("Executables", "exe"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int value = fileChooser.showOpenDialog(panel);
		if (value == JFileChooser.APPROVE_OPTION) {
			this.location = fileChooser.getSelectedFile().toString();
			this.label.setText(fileChooser.getSelectedFile().toString());
			if (this.status) {
				this.panel.setExeLocation(this.location);
			}
		}
	}

}
