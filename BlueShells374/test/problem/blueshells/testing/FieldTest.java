package problem.blueshells.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.asm.Field;
import problem.asm.IField;

public class FieldTest {
	
	private IField testField;

	@Before
	public void setUp() throws Exception {
		this.testField = new Field();
	}

	@After
	public void tearDown() throws Exception {
		this.testField = null;
	}
	
	@Test
	public void emptyTesting(){
		assertEquals(this.testField.getName(), null);
		assertEquals(this.testField.getDesc(), null);
		assertEquals(this.testField.getSignature(), null);
		assertEquals(this.testField.getValue() ,null);
		assertEquals(this.testField.getAccessLevel(), null);
	}

	@Test
	public void genericTesting() {
		this.testField.setName("MULTIPLE_CONSTANT");
		assertEquals(this.testField.getName(), "MULTIPLE_CONSTANT");
		// FIXME
		fail("Not yet implemented");
	}

}
