package javaClassesTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.interfaces.IRelation;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.HasRelation;
import problem.javaClasses.InterfaceRelation;
import problem.javaClasses.UsesRelation;

public class RelationTests {

	private IRelation hasRelation;
	private IRelation extensionRelation;
	private IRelation interfaceRelation;
	private IRelation usesRelation;

	private String FROM_OBJECT = "FROM";
	private String TO_OBJECT = "TO";

	@Before
	public void setUp() throws Exception {
		this.hasRelation = new HasRelation();
		this.extensionRelation = new ExtensionRelation();
		this.interfaceRelation = new InterfaceRelation();
		this.usesRelation = new UsesRelation();
	}

	@After
	public void tearDown() throws Exception {
		this.hasRelation = null;
		this.extensionRelation = null;
		this.interfaceRelation = null;
		this.usesRelation = null;
	}

	@Test
	public void hasRelationTest() {
		this.hasRelation.setToObject(TO_OBJECT);
		this.hasRelation.setFromObject(FROM_OBJECT);
		assertEquals(this.hasRelation.drawRelation(), "\tFROM -> TO\n\t\t[arrowhead=\"vee\"];\n");
	}

	@Test
	public void usesRelationTest() {
		this.usesRelation.setToObject(TO_OBJECT);
		this.usesRelation.setFromObject(FROM_OBJECT);
		assertEquals(this.usesRelation.drawRelation(), "\tFROM -> TO\n\t\t[arrowhead=\"vee\", style=\"dashed\"];\n");
	}

	@Test
	public void interfaceRelationTest() {
		this.interfaceRelation.setToObject(TO_OBJECT);
		this.interfaceRelation.setFromObject(FROM_OBJECT);
		assertEquals(this.interfaceRelation.drawRelation(),
				"\tFROM -> TO\n\t\t[arrowhead=\"onormal\", style=\"dashed\"];\n");
	}

	@Test
	public void extensionRelationTest() {
		this.extensionRelation.setToObject(TO_OBJECT);
		this.extensionRelation.setFromObject(FROM_OBJECT);
		assertEquals(this.extensionRelation.drawRelation(),
				"\tFROM -> TO\n\t\t[arrowhead=\"onormal\"];\n");
	}

}
