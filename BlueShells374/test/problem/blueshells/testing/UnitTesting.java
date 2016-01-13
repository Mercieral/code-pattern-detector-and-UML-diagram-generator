package problem.blueshells.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.ClassMethodVisitor;
import problem.asm.myMethodVisitor;
import problem.interfaces.IClass;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Interface;
import problem.javaClasses.Method;
import problem.javaClasses.Model;

public class UnitTesting {

	private static final MethodVisitor MethodVisitor = null;

	//Model 
	@Test
	public void testModelClassesList() {
		Model m = new Model();
		assertEquals(m.getClasses(), new ArrayList<IClass>());
		IClass c1 = new ConcreteClass();
		IClass c2 = new Interface();
		m.addClass(c1);
		m.addClass(c2);
		assertEquals(m.getClasses().size(), 2);
	}
	
	//Method
	@Test
	public void testMethodClass() {
		Method m = new Method();
		assertEquals(m.getAccessLevel(), null);
		assertEquals(m.getDesc(), null);
		assertEquals(m.getName(), null);
		assertEquals(m.getReturnType(), null);
		m.setAccessLevel("public");
		m.setDesc("javaLangString");
		m.setName("myName");
		m.setReturnType("void");
		m.addArgument("arg1");
		m.addArgument("arg2");
		assertEquals(m.getAccessLevel(), "public");
		assertEquals(m.getDesc(), "javaLangString");
		assertEquals(m.getName(), "myName");
		assertEquals(m.getReturnType(), "void");
		assertEquals(m.getArguments().size(), 2);
		
	}
	
	//ClassMethodVisitor
	@Test
	public void testClassMethodVisitor(){
//		ClassMethodVisitor init = new ClassMethodVisitor(327680);
//		IClass c = new ConcreteClass();
//		c.setClassName("testClass");
//		String[] s = {};
//		String[] exceptions = {"IOException"};
//		ClassMethodVisitor test = new ClassMethodVisitor(327680, init, c, s);
//		test.visitMethod(1, "method", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", exceptions);
//		assertEquals(c.getIMethods(), 1);
		
		
	}

}
