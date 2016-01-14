package javaClassesTest;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.javaClasses.Method;

public class MethodTest {

	//Method
	@Test
	public void testMethodClass() {
		Method m = new Method();
		assertEquals(m.getAccessLevel(), null);
		assertEquals(m.getDesc(), null);
		assertEquals(m.getName(), null);
		assertEquals(m.getReturnType(), null);
		m.setAccessLevel("public");
		m.setDesc("javaLangString");
		m.setName("myName");
		m.setReturnType("void");
		m.addArgument("arg1");
		m.addArgument("arg2");
		assertEquals(m.getAccessLevel(), "public");
		assertEquals(m.getDesc(), "javaLangString");
		assertEquals(m.getName(), "myName");
		assertEquals(m.getReturnType(), "void");
		assertEquals(m.getArguments().size(), 2);
	}
}
