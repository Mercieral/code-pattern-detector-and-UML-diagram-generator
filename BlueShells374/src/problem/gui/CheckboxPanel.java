package problem.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import problem.interfaces.IModel;

public class CheckboxPanel extends JPanel {
	
	public CheckboxPanel(IModel model){
		this.setPreferredSize(new Dimension(150,this.getHeight()));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		JLabel testLabel = new JLabel();
		testLabel.setText("test Checkbox");
		this.add(testLabel);
	}

}
