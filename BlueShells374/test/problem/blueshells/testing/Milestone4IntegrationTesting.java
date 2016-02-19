package problem.blueshells.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import project.asm.Config;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.IModel;
import project.interfaces.IPhase;
import project.interfaces.IRelation;
import project.javaClasses.ConcreteClass;
import project.javaClasses.Field;
import project.javaClasses.Method;
import project.javaClasses.Model;
import project.javaClasses.UsesRelation;
import project.patterns.SingletonPattern;
import project.visitor.SingletonVisitor;

public class Milestone4IntegrationTesting {
	
	@Test 
	public void Simple_Singleton() throws IOException{
		IModel m = new Model();
		
		IClass singleton = new ConcreteClass();
		singleton.setClassName("singleton");
		
		IField singletonField = new Field();
		singletonField.setDesc("singleton");
		singletonField.setName("instance");
		singleton.addIField(singletonField);
		
		IMethod getInstance = new Method();
		getInstance.setReturnType("singleton");
		getInstance.setDesc("()Lsingleton;");
		getInstance.setName("getInstance");
		singleton.addIMethod(getInstance);
		
		m.addClass(singleton);
		//test no patterns before running
		assertEquals(singleton.getPatterns().size(), 0);
		
		IPhase v = new SingletonVisitor();
		Config config = new Config();
		v.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, singleton.getPatterns().size());
		if (singleton.getPatterns().size() == 1){
			assertTrue(singleton.getPatterns().get(0) instanceof SingletonPattern);
		}
	}
	
	@Test
	public void Advanced_Singleton(){
		IModel m = new Model();
		
		IClass client = new ConcreteClass();
		client.setClassName("client");
		IClass singleton = new ConcreteClass();
		singleton.setClassName("singleton");
		
		IField singletonField = new Field();
		singletonField.setDesc("singleton");
		singletonField.setName("instance");
		singleton.addIField(singletonField);
		IField randomField = new Field();
		randomField.setDesc("blah");
		randomField.setName("blahField");
		singleton.addIField(randomField);
		
		IMethod getInstance = new Method();
		getInstance.setReturnType("singleton");
		getInstance.setDesc("()Lsingleton;");
		getInstance.setName("getInstance");
		singleton.addIMethod(getInstance);
		IMethod useSingleton = new Method();
		useSingleton.setReturnType("singleton");
		useSingleton.setDesc("()Lsingleton;");
		useSingleton.setName("useSingleton");
		client.addIMethod(useSingleton);
		
		IRelation clientUsesSingleton = new UsesRelation();
		clientUsesSingleton.setFromObject("client");
		clientUsesSingleton.setToObject("singleton");
		
		m.addRelation(clientUsesSingleton);
		m.addClass(client);
		m.addClass(singleton);
		//test no patterns before running
		assertEquals(singleton.getPatterns().size(), 0);
		
		IPhase v = new SingletonVisitor();
		Config config = new Config();
		v.execute(config, m);
		
		//test that there is a pattern
		assertEquals(1, singleton.getPatterns().size());
		if (singleton.getPatterns().size() == 1){
			assertTrue(singleton.getPatterns().get(0) instanceof SingletonPattern);
		}
	}
	
	@Test
	public void No_Singleton_with_Empty_Class(){
		IModel m = new Model();
		
		IClass nosingleton = new ConcreteClass();
		nosingleton.setClassName("empty");
		
		m.addClass(nosingleton);
		//test no patterns before running
		assertEquals(nosingleton.getPatterns().size(), 0);
		
		IPhase v = new SingletonVisitor();
		Config config = new Config();
		v.execute(config, m);
		
		//test that there is no pattern
		assertEquals(0, nosingleton.getPatterns().size());
	}
	
	@Test
	public void No_Singleton_with_Class_Returns_Itself(){
		IModel m = new Model();
		
		IClass test = new ConcreteClass();
		test.setClassName("test");
		
		IMethod getInstance = new Method();
		getInstance.setReturnType("test");
		getInstance.setReturnType("");
		getInstance.setDesc("()Ltest;");
		getInstance.setName("getInstance");
		test.addIMethod(getInstance);
		
		
		m.addClass(test);
		//test no patterns before running
		assertEquals(test.getPatterns().size(), 0);
		
		IPhase v = new SingletonVisitor();
		Config config = new Config();
		v.execute(config, m);
		
		//test that there is no pattern
		assertEquals(0, test.getPatterns().size());
	}
	
	@Test
	public void No_Singleton_with_Class_has_itself(){
		IModel m = new Model();
		
		IClass test = new ConcreteClass();
		test.setClassName("has");
		
		
		IField singletonField = new Field();
		singletonField.setDesc("test");
		singletonField.setName("instance");
		test.addIField(singletonField);
		
		
		m.addClass(test);
		//test no patterns before running
		assertEquals(test.getPatterns().size(), 0);
		
		IPhase v = new SingletonVisitor();
		Config config = new Config();
		v.execute(config, m);
		
		//test that there is no pattern
		assertEquals(0, test.getPatterns().size());
	}
	
}
