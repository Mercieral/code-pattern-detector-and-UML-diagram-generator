package problem.blueshells.testing;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import problem.asm.ConcreteClass;
import problem.asm.DesignParser;
import problem.asm.IClass;
import problem.asm.IModel;
import problem.asm.Model;

public class Mileston1Testing {
	private IClass testClass;
	private IModel testModel;
	private String[] args;
	
	@Before
	public void setUp(){
		this.testClass = new ConcreteClass();
		this.testModel = new Model();
		args = new String[2];
	}
	
	@Test
	public void testMilestone1() throws IOException{
		args[0] = "problem.asm.IField";
		args[1] = "problem.asm.Field";
		
		DesignParser.parser(args);
		File file = new File("graph.gv");
	}
}
