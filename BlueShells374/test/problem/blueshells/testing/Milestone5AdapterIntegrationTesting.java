package problem.blueshells.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.Field;
import problem.javaClasses.HasRelation;
import problem.javaClasses.InterfaceRelation;
import problem.javaClasses.Method;
import problem.javaClasses.Model;
import problem.patterns.AdapterPattern;
import problem.visitor.AdapterVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.IInvoker;
import problem.visitor.SingletonVisitor;

public class Milestone5AdapterIntegrationTesting {

	@Test
	public void Simple_Adapter(){
		IModel m = new Model();
		
		//make classes 
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adaptee");
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");
		
		//make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);
		
		//make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		Adaptee.addIMethod(m1);
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);
		
		//make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");
		IRelation hasRelation = new HasRelation();
		hasRelation.setFromObject("Adapter");
		hasRelation.setToObject("Adaptee");
		
		//add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addClass(Adaptee);
		m.addRelation(hasRelation);
		m.addRelation(targetImplement);
		
		//test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());
		
		//run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor();
		v1.write(m);
		v2.write(m);
		v3.write(m);
		
		//test for patterns
		assertEquals(1, ITarget.getPatterns().size());
		if (ITarget.getPatterns().size() == 1){
			assertTrue(ITarget.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern)ITarget.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter.getPatterns().size());
		if (Adapter.getPatterns().size() == 1){
			assertTrue(Adapter.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern)Adapter.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
		assertEquals(1, Adaptee.getPatterns().size());
		if (Adaptee.getPatterns().size() == 1){
			assertTrue(Adaptee.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern)Adaptee.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adaptee\\>\\>");
		}
	}
	
	@Test
	public void No_Adapter_empty_Class() {
		IModel m = new Model();
		
		//make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");
		
		//make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		IComponent.addIMethod(testMethod);
		
		//add classes/relations to model
		m.addClass(IComponent);
		
		//test no patterns before running
		assertEquals(IComponent.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor();
		v1.write(m);
		v2.write(m);
		v3.write(m);
		
		//test that there is no pattern after visiting
		assertEquals(0, IComponent.getPatterns().size());
	}
	
	@Test
	public void Incomplete_Adapter(){
		IModel m = new Model();
		
		//make classes 
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");
		
		//make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);
		
		//make methods
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);
		
		//make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");

		//add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addRelation(targetImplement);
		
		//test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		
		//run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor();
		v1.write(m);
		v2.write(m);
		v3.write(m);
		
		//test for patterns
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
	}
}
