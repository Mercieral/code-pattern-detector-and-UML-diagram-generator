package problem.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Model implements IModel {
	
	public Model() {
		this.classes = new ArrayList<IClass>();
	}

	private ArrayList<IClass> classes;

	@Override
	public void generateGraph() throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("generating graph file");
		
		String OUTPUT_FILE = "input_output/graph.gv";
		byte[] FIRST_LINE = "digraph G {".getBytes();
		byte[] LAST_LINE = "}".getBytes();
		OutputStream out = new FileOutputStream(OUTPUT_FILE);
		out.write(FIRST_LINE);

		//write all of the class info and relations
		//GOODLUCK
		for (IClass Class : classes){
			String classString = "\"" + Class.getClassName() + "\";";
			out.write(classString.getBytes());
		}
		
		out.write(LAST_LINE);
		out.close();		
		//System.out.println(new File("").getAbsoluteFile().toString());
		
		//run graphviz dot.exe with the new graph.gv file (commented because not complete)
		//NEITHER WORK WHYYYYYYYYY!! BOTTOM ONE WORKS IN COMMAND PROMPT IVE TRIED EVERYTHING
		//Process process = new ProcessBuilder("C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe", "-Tpng", "input_output\\graph.gv", ">" , "input_output\\graph.png").start();
		//Runtime.getRuntime().exec("\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe\" -Tpng input_output\\graph.gv > input_output\\graph.png", null, new File("").getAbsoluteFile());
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe\" -Tpng input_output\\graph.gv -o input_output\\graph.png");
		
	}
	
	@Override
	public void addClass(IClass currentClass){
		classes.add(currentClass);
	}
	
	@Override
	public ArrayList<IClass> getClasses(){
		return classes;
	}

}
