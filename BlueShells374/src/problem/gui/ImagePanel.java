package problem.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	public ImagePanel(){
		JLabel testLabel = new JLabel();
		testLabel.setText("test Image panel");
		this.add(testLabel);
	}

}
