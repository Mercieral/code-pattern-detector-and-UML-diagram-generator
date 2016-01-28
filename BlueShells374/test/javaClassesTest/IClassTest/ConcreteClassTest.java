package javaClassesTest.IClassTest;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Field;
import problem.javaClasses.Method;

public class ConcreteClassTest {
	
	@Test
	public void test() {
		IClass abs = new ConcreteClass();
		IField f = new Field();
		f.setName("localTest");
		IMethod m = new Method();
		m.setName("testMethod");
		abs.addIField(f);
		abs.addIMethod(m);
		abs.addInterface("ITest");
		assertEquals(abs.getIField().size(), 1);
		assertEquals(abs.getIField().iterator().next(), f);
		assertEquals(abs.getIMethods().size(), 1);
		assertEquals(abs.getIMethods().iterator().next(), m);
		assertEquals(abs.getInterface().size(), 1);
		assertEquals(abs.getInterface().iterator().next(), "ITest");
	}

}
