package javaClassesTest.IClassTest;

import static org.junit.Assert.*;

import org.junit.Test;

import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.javaClasses.ConcreteClass;
import project.javaClasses.Field;
import project.javaClasses.Method;

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
