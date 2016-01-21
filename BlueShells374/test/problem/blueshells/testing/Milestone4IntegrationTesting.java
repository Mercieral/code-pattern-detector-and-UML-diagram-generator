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

public class Milestone4IntegrationTesting {
	private String[] args;
	private PrintStream standardOut;
	
	@Before
	public void setup(){
		this.args = new String[12];
		this.standardOut = System.out;
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
	}
	
	@Test 
	public void IntegrationTest() throws IOException{
		this.args[0] = "chocolate.ChocolateBoiler";
		this.args[1] = "chocolate.ChocolateController";
		this.args[2] = "classic.Singleton";
		this.args[3] = "dcl.Singleton";
		this.args[4] = "dcl.SingletonClient";
		this.args[5] = "stat.Singleton";
		this.args[6] = "stat.SingletonClient";
		this.args[7] = "sub.CoolerSingleton";
		this.args[8] = "sub.HotterSingleton";
		this.args[9] = "sub.Singleton";
		this.args[10] = "sub.SingletonTestDrive";
		this.args[11] = "threadsafe.Singleton";
		
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc8"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}
	
	@After
	public void tearDown(){
		System.setOut(standardOut);
	}
}
