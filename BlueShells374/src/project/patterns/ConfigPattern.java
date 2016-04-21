package project.patterns;

import java.awt.Color;

import project.interfaces.IPattern;

public class ConfigPattern implements IPattern {
	
	private String type;
	private String className;

	public ConfigPattern(String className, String type) {
		this.className = className;
		this.type = type;
	}

	@Override
	public Color getColor() {
		return Color.PINK;
	}

	@Override
	public String getType() {
		return "ConfigPattern";
	}

	@Override
	public String getProperty() {
		return "fillcolor=pink, style=filled, ";
	}

	@Override
	public String getLabel() {
		return "\\<\\<Config\\>\\>";
	}

	@Override
	public String getClassName() {
		return this.className;
	}

}
