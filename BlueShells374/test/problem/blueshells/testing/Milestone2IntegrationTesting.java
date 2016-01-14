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

public class Milestone2IntegrationTesting {
	private String[] args;
	private String[] args2;
	private PrintStream standardOut;

	@Before
	public void setUp() {
		args = new String[2];
		args2 = new String[4];
		standardOut = System.out;
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
	}
	
	@After
	public void tearDown(){
		System.setOut(standardOut);
	}

	@Test
	public void testFieldHasArrows() throws IOException {
		args[0] = "arrowTestingClasses.testClass1";
		args[1] = "arrowTestingClasses.testClass2";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc2"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}

	@Test
	public void testFieldAsListHasArrows() throws IOException {
		args[0] = "arrowTestingClasses.testClass1";
		args[1] = "arrowTestingClasses.testClass3";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc3"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}

	@Test
	public void testMultipleFieldsHasArrows() throws IOException {
		args2[0] = "arrowTestingClasses.testClass1";
		args2[1] = "arrowTestingClasses.testClass2";
		args2[2] = "arrowTestingClasses.testClass3";
		args2[3] = "arrowTestingClasses.testClass4";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args2);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc4"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}

	@Test
	public void testMethodUseArrows() throws IOException {
		args[0] = "arrowTestingClasses.testClass2";
		args[1] = "arrowTestingClasses.testClass5";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc5"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}

	@Test
	public void testMultipleUseAndHasArrows() throws IOException {
		args2[0] = "arrowTestingClasses.testClass1";
		args2[1] = "arrowTestingClasses.testClass2";
		args2[2] = "arrowTestingClasses.testClass6";
		args2[3] = "arrowTestingClasses.testClass4";
		InputStream Input1 = new ByteArrayInputStream(
				"Generator\rUML\rQuit\r".getBytes());
		InputStream old = System.in;
		System.setIn(Input1);
		DesignParser.parser(args2);
		BufferedReader br1 = new BufferedReader(
				new FileReader("input_output/graph.gv"));
		BufferedReader br2 = new BufferedReader(
				new FileReader("test/problem/blueshells/testing/testDoc6"));

		String line = "";
		while ((line = br1.readLine()) != null) {
			assertEquals(line, br2.readLine());
		}
		System.setIn(old);
		br1.close();
		br2.close();
	}
}