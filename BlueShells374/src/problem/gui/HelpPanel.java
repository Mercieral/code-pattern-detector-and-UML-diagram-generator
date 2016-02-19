package problem.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HelpPanel extends JPanel{

	private static final long serialVersionUID = 2L;
	private JFrame mainMenuFrame;
	
	public HelpPanel(JFrame mainMenuFrame){
		this.mainMenuFrame = mainMenuFrame;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 20, 20);
		c.gridy = 0;
		this.backButton(c);
		c.gridy = 1;
		this.AnalyzeHelpButton(c);
		c.gridy = 2;
		this.ConfigHelpButton(c);
		c.gridy = 3;
		this.patternAdditionHelpButton(c);
		c.gridy = 4;
		this.includedPhasesButton(c);
	}
	
	private void backButton(GridBagConstraints c){
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackAction());
		this.add(backButton, c);
	}
	
	private void AnalyzeHelpButton(GridBagConstraints c){
		JButton UMLHelpButton = new JButton("Analyze Instructions");
		UMLHelpButton.addActionListener(new AnalyzeAction());
		this.add(UMLHelpButton, c);
	}
	
	private void ConfigHelpButton(GridBagConstraints c){
		JButton ConfigHelpButton = new JButton("Config Instructions");
		ConfigHelpButton.addActionListener(new ConfigAction());
		this.add(ConfigHelpButton, c);
	}
	
	private void patternAdditionHelpButton(GridBagConstraints c){
		JButton patternHelpButton = new JButton("Adding Phases Instructions");
		patternHelpButton.addActionListener(new PatternAction());
		this.add(patternHelpButton, c);
		
	}
	
	private void includedPhasesButton(GridBagConstraints c){
		JButton inludedPhasesButton = new JButton("Included Phases");
		inludedPhasesButton.addActionListener(new IncludedAction());
		this.add(inludedPhasesButton, c);
	}
	
	private class BackAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel mainMenu = new MainMenuPanel(HelpPanel.this.mainMenuFrame);
			HelpPanel.this.mainMenuFrame.setContentPane(mainMenu);
			HelpPanel.this.mainMenuFrame.pack();
			HelpPanel.this.mainMenuFrame.repaint();
			HelpPanel.this.mainMenuFrame.revalidate();
		}
	}
	
	private class AnalyzeAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String file = "AppInstructionDocuments\\AnalyzeInstructions.txt";
			try {
				String text = readInstructionFile(file);
				createInstructionFrame(text);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("File does not exist");
			}
		}
	}
	
	private class ConfigAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String file = "AppInstructionDocuments\\ConfigInstructions.txt";
			try {
				String text = readInstructionFile(file);
				createInstructionFrame(text);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("File does not exist");
			}
		}
	}
	
	private class PatternAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String file = "AppInstructionDocuments\\AddingAdditionalPatternInstructions.txt";
			try {
				String text = readInstructionFile(file);
				createInstructionFrame(text);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("File does not exist");
			}
		}
	}
	
	private class IncludedAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String file = "AppInstructionDocuments\\IncludedPhases.txt";
			try {
				String text = readInstructionFile(file);
				createInstructionFrame(text);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("File does not exist");
			}
		}
	}
	
	private String readInstructionFile(String File) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(File));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null){
			sb.append(line);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}
	
	private void createInstructionFrame(String text){
		JFrame instructionFrame = new JFrame("Design Parser");
		JTextArea instructions = new JTextArea(text);
		instructions.setEditable(false);
		instructionFrame.setContentPane(instructions);
		instructionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		instructionFrame.pack();
		instructionFrame.setVisible(true);
	}
}
