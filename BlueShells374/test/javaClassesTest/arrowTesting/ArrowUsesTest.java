package javaClassesTest.arrowTesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.interfaces.IArrow;
import problem.javaClasses.ArrowUses;

public class ArrowUsesTest {
	private IArrow ext;

	private static final String FROM_OBJECT = "FROM";
	private static final String TO_OBJECT = "TO";

	@Before
	public void setUp() throws Exception {
		this.ext = new ArrowUses();
	}

	@After
	public void tearDown() throws Exception {
		this.ext = null;
	}

	@Test
	public void setterGetterTest() {
		this.ext.setFromObject(FROM_OBJECT);
		this.ext.setToObject(TO_OBJECT);
		assertEquals(this.ext.getFromObject(), FROM_OBJECT);
		assertEquals(this.ext.getToObject(), TO_OBJECT);
	}

	@Test
	public void testDrawArrow() {
		this.ext.setFromObject(FROM_OBJECT);
		this.ext.setToObject(TO_OBJECT);
		String result = "	FROM -> TO" + "\n"
				+ "\t\t[arrowhead=\"vee\", style=\"dashed\"];\n";
		assertEquals(this.ext.drawArrow(), result);
	}
}
