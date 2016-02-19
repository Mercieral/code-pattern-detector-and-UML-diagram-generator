package project.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class ConfigFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigFrame() {
		this.setLayout(new GridLayout(0, 1));
		this.setSize(new Dimension(500, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
