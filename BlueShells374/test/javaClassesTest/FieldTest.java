package javaClassesTest;

import static org.junit.Assert.*;

import org.junit.Test;

import project.interfaces.IField;
import project.javaClasses.Field;

public class FieldTest {

	@Test
	public void setGetTest() {
		IField f = new Field();
		f.setAccessLevel("public");
		f.setDesc("desc");
		f.setName("name");
		f.setSignature("sign");
		f.setValue(1);
		assertEquals(f.getAccessLevel(), "public");
		assertEquals(f.getDesc(), "desc");
		assertEquals(f.getName(), "name");
		assertEquals(f.getSignature(), "sign");
		assertEquals(f.getValue(), 1);
	}

}
