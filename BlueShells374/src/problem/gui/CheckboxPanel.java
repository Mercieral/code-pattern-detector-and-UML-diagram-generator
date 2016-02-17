package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IPattern;

public class CheckboxPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Map<String, List<JCheckBox>> checkboxGroups;
	List<String> classes;
	
	public CheckboxPanel(IModel model){
		this.setPreferredSize(new Dimension(150,this.getHeight()));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		JLabel testLabel = new JLabel();
		classes = new ArrayList<String>();
		checkboxGroups = new HashMap<String, List<JCheckBox>>();
		testLabel.setText("test Checkbox");
		this.add(testLabel);
		
		//fills in map of CheckBoxes
		for (IClass c : model.getClasses()){
			clas
			JCheckBox classBox = new JCheckBox(c.getClassName());
			classBox.setSelected(true);
			if (c.getPatterns().isEmpty()){
				if (checkboxGroups.containsKey("No Pattern")){
					checkboxGroups.get("No Pattern").add(classBox);
				}
				else {
					checkboxGroups.put("No Pattern", new ArrayList<JCheckBox>());
					checkboxGroups.get("No Pattern").add(classBox);
				}
			}
			else {
				for (IPattern p : c.getPatterns()){
					if (checkboxGroups.containsKey(p.getType())){
						checkboxGroups.get(p.getType()).add(classBox);
					}
					else {
						checkboxGroups.put(p.getType(), new ArrayList<JCheckBox>());
						checkboxGroups.get(p.getType()).add(classBox);
					}
				}
			}
		}
	}

}
