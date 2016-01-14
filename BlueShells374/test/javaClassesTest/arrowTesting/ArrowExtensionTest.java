package javaClassesTest.arrowTesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import problem.interfaces.IArrow;
import problem.javaClasses.ArrowExtension;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrowExtensionTest {
	private IArrow ext;

	private static final String FROM_OBJECT = "FROM";
	private static final String TO_OBJECT = "TO";

	@Before
	public void setUp() throws Exception {
		this.ext = new ArrowExtension();
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
				+ "\t\t[arrowhead=\"onormal\"];\n";
		assertEquals(this.ext.drawArrow(), result);
	}

}
