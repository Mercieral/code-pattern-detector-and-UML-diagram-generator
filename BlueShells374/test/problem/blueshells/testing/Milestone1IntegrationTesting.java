package problem.blueshells.testing;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import problem.asm.DesignParser;

public class Milestone1IntegrationTesting {
	private String[] args;
	
	@Before
	public void setUp(){
		args = new String[2];
	}
	
	@Test
	public void testMilestone1() throws IOException{
		args[0] = "problem.asm.IField";
		args[1] = "problem.asm.Field";
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(new FileReader("test/problem/blueshells/testing/testDoc"));
		
		String line = "";
		while((line = br1.readLine()) != null){
			assertEquals(line, br2.readLine());
		}
		br1.close();
		br2.close();
	}
}
