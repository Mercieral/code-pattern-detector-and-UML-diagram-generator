package project.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import project.asm.Config;

public class ConfigLoader {

	public static Config loadFile(String file){
		Config config = new Config();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			while((line = in.readLine()) != null){
				String type = line.split(": ")[0];
				config.classesDiscludedFromDir = new ArrayList<String>();
				if (type.equals("Input-Folder")){
					if (line.split(": ").length <= 1){
						continue;
					}
					config.InputDir = line.split(": ")[1];
				}
				if (type.equals("Input-Classes")){
					ArrayList<String> classList = new ArrayList<String>();
					if (line.split(": ").length <= 1){
						continue;
					}
					line = line.split(": ")[1];
					for (String clazz : line.split(",")){
						classList.add(clazz);
					}
					config.classes = classList;
				}
				if (type.equals("Output-Directory")){
					config.outDir = line.split(": ")[1];
				}
				else if (type.equals("Dot-Path")){
					config.dotPath = line.split(": ")[1];
				}
				else if (type.equals("Phases")){
					if (line.split(": ").length <= 1){
						continue;
					}
					line = line.split(": ")[1];
					ArrayList<String> phaseList = new ArrayList<String>();
					for (String phase : line.split(",")){
						phaseList.add(phase);
					}
					config.phases = phaseList;
				}
				else if (type.equals("Adapter-MethodDelegation")){
					line = line.split(": ")[1];
					config.adapterMethodDelegation = Integer.parseInt(line);
				}
				else if (type.equals("File-Name")){
					config.outputFileName = line.split(": ")[1];
				}
				else if (type.equals("Singleton-requireGetInstance")){
					config.singletonGetInstance = Boolean.getBoolean( line.split(": ")[1]);
				}
				else if (type.equals("SDClass")){
					config.SDclass = line.split(": ")[1];
				}
				else if (type.equals("SDmethod")){
					config.SDmethod = line.split(": ")[1];
				}
				else if (type.equals("SDdesc")){
					config.SDdesc=line.split(": ")[1];
				}
				else if (type.equals("SDcallDepth")){
					config.SDcallDepth = Integer.parseInt(line.split(": ")[1]);
				}
			}
			if (config.classes == null){
				config.classes = new ArrayList<String>();
			}
			
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
}
