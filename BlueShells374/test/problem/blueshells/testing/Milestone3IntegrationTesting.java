package problem.blueshells.testing;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;

public class Milestone3IntegrationTesting {
	private String[] args;
	private PrintStream standardOut;
	
	@Before
	public void setup(){
		args = new String[1];
		standardOut = System.out;
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
	}
	
	@After
	public void tearDown(){
		System.setOut(standardOut);
	}
	
	@Test 
	public void IntegrationTest() throws IOException{
		args[0] = "java.util.Collections";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rSequence\rjava.util.Collections\rshuffle\r(Ljava/util/List;)V\r5\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/diagram.sd"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc7"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}
}
