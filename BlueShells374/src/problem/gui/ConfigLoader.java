package problem.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import problem.asm.Config;

public class ConfigLoader {

	public static Config loadFile(String file){
		Config config = new Config();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			while((line = in.readLine()) != null){
				String type = line.split(": ")[0];
				if (type.equals("Input-Folder")){
					if (line.split(": ").length <= 1){
						continue;
					}
					config.InputDir = line.split(": ")[1];
				}
				else if (type.equals("Input-Classes")){
					if (line.split(": ").length <= 1){
						continue;
					}
					line = line.split(": ")[1];
					config.classes = line.split(",");
				}
				else if (type.equals("Output-Directory")){
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
					config.phases = line.split(",");
				}
				else if (type.equals("Adapter-MethodDelegation")){
					line = line.split(": ")[1];
					config.adapterMethodDelegation = Integer.parseInt(line);
				}
			}
			
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
}
