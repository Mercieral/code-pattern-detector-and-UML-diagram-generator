package problem.blueshells.testing;

import org.junit.Before;
import org.junit.Test;

import problem.asm.Field;
import problem.asm.IField;
import problem.asm.IMethod;
import problem.asm.Method;

public class ConcreteClassTest {
	private IMethod testMethod;
	private IField testField;
	
	@Before
	public void setUp(){
		this.testMethod = new Method();
		this.testField = new Field();
		this.testField.setName("TestField");
	}
	
	@Test
	public void classTest(){
		// will write tests once fields and and methods are implented fully
	}
}
