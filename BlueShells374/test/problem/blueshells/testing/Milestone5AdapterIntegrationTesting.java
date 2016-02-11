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
import problem.javaClasses.MethodContainer;
import problem.javaClasses.Model;
import problem.patterns.AdapterPattern;
import problem.visitor.AdapterVisitor;
import problem.visitor.DecoratorVisitor;
import problem.visitor.IInvoker;
import problem.visitor.SingletonVisitor;

public class Milestone5AdapterIntegrationTesting {

	@Test
	public void Simple_Adapter() {
		IModel m = new Model();

		// make classes
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adaptee");
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");

		// make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);

		// make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		MethodContainer m2In = new MethodContainer();
		m2In.setGoingFromClass("Adapter");
		m2In.setGoingToClass("Adaptee");
		m2.addInnerCall(m2In);
		Adaptee.addIMethod(m1);
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");
		IRelation hasRelation = new HasRelation();
		hasRelation.setFromObject("Adapter");
		hasRelation.setToObject("Adaptee");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addClass(Adaptee);
		m.addRelation(hasRelation);
		m.addRelation(targetImplement);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test for patterns
		assertEquals(1, ITarget.getPatterns().size());
		if (ITarget.getPatterns().size() == 1) {
			assertTrue(ITarget.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) ITarget.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter.getPatterns().size());
		if (Adapter.getPatterns().size() == 1) {
			assertTrue(Adapter.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adapter.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
		assertEquals(1, Adaptee.getPatterns().size());
		if (Adaptee.getPatterns().size() == 1) {
			assertTrue(Adaptee.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adaptee.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adaptee\\>\\>");
		}
	}
	
	@Test
	public void No_Adapter_not_enough_Method_Calls() {
		IModel m = new Model();

		// make classes
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adaptee");
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");

		// make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);

		// make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		Adaptee.addIMethod(m1);
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");
		IRelation hasRelation = new HasRelation();
		hasRelation.setFromObject("Adapter");
		hasRelation.setToObject("Adaptee");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addClass(Adaptee);
		m.addRelation(hasRelation);
		m.addRelation(targetImplement);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test for patterns
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());
	}

	@Test
	public void Multiple_Adapters() {
		IModel m = new Model();

		// make classes
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adaptee");
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");
		IClass Adaptee2 = new ConcreteClass();
		Adaptee2.setClassName("Adaptee2");
		IClass Adapter2 = new ConcreteClass();
		Adapter2.setClassName("Adapter2");
		Adapter2.addInterface("ITarget2");
		IClass ITarget2 = new ConcreteClass();
		ITarget2.setClassName("ITarget2");

		// make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);
		IField adaptee2 = new Field();
		adaptee2.setDesc("Adaptee2");
		adaptee2.setName("a2");
		Adapter2.addIField(adaptee2);

		// make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		MethodContainer m2In = new MethodContainer();
		m2In.setGoingFromClass("Adapter");
		m2In.setGoingToClass("Adaptee");
		m2.addInnerCall(m2In);
		Adaptee.addIMethod(m1);
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);
		IMethod m3 = new Method();
		m3.setDesc("()V;");
		m3.setName("m2");
		IMethod m4 = new Method();
		m4.setDesc("()V;");
		m4.setName("method2");
		MethodContainer m4In = new MethodContainer();
		m4In.setGoingFromClass("Adapter2");
		m4In.setGoingToClass("Adaptee2");
		m4.addInnerCall(m4In);
		Adaptee2.addIMethod(m3);
		Adapter2.addIMethod(m4);
		ITarget2.addIMethod(m4);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");
		IRelation hasRelation = new HasRelation();
		hasRelation.setFromObject("Adapter");
		hasRelation.setToObject("Adaptee");
		IRelation targetImplement2 = new InterfaceRelation();
		targetImplement2.setFromObject("Adapter2");
		targetImplement2.setToObject("ITarget2");
		IRelation hasRelation2 = new HasRelation();
		hasRelation2.setFromObject("Adapter2");
		hasRelation2.setToObject("Adaptee2");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addClass(Adaptee);
		m.addRelation(hasRelation);
		m.addRelation(targetImplement);
		m.addClass(ITarget2);
		m.addClass(Adapter2);
		m.addClass(Adaptee2);
		m.addRelation(hasRelation2);
		m.addRelation(targetImplement2);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());
		assertEquals(0, ITarget2.getPatterns().size());
		assertEquals(0, Adapter2.getPatterns().size());
		assertEquals(0, Adaptee2.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test for patterns
		assertEquals(1, ITarget.getPatterns().size());
		if (ITarget.getPatterns().size() == 1) {
			assertTrue(ITarget.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) ITarget.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter.getPatterns().size());
		if (Adapter.getPatterns().size() == 1) {
			assertTrue(Adapter.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adapter.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
		assertEquals(1, Adaptee.getPatterns().size());
		if (Adaptee.getPatterns().size() == 1) {
			assertTrue(Adaptee.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adaptee.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adaptee\\>\\>");
		}
		assertEquals(1, ITarget2.getPatterns().size());
		if (ITarget2.getPatterns().size() == 1) {
			assertTrue(ITarget2.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) ITarget2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter2.getPatterns().size());
		if (Adapter2.getPatterns().size() == 1) {
			assertTrue(Adapter2.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adapter2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
		assertEquals(1, Adaptee2.getPatterns().size());
		if (Adaptee2.getPatterns().size() == 1) {
			assertTrue(Adaptee2.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adaptee2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adaptee\\>\\>");
		}

	}

	@Test
	public void Complex_Adapter() {
		IModel m = new Model();

		// make classes
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adaptee");
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");
		IClass Adapter2 = new ConcreteClass();
		Adapter2.setClassName("Adapter2");
		Adapter2.addInterface("ITarget2");
		IClass ITarget2 = new ConcreteClass();
		ITarget2.setClassName("ITarget2");

		// make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);
		Adapter2.addIField(adaptee);

		// make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		MethodContainer m2In = new MethodContainer();
		m2In.setGoingFromClass("Adapter");
		m2In.setGoingToClass("Adaptee");
		m2.addInnerCall(m2In);
		IMethod m3 = new Method();
		MethodContainer m3In = new MethodContainer();
		m3In.setGoingFromClass("Adapter2");
		m3In.setGoingToClass("Adaptee");
		m3.addInnerCall(m3In);
		m3.setDesc("()V;");
		m3.setName("m3");
		Adaptee.addIMethod(m1);
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);
		Adapter2.addIMethod(m3);
		ITarget2.addIMethod(m3);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");
		IRelation hasRelation = new HasRelation();
		hasRelation.setFromObject("Adapter");
		hasRelation.setToObject("Adaptee");
		IRelation targetImplement2 = new InterfaceRelation();
		targetImplement2.setFromObject("Adapter2");
		targetImplement2.setToObject("ITarget2");
		IRelation hasRelation2 = new HasRelation();
		hasRelation2.setFromObject("Adapter2");
		hasRelation2.setToObject("Adaptee");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addClass(Adaptee);
		m.addRelation(hasRelation);
		m.addRelation(targetImplement);
		m.addClass(ITarget2);
		m.addClass(Adapter2);
		m.addRelation(targetImplement2);
		m.addRelation(hasRelation2);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());
		assertEquals(0, ITarget2.getPatterns().size());
		assertEquals(0, Adapter2.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);
		
		// test for patterns
		assertEquals(1, ITarget.getPatterns().size());
		if (ITarget.getPatterns().size() == 1) {
			assertTrue(ITarget.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) ITarget.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter.getPatterns().size());
		if (Adapter.getPatterns().size() == 1) {
			assertTrue(Adapter.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adapter.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
		assertEquals(2, Adaptee.getPatterns().size());
		if (Adaptee.getPatterns().size() == 2) {
			assertTrue(Adaptee.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adaptee.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adaptee\\>\\>");
			assertTrue(Adaptee.getPatterns().get(1) instanceof AdapterPattern);
			AdapterPattern p2 = (AdapterPattern) Adaptee.getPatterns().get(1);
			assertEquals(p2.getLabel(), "\\<\\<adaptee\\>\\>");
		}
		assertEquals(1, ITarget2.getPatterns().size());
		if (ITarget2.getPatterns().size() == 1) {
			assertTrue(ITarget2.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) ITarget2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<target\\>\\>");
		}
		assertEquals(1, Adapter2.getPatterns().size());
		if (Adapter2.getPatterns().size() == 1) {
			assertTrue(Adapter2.getPatterns().get(0) instanceof AdapterPattern);
			AdapterPattern p = (AdapterPattern) Adapter2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<adapter\\>\\>");
		}
	}

	@Test
	public void No_Adapter_empty_Class() {
		IModel m = new Model();

		// make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");

		// make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		IComponent.addIMethod(testMethod);

		// add classes/relations to model
		m.addClass(IComponent);

		// test no patterns before running
		assertEquals(IComponent.getPatterns().size(), 0);

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test that there is no pattern after visiting
		assertEquals(0, IComponent.getPatterns().size());
	}

	@Test
	public void Incomplete_Adapter_No_Adaptee() {
		IModel m = new Model();

		// make classes
		IClass Adapter = new ConcreteClass();
		Adapter.setClassName("Adapter");
		Adapter.addInterface("ITarget");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");

		// make fields
		IField adaptee = new Field();
		adaptee.setDesc("Adaptee");
		adaptee.setName("a");
		Adapter.addIField(adaptee);

		// make methods
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		Adapter.addIMethod(m2);
		ITarget.addIMethod(m2);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adapter);
		m.addRelation(targetImplement);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test for patterns
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adapter.getPatterns().size());
	}

	@Test
	public void Incomplete_Adapter_No_Adapter() {
		IModel m = new Model();

		// make classes
		IClass Adaptee = new ConcreteClass();
		Adaptee.setClassName("Adapter");
		IClass ITarget = new ConcreteClass();
		ITarget.setClassName("ITarget");

		// make methods
		IMethod m1 = new Method();
		m1.setDesc("()V;");
		m1.setName("m1");
		IMethod m2 = new Method();
		m2.setDesc("()V;");
		m2.setName("method1");
		Adaptee.addIMethod(m1);
		ITarget.addIMethod(m2);

		// make relations
		IRelation targetImplement = new InterfaceRelation();
		targetImplement.setFromObject("Adapter");
		targetImplement.setToObject("ITarget");

		// add to model
		m.addClass(ITarget);
		m.addClass(Adaptee);
		m.addRelation(targetImplement);

		// test before running
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());

		// run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		IInvoker v3 = new AdapterVisitor(1);
		v1.write(m);
		v2.write(m);
		v3.write(m);

		// test for patterns
		assertEquals(0, ITarget.getPatterns().size());
		assertEquals(0, Adaptee.getPatterns().size());
	}
}
