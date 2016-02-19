package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import problem.asm.Config;
import problem.asm.DesignParser;
import problem.asm.PhaseFactory;
import problem.interfaces.IClass;
import problem.interfaces.IModel;
import problem.interfaces.IPattern;

public class CheckboxPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	Map<String, List<JCheckBox>> checkboxGroups;
	JPanel parent;
	ImageProxy image;
	private Config config;

	public CheckboxPanel(JPanel parent, Config config, IModel model, ImageProxy image) {
		this.image = image;
		this.config = config;
		// this.setPreferredSize(new Dimension(200 ,this.getHeight()));
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		checkboxGroups = new HashMap<String, List<JCheckBox>>();
		this.parent = parent;
		// fills in map of CheckBoxes
		this.fillMapWithCheckBoxes(model);
		this.drawCheckBoxes();
	}
	
	public void addToPanel(JComponent c){
		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.WEST;
		cons.gridy = 0;
		this.add(c, cons);
	}

	private void fillMapWithCheckBoxes(IModel model) {
		for (IClass c : model.getClasses()) {
			JCheckBox classBox = new JCheckBox(c.getClassName());
			classBox.setSelected(true);
			classBox.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (classBox.isSelected()){
						config.classes.add(classBox.getText().replace("/", "."));
					}
					else{
						config.classes.remove(classBox.getText().replace("/", "."));
					}
					try {
						image.flushImage();
						File oldImage = new File(config.outDir + "/graph.png");
						if (oldImage.exists()) {
							oldImage.delete();
						}
						BorderLayout layout = (BorderLayout) parent.getLayout();
						parent.remove(layout.getLayoutComponent(BorderLayout.CENTER));
						
						parent.repaint();
						parent.revalidate();
						DesignParser.parse(config);
						Icon umlImage = new ImageProxy(config.outDir + "/graph.png");
						JScrollPane imageScrollPane = new JScrollPane(new JLabel(umlImage));
						parent.add(imageScrollPane, BorderLayout.CENTER);
						parent.repaint();
						parent.revalidate();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			if (c.getPatterns().isEmpty()) {
				if (checkboxGroups.containsKey("No Pattern")) {
					checkboxGroups.get("No Pattern").add(classBox);
				} else {
					checkboxGroups.put("No Pattern", new ArrayList<JCheckBox>());
					checkboxGroups.get("No Pattern").add(classBox);
				}
			} else {
				for (IPattern p : c.getPatterns()) {
					if (checkboxGroups.containsKey(p.getType())) {
						checkboxGroups.get(p.getType()).add(classBox);
					} else {
						checkboxGroups.put(p.getType(), new ArrayList<JCheckBox>());
						checkboxGroups.get(p.getType()).add(classBox);
					}
				}
			}
		}
	}

	private void drawCheckBoxes() {
		int counter = 1;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		for (String s : this.checkboxGroups.keySet()) {
			c.gridy = counter;
			Font patternFont = new Font("Serif", Font.BOLD, 20);
			if (s.equals("No Pattern")) {
				JLabel noPatternLabel = new JLabel("No Pattern");
				noPatternLabel.setFont(patternFont);
				this.add(noPatternLabel, c);
			} else {
				JCheckBox patternBox = new JCheckBox(s);
				patternBox.setSelected(true);
				patternBox.setFont(patternFont);
				patternBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						patternBox.setFont(patternFont);
						String PhaseName = PhaseFactory.patternToPhaseName.get(s);
						if (patternBox.isSelected()){
							config.phases.add(1, PhaseName);
						}
						else{
							config.phases.remove(PhaseName);
						}
						try {
							image.flushImage();
							File oldImage = new File(config.outDir + "/graph.png");
							if (oldImage.exists()) {
								oldImage.delete();
							}
							BorderLayout layout = (BorderLayout) parent.getLayout();
							parent.remove(layout.getLayoutComponent(BorderLayout.CENTER));
							
							parent.repaint();
							parent.revalidate();
							DesignParser.parse(config);
							Icon umlImage = new ImageProxy(config.outDir + "/graph.png");
							JScrollPane imageScrollPane = new JScrollPane(new JLabel(umlImage));
							parent.add(imageScrollPane, BorderLayout.CENTER);
							parent.repaint();
							parent.revalidate();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				this.add(patternBox, c);
			}
			c.insets = new Insets(0, 30, 0, 0);
			for (JCheckBox box : this.checkboxGroups.get(s)) {
				counter++;
				c.gridy = counter;
				this.add(box, c);
			}
			c.insets = new Insets(0, 0, 0, 0);
			counter++;
		}
	}

}
