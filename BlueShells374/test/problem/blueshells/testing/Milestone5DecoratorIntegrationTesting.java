package problem.blueshells.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.interfaces.IRelation;
import project.javaClasses.ConcreteClass;
import project.javaClasses.ExtensionRelation;
import project.javaClasses.Field;
import project.javaClasses.HasRelation;
import project.javaClasses.Method;
import project.javaClasses.Model;
import project.patterns.DecoratorPattern;
import project.visitor.DecoratorVisitor;
import project.visitor.SingletonVisitor;

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
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
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
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
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
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
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
	
	@Test
	public void Decorator_with_no_Component() {
		IModel m = new Model();
		
		//make classes
		IClass abstractDecorator = new ConcreteClass();
		abstractDecorator.setClassName("abstractDecorator");
		abstractDecorator.addInterface("IComponent");
		IClass concreteDecorator1 = new ConcreteClass();
		concreteDecorator1.setClassName("concreteDecorator1");
		concreteDecorator1.setExtension("abstractDecorator");
		IClass concreteDecorator2 = new ConcreteClass();
		concreteDecorator2.setClassName("concreteDecorator2");
		concreteDecorator2.setExtension("abstractDecorator");
		
		//make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		abstractDecorator.addIMethod(testMethod);
		concreteDecorator1.addIMethod(testMethod);
		concreteDecorator2.addIMethod(testMethod);
		
		//make relations
		IRelation c1toDecorator = new ExtensionRelation();
		c1toDecorator.setFromObject("concreteDecorator1");
		c1toDecorator.setToObject("abstractDecorator");
		IRelation c2toDecorator = new ExtensionRelation();
		c2toDecorator.setFromObject("concreteDecorator2");
		c2toDecorator.setToObject("abstractDecorator");
		
		//add classes/relations to model
		m.addClass(abstractDecorator);
		m.addClass(concreteDecorator2);
		m.addClass(concreteDecorator1);
		m.addRelation(c1toDecorator);
		m.addRelation(c2toDecorator);
		
		//test no patterns before running
		assertEquals(abstractDecorator.getPatterns().size(), 0);
		assertEquals(concreteDecorator1.getPatterns().size(), 0);
		assertEquals(concreteDecorator2.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
		//test that there is no pattern
		assertEquals(0, abstractDecorator.getPatterns().size());
		assertEquals(0, concreteDecorator1.getPatterns().size());
		assertEquals(0, concreteDecorator2.getPatterns().size());
	}
	
	@Test
	public void Multiple_Decorators() {
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
		IClass abstractDecorator2 = new ConcreteClass();
		abstractDecorator2.setClassName("abstractDecorator2");
		abstractDecorator2.addInterface("IComponent");
		IClass concreteDecorator3 = new ConcreteClass();
		concreteDecorator3.setClassName("concreteDecorator3");
		concreteDecorator3.setExtension("abstractDecorator2");
		IClass concreteDecorator4 = new ConcreteClass();
		concreteDecorator4.setClassName("concreteDecorator4");
		concreteDecorator4.setExtension("abstractDecorator2");
		
		//make fields
		IField component = new Field();
		component.setDesc("IComponent");
		component.setName("c");
		abstractDecorator.addIField(component);
		abstractDecorator2.addIField(component);
		
		
		//make Methods
		IMethod testMethod = new Method();
		testMethod.setDesc("()V;");
		testMethod.setName("testMethod");
		IComponent.addIMethod(testMethod);
		abstractDecorator.addIMethod(testMethod);
		ConcreteComponent.addIMethod(testMethod);
		concreteDecorator1.addIMethod(testMethod);
		concreteDecorator2.addIMethod(testMethod);
		abstractDecorator2.addIMethod(testMethod);
		concreteDecorator3.addIMethod(testMethod);
		concreteDecorator4.addIMethod(testMethod);
		
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
		IRelation abstractToComponent3 = new ExtensionRelation();
		abstractToComponent3.setFromObject("abstractDecorator2");
		abstractToComponent3.setToObject("IComponent");
		IRelation abstractToComponent4 = new HasRelation();
		abstractToComponent4.setFromObject("abstractDecorator2");
		abstractToComponent4.setToObject("IComponent");
		IRelation c3toDecorator = new ExtensionRelation();
		c3toDecorator.setFromObject("concreteDecorator3");
		c3toDecorator.setToObject("abstractDecorator2");
		IRelation c4toDecorator = new ExtensionRelation();
		c4toDecorator.setFromObject("concreteDecorator4");
		c4toDecorator.setToObject("abstractDecorator2");
		
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
		m.addClass(abstractDecorator2);
		m.addClass(concreteDecorator3);
		m.addClass(concreteDecorator4);
		m.addRelation(abstractToComponent3);
		m.addRelation(abstractToComponent4);
		m.addRelation(c3toDecorator);
		m.addRelation(c4toDecorator);
		
		//test no patterns before running
		assertEquals(abstractDecorator.getPatterns().size(), 0);
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(ConcreteComponent.getPatterns().size(), 0);
		assertEquals(concreteDecorator1.getPatterns().size(), 0);
		assertEquals(concreteDecorator2.getPatterns().size(), 0);
		assertEquals(abstractDecorator2.getPatterns().size(), 0);
		assertEquals(concreteDecorator3.getPatterns().size(), 0);
		assertEquals(concreteDecorator4.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
		//test that there is a pattern
		assertEquals(2, IComponent.getPatterns().size());
		if (IComponent.getPatterns().size() == 2){
			assertTrue(IComponent.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)IComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<component\\>\\>");
			assertTrue(IComponent.getPatterns().get(1) instanceof DecoratorPattern);
			DecoratorPattern p2 = (DecoratorPattern)IComponent.getPatterns().get(1);
			assertEquals(p2.getLabel(), "\\<\\<component\\>\\>");
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
		assertEquals(1, abstractDecorator2.getPatterns().size());
		if (abstractDecorator2.getPatterns().size() == 1){
			assertTrue(abstractDecorator2.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)abstractDecorator.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		assertEquals(1, concreteDecorator3.getPatterns().size());
		if (concreteDecorator3.getPatterns().size() == 1){
			assertTrue(concreteDecorator3.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)concreteDecorator1.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		assertEquals(1, concreteDecorator4.getPatterns().size());
		if (concreteDecorator4.getPatterns().size() == 1){
			assertTrue(concreteDecorator4.getPatterns().get(0) instanceof DecoratorPattern);
			DecoratorPattern p = (DecoratorPattern)concreteDecorator2.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<decorator\\>\\>");
		}
		//concrete component is not part of decorator pattern
		assertEquals(0, ConcreteComponent.getPatterns().size());
	}
	
	@Test
	public void No_Decorator_No_Has_Relation() {
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
		IRelation concreteToComponent = new ExtensionRelation();
		abstractToComponent.setFromObject("ConcreteComponent");
		abstractToComponent.setToObject("IComponent");

		
		//add classes/relations to model
		m.addClass(IComponent);
		m.addClass(abstractDecorator);
		m.addClass(ConcreteComponent);
		m.addRelation(abstractToComponent);
		m.addRelation(concreteToComponent);

		
		//test no patterns before running
		assertEquals(abstractDecorator.getPatterns().size(), 0);
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(ConcreteComponent.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new SingletonVisitor();
		IPhase v2 = new DecoratorVisitor();
		Config config = new Config();
		v1.execute(config, m);
		v2.execute(config, m);
		
		//test that there is a pattern
		assertEquals(0, IComponent.getPatterns().size());
		assertEquals(0, abstractDecorator.getPatterns().size());
		//concrete component is not part of decorator pattern
		assertEquals(0, ConcreteComponent.getPatterns().size());
	}

}
