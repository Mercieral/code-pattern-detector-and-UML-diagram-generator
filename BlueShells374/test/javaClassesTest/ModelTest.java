package javaClassesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import problem.interfaces.IClass;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Interface;
import problem.javaClasses.Model;

public class ModelTest {

	//Model 
	@Test
	public void testClassesList() {
		Model m = new Model();
		assertEquals(m.getClasses(), new ArrayList<IClass>());
		IClass c1 = new ConcreteClass();
		IClass c2 = new Interface();
		m.addClass(c1);
		m.addClass(c2);
		assertEquals(m.getClasses().size(), 2);
	}

}
