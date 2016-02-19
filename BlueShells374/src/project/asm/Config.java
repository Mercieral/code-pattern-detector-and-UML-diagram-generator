package project.asm;

import java.util.List;

public class Config {
	
	public String InputDir;
	public List<String> classesDiscludedFromDir;
	public List<String> classes;
	public String outDir;
	public String outputFileName;
	public String dotPath;
	public List<String> phases;
	public int adapterMethodDelegation = 1;
	public boolean singletonGetInstance = false;
	public String SDclass;
	public String SDmethod;
	public String SDdesc;
	public int SDcallDepth = 5;
	

}
