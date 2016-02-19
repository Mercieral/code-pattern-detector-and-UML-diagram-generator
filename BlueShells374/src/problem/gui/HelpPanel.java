package problem.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
			System.out.println("Analyze Instructions");
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
			System.out.println("Config Instructions");
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
			System.out.println("Pattern addition instructions");
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
	
	private String readInstructionFile(String File) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(File));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null){
			sb.append(line);
			sb.append("\n");
		}
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
