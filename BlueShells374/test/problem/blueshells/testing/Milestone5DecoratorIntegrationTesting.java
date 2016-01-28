package problem.blueshells.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.Field;
import problem.javaClasses.HasRelation;
import problem.javaClasses.Method;
import problem.javaClasses.Model;
import problem.patterns.DecoratorPattern;
import problem.visitor.DecoratorVisitor;
import problem.visitor.IInvoker;
import problem.visitor.SingletonVisitor;

public class Milestone5DecoratorIntegrationTesting {

	@Test
	public void Decorator_with_no_Concrete_Decorators() {
		IModel m = new Model();
		
		//make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");
		IClass ConcreteComponent = new ConcreteClass();
		ConcreteComponent.setClassName("ConcreteComponent");
		ConcreteComponent.addInterface("IComponent");
		IClass abstractDecorator = new ConcreteClass();
		abstractDecorator.setClassName("abstractDecorator");
		abstractDecorator.addInterface("IComponent");

		
		//make fields
		IField component = new Field();
		component.setDesc("IComponent");
		component.setName("c");
		abstractDecorator.addIField(component);
		
		
		//make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		IComponent.addIMethod(testMethod);
		abstractDecorator.addIMethod(testMethod);
		ConcreteComponent.addIMethod(testMethod);
		
		//make relations
		IRelation abstractToComponent = new ExtensionRelation();
		abstractToComponent.setFromObject("abstractDecorator");
		abstractToComponent.setToObject("IComponent");
		IRelation abstractToComponent2 = new HasRelation();
		abstractToComponent2.setFromObject("abstractDecorator");
		abstractToComponent2.setToObject("IComponent");
		IRelation concreteToComponent = new ExtensionRelation();
		abstractToComponent.setFromObject("ConcreteComponent");
		abstractToComponent.setToObject("IComponent");

		
		//add classes/relations to model
		m.addClass(IComponent);
		m.addClass(abstractDecorator);
		m.addClass(ConcreteComponent);
		m.addRelation(abstractToComponent);
		m.addRelation(abstractToComponent2);
		m.addRelation(concreteToComponent);
		
		//test no patterns before running
		assertEquals(abstractDecorator.getPatterns().size(), 0);
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(ConcreteComponent.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		v1.write(m);
		v2.write(m);
		
		//test that there is a pattern
		assertEquals(1, IComponent.getPatterns().size());
		if (IComponent.getPatterns().size() == 1){
			assertTrue(IComponent.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)IComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<component\\>\\>");
		}
		assertEquals(1, abstractDecorator.getPatterns().size());
		if (abstractDecorator.getPatterns().size() == 1){
			assertTrue(abstractDecorator.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)abstractDecorator.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		//concrete component is not part of decorator pattern
		assertEquals(0, ConcreteComponent.getPatterns().size());
	}
	
	@Test
	public void No_Decorator_empty_Class() {
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
		v1.write(m);
		v2.write(m);
		
		//test that there is no pattern after visiting
		assertEquals(0, IComponent.getPatterns().size());
	}
	
	@Test
	public void Simple_Decorator(){
		IModel m = new Model();
		
		//make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");
		IClass ConcreteComponent = new ConcreteClass();
		ConcreteComponent.setClassName("ConcreteComponent");
		ConcreteComponent.addInterface("IComponent");
		IClass abstractDecorator = new ConcreteClass();
		abstractDecorator.setClassName("abstractDecorator");
		abstractDecorator.addInterface("IComponent");
		IClass concreteDecorator1 = new ConcreteClass();
		concreteDecorator1.setClassName("concreteDecorator1");
		concreteDecorator1.setExtension("abstractDecorator");
		IClass concreteDecorator2 = new ConcreteClass();
		concreteDecorator2.setClassName("concreteDecorator2");
		concreteDecorator2.setExtension("abstractDecorator");
		
		//make fields
		IField component = new Field();
		component.setDesc("IComponent");
		component.setName("c");
		abstractDecorator.addIField(component);
		
		
		//make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		IComponent.addIMethod(testMethod);
		abstractDecorator.addIMethod(testMethod);
		ConcreteComponent.addIMethod(testMethod);
		concreteDecorator1.addIMethod(testMethod);
		concreteDecorator2.addIMethod(testMethod);
		
		//make relations
		IRelation abstractToComponent = new ExtensionRelation();
		abstractToComponent.setFromObject("abstractDecorator");
		abstractToComponent.setToObject("IComponent");
		IRelation abstractToComponent2 = new HasRelation();
		abstractToComponent2.setFromObject("abstractDecorator");
		abstractToComponent2.setToObject("IComponent");
		IRelation concreteToComponent = new ExtensionRelation();
		abstractToComponent.setFromObject("ConcreteComponent");
		abstractToComponent.setToObject("IComponent");
		IRelation c1toDecorator = new ExtensionRelation();
		c1toDecorator.setFromObject("concreteDecorator1");
		c1toDecorator.setToObject("abstractDecorator");
		IRelation c2toDecorator = new ExtensionRelation();
		c2toDecorator.setFromObject("concreteDecorator2");
		c2toDecorator.setToObject("abstractDecorator");
		
		//add classes/relations to model
		m.addClass(IComponent);
		m.addClass(abstractDecorator);
		m.addClass(ConcreteComponent);
		m.addClass(concreteDecorator2);
		m.addClass(concreteDecorator1);
		m.addRelation(abstractToComponent);
		m.addRelation(abstractToComponent2);
		m.addRelation(concreteToComponent);
		m.addRelation(c1toDecorator);
		m.addRelation(c2toDecorator);
		
		//test no patterns before running
		assertEquals(abstractDecorator.getPatterns().size(), 0);
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(ConcreteComponent.getPatterns().size(), 0);
		assertEquals(concreteDecorator1.getPatterns().size(), 0);
		assertEquals(concreteDecorator2.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IInvoker v1 = new SingletonVisitor();
		IInvoker v2 = new DecoratorVisitor();
		v1.write(m);
		v2.write(m);
		
		//test that there is a pattern
		assertEquals(1, IComponent.getPatterns().size());
		if (IComponent.getPatterns().size() == 1){
			assertTrue(IComponent.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)IComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<component\\>\\>");
		}
		assertEquals(1, abstractDecorator.getPatterns().size());
		if (abstractDecorator.getPatterns().size() == 1){
			assertTrue(abstractDecorator.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)abstractDecorator.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		assertEquals(1, concreteDecorator1.getPatterns().size());
		if (concreteDecorator1.getPatterns().size() == 1){
			assertTrue(concreteDecorator1.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)concreteDecorator1.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		assertEquals(1, concreteDecorator2.getPatterns().size());
		if (concreteDecorator2.getPatterns().size() == 1){
			assertTrue(concreteDecorator2.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)concreteDecorator2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		//concrete component is not part of decorator pattern
		assertEquals(0, ConcreteComponent.getPatterns().size());
	}

}
