package javaClassesTest.asmTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.asm.ClassMethodVisitor;
import problem.interfaces.IClass;
import problem.javaClasses.ConcreteClass;

public class ClassMethodVisitorTest {

	// ClassMethodVisitor
	@Test
	public void testClassMethodVisitor() {
		ClassMethodVisitor init = new ClassMethodVisitor(327680);
		IClass c = new ConcreteClass();
		c.setClassName("testClass");
		String[] s = {};
		String[] exceptions = { "IOException" };
		ClassMethodVisitor test = new ClassMethodVisitor(327680, init, c, s);
		test.visitMethod(1, "method",
				"(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V",
				"(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V",
				exceptions);
		assertEquals(c.getIMethods(), 1);
	}

}
