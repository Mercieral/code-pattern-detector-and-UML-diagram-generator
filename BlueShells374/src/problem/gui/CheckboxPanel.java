package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IPattern;

public class CheckboxPanel extends JPanel {
	
	Map<String, List<JCheckBox>> checkboxGroups;
	
	public CheckboxPanel(IModel model){
		this.setPreferredSize(new Dimension(150,this.getHeight()));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		JLabel testLabel = new JLabel();
		checkboxGroups = new HashMap<String, List<JCheckBox>>();
		testLabel.setText("test Checkbox");
		this.add(testLabel);
		
		for (IClass c : model.getClasses()){
			JCheckBox classBox = new JCheckBox(c.getClassName());
			classBox.setSelected(true);
			if (c.getPatterns().isEmpty()){
				this.add(classBox);
			}
			else {
				for (IPattern p : c.getPatterns()){
					this.add(classBox);
				}
			}
		}
	}

}
