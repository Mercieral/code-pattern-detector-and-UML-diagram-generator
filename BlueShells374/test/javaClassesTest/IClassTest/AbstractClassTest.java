package javaClassesTest.IClassTest;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import problem.interfaces.IArrow;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.javaClasses.AbstractClass;
import problem.javaClasses.ArrowHas;
import problem.javaClasses.Field;
import problem.javaClasses.Method;

public class AbstractClassTest {

	private static final String FROM_OBJECT = "FROM";
	private static final String TO_OBJECT = "TO";

	@Test
	public void addTests() {
		IClass abs = new AbstractClass();
		IArrow a = new ArrowHas();
		a.setFromObject(FROM_OBJECT);
		a.setToObject(TO_OBJECT);
		IField f = new Field();
		f.setName("localTest");
		IMethod m = new Method();
		m.setName("testMethod");
		abs.addArrow(a);
		abs.addIField(f);
		abs.addIMethod(m);
		abs.addInterface("ITest");
		Collection<IArrow> lA = abs.getArrows();
		assertEquals(lA.size(), 1);
		assertEquals(lA.iterator().next(), a);
		assertEquals(abs.getIField().size(), 1);
		assertEquals(abs.getIField().iterator().next(), f);
		assertEquals(abs.getIMethods().size(), 1);
		assertEquals(abs.getIMethods().iterator().next(), m);
		assertEquals(abs.getInterface().size(), 1);
		assertEquals(abs.getInterface().iterator().next(), "ITest");
	}

}
