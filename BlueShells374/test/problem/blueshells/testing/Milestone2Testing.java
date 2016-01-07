package problem.blueshells.testing;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;

public class Milestone2Testing {
	private String[] args;
	private String[] args2;
	
	@Before
	public void setUp(){
		args = new String[2];
		args2 = new String[4];
	}
	
	@Test
	public void testMilestone2() throws IOException{
		args[0] = "arrowTestingClasses.testClass1";
		args[1] = "arrowTestingClasses.testClass2";
		DesignParser.parser(args); 
		BufferedReader br1 = new BufferedReader(new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(new FileReader("test/problem/blueshells/testing/testDoc2"));
		
		String line = "";
		while((line = br1.readLine()) != null){
			assertEquals(line, br2.readLine());
		}
		br1.close();
		br2.close();
		
		args[0] = "arrowTestingClasses.testClass1";
		args[1] = "arrowTestingClasses.testClass3";
		DesignParser.parser(args); 
		br1 = new BufferedReader(new FileReader("input_output/graph.gv"));
		br2 = new BufferedReader(new FileReader("test/problem/blueshells/testing/testDoc3"));
		
		line = "";
		while((line = br1.readLine()) != null){
			assertEquals(line, br2.readLine());
		}
		br1.close();
		br2.close();
		
		args2[0] = "arrowTestingClasses.testClass1";
		args2[1] = "arrowTestingClasses.testClass2";
		args2[2] = "arrowTestingClasses.testClass3";
		args2[3] = "arrowTestingClasses.testClass4";
		DesignParser.parser(args2); 
		br1 = new BufferedReader(new FileReader("input_output/graph.gv"));
		br2 = new BufferedReader(new FileReader("test/problem/blueshells/testing/testDoc4"));
		
		line = "";
		while((line = br1.readLine()) != null){
			assertEquals(line, br2.readLine());
		}
		br1.close();
		br2.close();
	}
}
