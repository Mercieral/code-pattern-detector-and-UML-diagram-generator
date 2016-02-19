package javaClassesTest;

import static org.junit.Assert.*;

import org.junit.Test;

import project.javaClasses.MethodContainer;

public class MethodContainerTest {

	@Test
	public void generalTest() {
		MethodContainer mc = new MethodContainer();
		mc.setGoingFromClass("CLASS FROM");
		mc.setGoingToClass("TO");
		mc.setGoingToMethod("GOING");
		mc.setInstantiation(false);
		assertEquals(mc.getGoingFromClass(), "CLASS FROM");
		assertEquals(mc.getGoingToClass(), "TO");
		assertEquals(mc.getGoingToMethod(), "GOING");
		assertEquals(mc.isInstantiation(), false);
	}

}
