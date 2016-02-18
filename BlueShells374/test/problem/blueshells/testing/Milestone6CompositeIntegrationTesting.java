package problem.blueshells.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.asm.Config;
import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IMethod;
import problem.interfaces.IModel;
import problem.interfaces.IPhase;
import problem.interfaces.IRelation;
import problem.javaClasses.ConcreteClass;
import problem.javaClasses.ExtensionRelation;
import problem.javaClasses.Field;
import problem.javaClasses.InterfaceRelation;
import problem.javaClasses.Method;
import problem.javaClasses.Model;
import problem.patterns.CompositePattern;
import problem.visitor.CompositeVisitor;

public class Milestone6CompositeIntegrationTesting {

	@Test
	public void Simple_Composite() {
		IModel m = new Model();
		
		//make classes
		IClass MyComponent = new ConcreteClass();
		MyComponent.setClassName("MyComponent");
		MyComponent.setAccessLevel(1057);
		IClass MyComposite = new ConcreteClass();
		MyComposite.setClassName("MyComposite");
		MyComposite.setExtension("MyComponent");
		IClass LeafA = new ConcreteClass();
		LeafA.setClassName("LeafA");
		LeafA.setExtension("MyComponent");
		IClass LeafB = new ConcreteClass();
		LeafB.setClassName("LeafB");
		LeafB.setExtension("MyComponent");
		
		//make fields
		IField compositeField = new Field();
		compositeField.setName("compositeField");
		compositeField.setSignature("Ljava/util/List<LMyComponent");
		MyComposite.addIField(compositeField);
		
		//make methods
		IMethod addMethod = new Method();
		addMethod.setDesc("(MyComponent)V;");
		addMethod.setName("add");
		MyComposite.addIMethod(addMethod);
		
		//make relations
		IRelation compositeToComponent = new ExtensionRelation();
		compositeToComponent.setFromObject("MyComposite");
		compositeToComponent.setToObject("MyComponent");
		IRelation leafAToComponent = new ExtensionRelation();
		leafAToComponent.setFromObject("LeafA");
		leafAToComponent.setToObject("MyComponent");
		IRelation leafBToComponent = new ExtensionRelation();
		leafBToComponent.setFromObject("LeafB");
		leafBToComponent.setToObject("MyComponent");
		
		//add Classes/relations to model
		m.addClass(MyComponent);
		m.addClass(MyComposite);
		m.addClass(LeafA);
		m.addClass(LeafB);
		m.addRelation(compositeToComponent);
		m.addRelation(leafAToComponent);
		m.addRelation(leafBToComponent);
		
		//test no patterns before running
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, MyComponent.getPatterns().size());
		if (MyComponent.getPatterns().size() == 1){
			assertTrue(MyComponent.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Component\\>\\>");
		}
		assertEquals(1, MyComposite.getPatterns().size());
		if (MyComposite.getPatterns().size() == 1){
			assertTrue(MyComposite.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComposite.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Composite\\>\\>");
		}
		assertEquals(1, LeafA.getPatterns().size());
		if (LeafA.getPatterns().size() == 1){
			assertTrue(LeafA.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafA.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}
		assertEquals(1, LeafB.getPatterns().size());
		if (LeafB.getPatterns().size() == 1){
			assertTrue(LeafB.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafB.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}		
	}
	
	@Test
	public void Composite_with_abstract_to_interface() {
		IModel m = new Model();
		
		//make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");
		IComponent.setAccessLevel(1537);
		IClass MyComponent = new ConcreteClass();
		MyComponent.setClassName("MyComponent");
		MyComponent.setAccessLevel(1057);
		MyComponent.addInterface("IComponent");
		IClass MyComposite = new ConcreteClass();
		MyComposite.setClassName("MyComposite");
		MyComposite.setExtension("MyComponent");
		IClass LeafA = new ConcreteClass();
		LeafA.setClassName("LeafA");
		LeafA.setExtension("MyComponent");
		IClass LeafB = new ConcreteClass();
		LeafB.setClassName("LeafB");
		LeafB.setExtension("MyComponent");
		
		//make fields
		IField compositeField = new Field();
		compositeField.setName("compositeField");
		compositeField.setSignature("Ljava/util/List<LIComponent");
		MyComposite.addIField(compositeField);
		
		//make methods
		IMethod addMethod = new Method();
		addMethod.setDesc("(IComponent)V;");
		addMethod.setName("add");
		MyComposite.addIMethod(addMethod);
		
		//make relations
		IRelation componentToIComponent = new InterfaceRelation();
		componentToIComponent.setFromObject("MyComponent");
		componentToIComponent.setToObject("IComponent");
		IRelation compositeToComponent = new ExtensionRelation();
		compositeToComponent.setFromObject("MyComposite");
		compositeToComponent.setToObject("MyComponent");
		IRelation leafAToComponent = new ExtensionRelation();
		leafAToComponent.setFromObject("LeafA");
		leafAToComponent.setToObject("MyComponent");
		IRelation leafBToComponent = new ExtensionRelation();
		leafBToComponent.setFromObject("LeafB");
		leafBToComponent.setToObject("MyComponent");
		
		//add Classes/relations to model
		m.addClass(IComponent);
		m.addClass(MyComponent);
		m.addClass(MyComposite);
		m.addClass(LeafA);
		m.addClass(LeafB);
		m.addRelation(componentToIComponent);
		m.addRelation(compositeToComponent);
		m.addRelation(leafAToComponent);
		m.addRelation(leafBToComponent);
		
		//test no patterns before running
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, IComponent.getPatterns().size());
		if (IComponent.getPatterns().size() == 1){
			assertTrue(IComponent.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)IComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Component\\>\\>");
		}
		assertEquals(1, MyComponent.getPatterns().size());
		if (MyComponent.getPatterns().size() == 1){
			assertTrue(MyComponent.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Component\\>\\>");
		}
		assertEquals(1, MyComposite.getPatterns().size());
		if (MyComposite.getPatterns().size() == 1){
			assertTrue(MyComposite.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComposite.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Composite\\>\\>");
		}
		assertEquals(1, LeafA.getPatterns().size());
		if (LeafA.getPatterns().size() == 1){
			assertTrue(LeafA.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafA.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}
		assertEquals(1, LeafB.getPatterns().size());
		if (LeafB.getPatterns().size() == 1){
			assertTrue(LeafB.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafB.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}		
	}
	
	@Test
	public void Composite_with_only_interface() {
		IModel m = new Model();
		
		//make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");
		IComponent.setAccessLevel(1537);
		IClass MyComposite = new ConcreteClass();
		MyComposite.setClassName("MyComposite");
		MyComposite.addInterface("IComponent");
		IClass LeafA = new ConcreteClass();
		LeafA.setClassName("LeafA");
		LeafA.addInterface("IComponent");
		IClass LeafB = new ConcreteClass();
		LeafB.setClassName("LeafB");
		LeafB.addInterface("IComponent");
		
		//make fields
		IField compositeField = new Field();
		compositeField.setName("compositeField");
		compositeField.setSignature("Ljava/util/List<LIComponent");
		MyComposite.addIField(compositeField);
		
		//make methods
		IMethod addMethod = new Method();
		addMethod.setDesc("(IComponent)V;");
		addMethod.setName("add");
		MyComposite.addIMethod(addMethod);
		
		//make relations
		IRelation compositeToComponent = new InterfaceRelation();
		compositeToComponent.setFromObject("MyComposite");
		compositeToComponent.setToObject("IComponent");
		IRelation leafAToComponent = new InterfaceRelation();
		leafAToComponent.setFromObject("LeafA");
		leafAToComponent.setToObject("IComponent");
		IRelation leafBToComponent = new InterfaceRelation();
		leafBToComponent.setFromObject("LeafB");
		leafBToComponent.setToObject("IComponent");
		
		//add Classes/relations to model
		m.addClass(IComponent);
		m.addClass(MyComposite);
		m.addClass(LeafA);
		m.addClass(LeafB);
		m.addRelation(compositeToComponent);
		m.addRelation(leafAToComponent);
		m.addRelation(leafBToComponent);
		
		//test no patterns before running
		assertEquals(IComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, IComponent.getPatterns().size());
		if (IComponent.getPatterns().size() == 1){
			assertTrue(IComponent.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)IComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Component\\>\\>");
		}
		assertEquals(1, MyComposite.getPatterns().size());
		if (MyComposite.getPatterns().size() == 1){
			assertTrue(MyComposite.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComposite.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Composite\\>\\>");
		}
		assertEquals(1, LeafA.getPatterns().size());
		if (LeafA.getPatterns().size() == 1){
			assertTrue(LeafA.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafA.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}
		assertEquals(1, LeafB.getPatterns().size());
		if (LeafB.getPatterns().size() == 1){
			assertTrue(LeafB.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafB.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}	
	}
	
	@Test
	public void Composite_with_extended_Composites() {
		IModel m = new Model();
		
		//make classes
		IClass MyComponent = new ConcreteClass();
		MyComponent.setClassName("MyComponent");
		MyComponent.setAccessLevel(1057);
		IClass MyComposite = new ConcreteClass();
		MyComposite.setClassName("MyComposite");
		MyComposite.setExtension("MyComponent");
		IClass ConcreteComposite = new ConcreteClass();
		ConcreteComposite.setClassName("ConcreteComposite");
		ConcreteComposite.setExtension("MyComposite");
		IClass LeafA = new ConcreteClass();
		LeafA.setClassName("LeafA");
		LeafA.setExtension("MyComponent");
		IClass LeafB = new ConcreteClass();
		LeafB.setClassName("LeafB");
		LeafB.setExtension("MyComponent");
		
		//make fields
		IField compositeField = new Field();
		compositeField.setName("compositeField");
		compositeField.setSignature("Ljava/util/List<LMyComponent");
		MyComposite.addIField(compositeField);
		
		//make methods
		IMethod addMethod = new Method();
		addMethod.setDesc("(MyComponent)V;");
		addMethod.setName("add");
		MyComposite.addIMethod(addMethod);
		
		//make relations
		IRelation compositeToComponent = new ExtensionRelation();
		compositeToComponent.setFromObject("MyComposite");
		compositeToComponent.setToObject("MyComponent");
		IRelation compositeToComposite = new ExtensionRelation();
		compositeToComposite.setFromObject("ConcreteComposite");
		compositeToComposite.setToObject("MyComposite");
		IRelation leafAToComponent = new ExtensionRelation();
		leafAToComponent.setFromObject("LeafA");
		leafAToComponent.setToObject("MyComponent");
		IRelation leafBToComponent = new ExtensionRelation();
		leafBToComponent.setFromObject("LeafB");
		leafBToComponent.setToObject("MyComponent");
		
		//add Classes/relations to model
		m.addClass(MyComponent);
		m.addClass(MyComposite);
		m.addClass(ConcreteComposite);
		m.addClass(LeafA);
		m.addClass(LeafB);
		m.addRelation(compositeToComponent);
		m.addRelation(leafAToComponent);
		m.addRelation(leafBToComponent);
		m.addRelation(compositeToComposite);
		
		//test no patterns before running
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		assertEquals(ConcreteComposite.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, MyComponent.getPatterns().size());
		if (MyComponent.getPatterns().size() == 1){
			assertTrue(MyComponent.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComponent.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Component\\>\\>");
		}
		assertEquals(1, MyComposite.getPatterns().size());
		if (MyComposite.getPatterns().size() == 1){
			assertTrue(MyComposite.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)MyComposite.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Composite\\>\\>");
		}
		assertEquals(1, ConcreteComposite.getPatterns().size());
		if (ConcreteComposite.getPatterns().size() == 1){
			assertTrue(ConcreteComposite.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)ConcreteComposite.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Composite\\>\\>");
		}
		assertEquals(1, LeafA.getPatterns().size());
		if (LeafA.getPatterns().size() == 1){
			assertTrue(LeafA.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafA.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}
		assertEquals(1, LeafB.getPatterns().size());
		if (LeafB.getPatterns().size() == 1){
			assertTrue(LeafB.getPatterns().get(0) instanceof CompositePattern);
			CompositePattern p = (CompositePattern)LeafB.getPatterns().get(0);
			assertEquals(p.getLabel(), "\\<\\<Leaf\\>\\>");
		}	
	}
	
	@Test
	public void Empty_Class_No_Composite() {
		IModel m = new Model();

		// make classes
		IClass IComponent = new ConcreteClass();
		IComponent.setClassName("IComponent");


		// add classes/relations to model
		m.addClass(IComponent);

		// test no patterns before running
		assertEquals(IComponent.getPatterns().size(), 0);

		// run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);

		// test that there is no pattern after visiting
		assertEquals(0, IComponent.getPatterns().size());
	}
	
	@Test
	public void No_Leaf_No_Composite() {
IModel m = new Model();
		
		//make classes
		IClass MyComponent = new ConcreteClass();
		MyComponent.setClassName("MyComponent");
		MyComponent.setAccessLevel(1057);
		IClass MyComposite = new ConcreteClass();
		MyComposite.setClassName("MyComposite");
		MyComposite.setExtension("MyComponent");
		
		//make fields
		IField compositeField = new Field();
		compositeField.setName("compositeField");
		compositeField.setSignature("Ljava/util/List<LMyComponent");
		MyComposite.addIField(compositeField);
		
		//make methods
		IMethod addMethod = new Method();
		addMethod.setDesc("(MyComponent)V;");
		addMethod.setName("add");
		MyComposite.addIMethod(addMethod);
		
		//make relations
		IRelation compositeToComponent = new ExtensionRelation();
		compositeToComponent.setFromObject("MyComposite");
		compositeToComponent.setToObject("MyComponent");
		
		//add Classes/relations to model
		m.addClass(MyComponent);
		m.addClass(MyComposite);
		m.addRelation(compositeToComponent);
		//test no patterns before running
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//check that they still do not have the pattern
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(MyComposite.getPatterns().size(), 0);
		
		

	}
	
	@Test
	public void Leaf_Only_No_Composite() {
IModel m = new Model();
		
		//make classes
		IClass MyComponent = new ConcreteClass();
		MyComponent.setClassName("MyComponent");
		MyComponent.setAccessLevel(1057);
		IClass LeafA = new ConcreteClass();
		LeafA.setClassName("LeafA");
		LeafA.setExtension("MyComponent");
		IClass LeafB = new ConcreteClass();
		LeafB.setClassName("LeafB");
		LeafB.setExtension("MyComponent");
		
		//make relations
		IRelation leafAToComponent = new ExtensionRelation();
		leafAToComponent.setFromObject("LeafA");
		leafAToComponent.setToObject("MyComponent");
		IRelation leafBToComponent = new ExtensionRelation();
		leafBToComponent.setFromObject("LeafB");
		leafBToComponent.setToObject("MyComponent");
		
		//add Classes/relations to model
		m.addClass(MyComponent);
		m.addClass(LeafA);
		m.addClass(LeafB);
		m.addRelation(leafAToComponent);
		m.addRelation(leafBToComponent);
		
		//test no patterns before running
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
		
		//run the Pattern Visitors
		IPhase v1 = new CompositeVisitor();
		Config config = new Config();
		v1.execute(config, m);
		
		//test that there is no pattern
		assertEquals(MyComponent.getPatterns().size(), 0);
		assertEquals(LeafA.getPatterns().size(), 0);
		assertEquals(LeafB.getPatterns().size(), 0);
	}
	

}
