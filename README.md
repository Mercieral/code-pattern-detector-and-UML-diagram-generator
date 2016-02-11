# 374BlueShells
Repository for our CSSE 374 Software Design project.

------------------------------------------------------------

## Computer Science Joke

![Joke](http://imgs.xkcd.com/comics/commented.png)

------------------------------------------------------------

## Milestone 6.5

For this half-milestone, we added further detection to the Adapter Pattern. We also made all detectors implement one interface, the Invoker interface. This was in place for brute force detection or visitor detection, allowing either to be called without changing core code.

##### Milestone 7 steps

We are planning to do a GUI. We plan to make further progress during the weekend, once we all finish the exam and other homework. General framework started. 


------------------------------------------------------------
## Computer Science Joke

Look the adapter pattern
![Adapter Pattern](http://imgs.xkcd.com/comics/universal_converter_box.png)

![Wisdom of the ancients](http://imgs.xkcd.com/comics/wisdom_of_the_ancients.png)

------------------------------------------------------------

## Milestone 6

#### Deliverables and Locations 

1. Diagrams (Pictures below in smae order):
  1. [Auto-Generated UML Week 7-2 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_UML_7-2.png)
  2. [Auto-Generated UML java.awt and java.swing](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_AWT_Swing.png)
  3. [Auto-Generated UML of Project](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_UML_Project.png)
  4. [Manually Generated UML of Project](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Manually_Generated_UML_Project.png)

2. Pictures
  1. ![Auto-Generated UML Week 7-2 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_UML_7-2.png)
  2. ![Auto-Generated UML java.awt and java.swing](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_AWT_Swing.png)
  3. ![Auto-Generated UML of Project](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Generated_UML_Project.png)
  4. ![Manually Generated UML of Project](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Manually_Generated_UML_Project.png)

------------------------------------------------------------

#### Design (Milestone Evolution)

![Design Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone6/Manually_Generated_UML_Project.png)

We continued to use the visitor pattern. Left code the same in the framework/library unless bug was found or additional functionality that was not there in the first place.

We also made a brute force Adapter Pattern Detector. To see it in work, there are lines that can be uncommented. When GUI is made, an option could be made to see it separate. 

------------------------------------------------------------

#### Instructions 

Instructions are still similar to previous milestone.

To test the program, there is a console available for use. 

The client would input the desired classes, such as classes in a package, in as the initial arguments. Once that object is finished loading, a console type structure is loaded. From there, follow the prompts for desired inputs. 

To the see the brute force Adapter Pattern finder, code must be changed for the current console implementation.

```
Supported operations: Generator, Help, Quit 
Input command:> Generator
Generators: Supported generators - UML, Sequence 
Input generator:> UML
Generated graph, please refresh the input_output folder
Supported operations: Generator, Help, Quit 
Input command:> quit
```

------------------------------------------------------------

#### Code

###### DesignParser

```java
   CompositeVisitor compositeVisitor = new CompositeVisitor();
   compositeVisitor.write(model);
// BruteForceAdapterDetector adapterVisitor = new BruteForceAdapterDetector(model);
// adapterVisitor.adapterDetect();
```

###### BruteForceAdapterDetector

```java
package problem.visitor;

import java.util.ArrayList;
import java.util.List;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.interfaces.IRelation;
import problem.patterns.AdapterPattern;

public class BruteForceAdapterDetector {
	IModel model;
	private List<IClass> classList;

	public BruteForceAdapterDetector(IModel m){
		this.model = m;
		this.classList = new ArrayList<>();
	}
	
	public void adapterDetect(){
		for(IClass c: this.model.getClasses()){
			if (c.getInterface().size() == 1) {
				// Detecting adapter
				if (c.getExtension().equals("java/lang/Object")
						|| c.getExtension().equals("")) {
					// Doesn't have an extension
					if (c.getIField().size() == 1) {
						// Only 1 field
						this.classList.add(c);
						// Adds it when all three parts exist
					}
				}
			}
		}
		for (IClass c0 : this.classList) {
			boolean isAdaptee = false;
			IClass adaptee = null;
			boolean isTarget = false;
			IClass target = null;
			String fieldType = ((List<IField>) c0.getIField())
					.get(0).getDesc();
			String interfaceName = ((List<String>) c0
					.getInterface()).get(0);
			for (IClass c1 : this.model.getClasses()) {
				if (c1.getClassName().equals(fieldType)
						|| c1.getClassName().replace("/", ".")
								.equals(fieldType)) {
					isAdaptee = true;
					adaptee = c1;
					continue; // If this one, not the interface
				}
				if (c1.getClassName().equals(interfaceName)) {
					isTarget = true;
					target = c1;
				}
				if (target != null && adaptee != null) {
					break;
				}
			}
			if (isAdaptee && isTarget) {
				adaptee.addPattern(
						new AdapterPattern(adaptee.getClassName(),
								"\\<\\<adaptee\\>\\>"));
				target.addPattern(
						new AdapterPattern(target.getClassName(),
								"\\<\\<target\\>\\>"));
				c0.addPattern(new AdapterPattern(c0.getClassName(),
						"\\<\\<adapter\\>\\>"));
			}
			for (IRelation r : this.model.getRelations()) {
				if (r.getFromObject()
						.equals(c0.getClassName().replace("/", ""))
						&& r.getToObject().equals(
								fieldType.replace(".", ""))) {
					r.addProperty("xlabel=\"\\<\\<adapts\\>\\>\"");
				}
			}
		}
	}
}
```

###### CompositeVisitor

```java
package problem.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.interfaces.IClass;
import problem.interfaces.IField;
import problem.interfaces.IModel;
import problem.patterns.CompositePattern;

public class CompositeVisitor implements IInvoker {

	private IVisitor visitor;
	private Map<String, String> abstractToInterface; // extension, interface
	private List<IClass> possibleComposites;
	private List<String> possibleComponents;
	private boolean notPossibleComposite;
	private IClass currentClass;

	public CompositeVisitor() {
		this.visitor = new Visitor();
		this.abstractToInterface = new HashMap<>();
		this.possibleComposites = new ArrayList<>();
		this.possibleComponents = new ArrayList<>();
		this.preVisitModel();
		this.preVisitClass();
		this.visitField();
		this.postVisitModel();
	}

	private void preVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					for (IClass c : m.getClasses()) {
						if (c.getAccessLevel() == 1057) {
							if (c.getInterface().size() == 1) {
								String inter = "";
								for (String interfaze : c.getInterface()) {
									inter = interfaze;
								}
								this.abstractToInterface.put(c.getClassName(),
										inter);
							} else {
								this.abstractToInterface.put(c.getClassName(),
										"");
							}
						}
					}
				});
	}

	private void preVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, IClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					this.notPossibleComposite = false;
					if (c.getInterface().size() == 0
							&& c.getExtension().equals(null)) {
						this.notPossibleComposite = true;
					}
					this.currentClass = c;
				});
	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField f = (IField) t;
			if (this.notPossibleComposite) {
				return;
			}
			if (f.getSignature().equals("")) {
				return;
			}

			String[] sig = f.getSignature().split("<");
			String sigCheck = sig[sig.length - 1];
			sigCheck = sigCheck.replace(".", "/");
			sigCheck = sigCheck.substring(1);

			for (String s : this.currentClass.getInterface()) {
				if (s.equals(sigCheck)) {
					this.possibleComposites.add(this.currentClass);
					this.possibleComponents.add(sigCheck);
				}
			}

			if (this.currentClass.getExtension().equals(sigCheck)) {
				this.possibleComposites.add(this.currentClass);
				this.possibleComponents.add(sigCheck);
			}

			if (this.abstractToInterface
					.containsKey(this.currentClass.getExtension())
					&& this.abstractToInterface.containsValue(sigCheck)) {
				this.possibleComposites.add(this.currentClass);
				this.possibleComponents.add(sigCheck);
			}
		});

	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					List<String> comfComp = new ArrayList<>();
					List<String> comfComposite = new ArrayList<>();
					for (IClass c : m.getClasses()) {
						for (String s : this.possibleComponents) {
							if (c.getInterface().size() > 1
									|| c.getAccessLevel() == 1057) {
								break;
							}
							List<String> classInterface = (List<String>) c
									.getInterface();
							int iSize = classInterface.size();
							String cExt = c.getExtension();
							if ((iSize != 0 && classInterface.get(0).equals(s))
									|| cExt.equals(s)
									|| (this.abstractToInterface
											.get(cExt) != null
											&& this.abstractToInterface
													.get(cExt).equals(s))) {
								// detect maybe leaf check fields
								boolean hasComField = false;
								for (IField f : c.getIField()) {
									if (hasComField) {
										break;
									}
									String sigCheck = f.getSignature();
									if (!sigCheck.equals("")) {
										String[] sig = f.getSignature()
												.split("<");
										sigCheck = sig[sig.length - 1];
										sigCheck = sigCheck.replace(".", "/");
										sigCheck = sigCheck.substring(1);
									}

									if (sigCheck.equals(s)) {
										hasComField = true;
									}
								}
								if (!hasComField) {
									if (!comfComp.contains(s)) {
										comfComp.add(s);
									}
									c.addPattern(new CompositePattern(
											c.getClassName(),
											"\\<\\<Leaf\\>\\>"));
								}
							}
						}
					}

					for (String s : comfComp) {
						for (IClass c : m.getClasses()) {
							if (c.getClassName().equals(s)) {
								c.addPattern(
										new CompositePattern(c.getClassName(),
												"\\<\\<Component\\>\\>"));
							}
							if (this.abstractToInterface
									.get(c.getClassName()) != null
									&& this.abstractToInterface
											.get(c.getClassName()).equals(s)) {
								c.addPattern(
										new CompositePattern(c.getClassName(),
												"\\<\\<Component\\>\\>"));
							}
						}
					}

					for (IClass s2 : this.possibleComposites) {
						List<IField> classFields = (List<IField>) s2
								.getIField();
						for (IField f : classFields) {
							String sigCheck = f.getSignature();
							if (!sigCheck.equals("")) {
								String[] sig = f.getSignature().split("<");
								sigCheck = sig[sig.length - 1];
								sigCheck = sigCheck.replace(".", "/");
								sigCheck = sigCheck.substring(1);
							}
							if (comfComp.contains(sigCheck)) {
								s2.addPattern(
										new CompositePattern(s2.getClassName(),
												"\\<\\<Composite\\>\\>"));
								comfComposite.add(s2.getClassName());
							}
						}
					}

					for (IClass c2 : m.getClasses()) {
						for (String s3 : comfComposite) {
							if (c2.getExtension().equals(s3)) {
								c2.addPattern(
										new CompositePattern(c2.getClassName(),
												"\\<\\<Composite\\>\\>"));
							}
						}
					}
				});
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}
}
```

###### CompositePattern

```java
package problem.patterns;

import problem.interfaces.IPattern;

public class CompositePattern implements IPattern {

	private String className;
	private String UMLProperty;
	private String UMLlabel;

	/**
	 * Container class for holding information about creating representation of
	 * the Component Pattern
	 * 
	 * @param className
	 *            - name of class the pattern is being added to
	 * @param type
	 *            - The label for the class name: Component (interface for all
	 *            objects in the composition), Composite (Behavior of the
	 *            components having children and to store child component), Leaf
	 *            (Behavior for the elements in the composition)
	 */
	public CompositePattern(String className, String type) {
		this.className = className;
		this.UMLProperty = "fillcolor=yellow, style=filled, ";
		this.UMLlabel = type;
	}

	@Override
	public String getProperty() {
		return this.UMLProperty;
	}

	@Override
	public String getLabel() {
		return this.UMLlabel;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

}
```

------------------------------------------------------------

#### Contributions

Luke Danielson

1. Worked on Composite Visitor Pattern
2. Found the java.awt and java.swing arguments
3. Added additional tests to previous milestone

Larry Gates

1. Started the Composite Visitor Pattern
2. Created the Composite Pattern Object
3. Cleaned up code and README

Aaron Mercier

1. Worked on Composite Visitor Pattern
2. Tests for current milestone
3. Brute force for previous milestone

------------------------------------------------------------

## Professor Feedback and Fixes

After feedback from Chandan's review:
* [x] : Add additional testing to the integration testing, four or more additional testing
* [x] : Brute force version as we he had earlier

------------------------------------------------------------

## Computer Science Joke

![Computer Code joke](http://imgs.xkcd.com/comics/git.png)
![Computer Code joke](https://www.browserling.com/images/blog/web-designer-vim-radom-string-web-comic.png)

------------------------------------------------------------

## Milestone 5

#### Deliverables and Locations

1. Diagrams (Pictures below in same order):
  1. [Manually Created UML](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Manual_Generated_UML_Project.png)
  2. [Generated UML Project Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_Project.png)
  3. [Auto-Generated Week 2-1 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_2-1.png)
  4. [Auto-Generated Week 5-1 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_5-1.png)

2. Pictures:
  1. ![Manually Created UML](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Manual_Generated_UML_Project.png) 
  2. ![Generated UML Project Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_Project.png) 
  3. ![Auto-Generated Week 2-1 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_2-1.png) 
  4. ![Auto-Generated Week 5-1 Solution](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Generated_UML_5-1.png) 

------------------------------------------------------------

#### Design (Milestone Evolution)

![Design Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone5/Manual_Generated_UML_Project.png)

We continued to use the visitor pattern. Left code the same in the framework/library unless bug was found. Add to add additional methods to the IRelation objects, as a way to add label descriptions to be used. 

------------------------------------------------------------

#### Instructions

Instructions are still similar to previous milestone.

To test the program, there is a console available for use. 

The client would input the desired classes, such as classes in a package, in as the initial arguments. Once that object is finished loading, a console type structure is loaded. From there, follow the prompts for desired inputs. 

```
Supported operations: Generator, Help, Quit 
Input command:> Generator
Generators: Supported generators - UML, Sequence 
Input generator:> UML
Generated graph, please refresh the input_output folder
Supported operations: Generator, Help, Quit 
Input command:> quit
```

------------------------------------------------------------

#### Code

###### DecoratorPattern Object

```java
public class DecoratorPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	//need something with relations
	
	public DecoratorPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=green, style=filled,";
		this.UMLlabel = type; //type means decorator or component and will be provided from the visitor
	}
	
	@Override
	public String getProperty() {
		return this.UMLproperty;
	}

	@Override
	public String getLabel() {
		return this.UMLlabel;
	}
	
	public String getClassName(){
		return this.className;
	}

}
```

###### DecoratorVisitor Object

```java
public class DecoratorVisitor implements IInvoker {
	private IVisitor visitor;
	private List<String> decoratorList;
	private List<String> concreteDecorators;
	private Collection<String> tempInterfaces;
	private List<String> componentList;
	private String tempExtension;
	private IClass tempClass;
	private boolean notAbstract;
	private boolean isDecorator;

	public DecoratorVisitor() {
		this.visitor = new Visitor();
		this.decoratorList = new ArrayList<>();
		this.concreteDecorators = new ArrayList<>();
		this.componentList = new ArrayList<>();

		this.setupPreVisitClass();
		this.visitField();
		this.visitExtensionRelation();
		this.postVisitModel();
	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void setupPreVisitClass() { // search for a class that could
										// potentially be the decorator and add
										// it to a list
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					this.tempClass = (IClass) t;
					this.tempInterfaces = new ArrayList<>();
					this.tempExtension = "";

					this.notAbstract = false;
					this.isDecorator = false;
					// if (this.tempClass.getAcessLevel() != 1057){ //if the
					// class is not abstract it cannot be a decorator
					// this.notAbstract = true;
					// return;
					// }

					this.tempInterfaces = this.tempClass.getInterface();
					this.tempExtension = this.tempClass.getExtension();
				});
	}

	private void visitField() {
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			if (this.notAbstract) // if already determined to be a decorator or
									// not return to avoid wasted computation
				return;

			IField f = (IField) t;
			String desc = f.getDesc().replace(".", "/");
			if (desc.equals("java/lang/Object")) {
				return;
			}

			if (desc.equals(this.tempExtension)
					|| this.tempInterfaces.contains(desc)) {
				this.componentList.add(desc);
				if (!this.isDecorator) {
					this.decoratorList.add(this.tempClass.getClassName());
					this.tempClass.addPattern(
							new DecoratorPattern(this.tempClass.getClassName(),
									"\\<\\<decorator\\>\\>"));
					this.isDecorator = true;
				}
			}
		});
	}

	private void visitExtensionRelation() {
		this.visitor.addVisit(VisitType.Visit, ExtensionRelation.class,
				(ITraverser t) -> {
					IRelation ext = (IRelation) t;
					for (int i = 0; i < this.decoratorList.size(); i++) {
						String decorator = this.decoratorList.get(i);
						decorator = decorator.replace("/", "");
						if (ext.getToObject().equals(decorator)) { 
						// once the concrete decorator is found add its
						// name to a list of concrete decorators
							this.concreteDecorators.add(ext.getFromObject());
							break;
						}
					}
				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;

					List<IClass> classList = m.getClasses();
					for (String tempComponent : this.componentList) { 
					// find all component classes and add a decorator pattern object to it
						for (IClass tempClass : classList) {
							if (tempClass.getClassName()
									.equals(tempComponent)) {
								tempClass.addPattern(new DecoratorPattern(
										tempClass.getClassName(),
										"\\<\\<component\\>\\>"));
							}
						}
					}

					for (String tempConcrete : this.concreteDecorators) { 
					// find all concrete decorators and add a decorator pattern object
						for (IClass tempClass : classList) {
							if (tempClass.getClassName().replace("/", "")
									.equals(tempConcrete)) {
								tempClass.addPattern(new DecoratorPattern(
										tempClass.getClassName(),
										"\\<\\<decorator\\>\\>"));
							}
						}
					}

					List<IRelation> relations = m.getRelations();
					for (String s : this.decoratorList) {
						for (IRelation r : relations) {
							if (r.getClass().equals(HasRelation.class)) {
								if (s.replace("/", "")
										.equals(r.getFromObject())) {
									r.addProperty("xlabel=\"<<decorates>>\"");
								}
							}
						}
					}
				});
	}
}
```

###### DesignParser

```java
		DecoratorVisitor decoratorVisitor = new DecoratorVisitor();
		decoratorVisitor.write(model);
```

###### AdapterPattern Object

```java
public class AdapterPattern implements IPattern{
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	
	public AdapterPattern(String className, String type) {
		this.className = className;
		this.UMLproperty = "fillcolor=red, style=filled,";
		this.UMLlabel = type; //type means target, adapter, or adaptee and will be provided from the visitor
	}
	
	@Override
	public String getProperty() {
		return UMLproperty;
	}

	@Override
	public String getLabel() {
		return UMLlabel;
	}
	
	public String getClassName(){
		return this.className;
	}
}
```
###### AdapterVisitor Object

```java
public class AdapterVisitor implements IInvoker {

	private IVisitor visitor;
	private List<IClass> classList;

	public AdapterVisitor() {
		this.visitor = new Visitor();
		this.classList = new ArrayList<>();
		this.setupPreVisitClass();
		this.visitHasRelation();
		this.postVisitModel();

	}

	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);
	}

	private void visitHasRelation() {
		this.visitor.addVisit(VisitType.Visit, ConcreteClass.class,
				(ITraverser t) -> {

				});
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class,
				(ITraverser t) -> {
					IClass c = (IClass) t;
					if (c.getInterface().size() == 1) {
						// Detecting adapter
						if (c.getExtension().equals("java/lang/Object")) {
							// Doesn't have an extension
							if (c.getIField().size() == 1) {
								// Only 1 field
								this.classList.add(c);
								// Adds it when all three parts exist
							}
						}
					}
				});
	}

	private void postVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class,
				(ITraverser t) -> {
					IModel m = (IModel) t;
					for (IClass c0 : this.classList) {
						boolean isAdaptee = false;
						IClass adaptee = null;
						boolean isTarget = false;
						IClass target = null;
						String fieldType = ((List<IField>) c0.getIField())
								.get(0).getDesc();
						String interfaceName = ((List<String>) c0
								.getInterface()).get(0);
						for (IClass c1 : m.getClasses()) {
							if (c1.getClassName().equals(fieldType)
									|| c1.getClassName().replace("/", ".")
											.equals(fieldType)) {
								isAdaptee = true;
								adaptee = c1;
								continue; // If this one, not the interface
							}
							if (c1.getClassName().equals(interfaceName)) {
								isTarget = true;
								target = c1;
							}
							if (target != null && adaptee != null) {
								break;
							}
						}
						if (isAdaptee && isTarget) {
							adaptee.addPattern(
									new AdapterPattern(adaptee.getClassName(),
											"\\<\\<adaptee\\>\\>"));
							target.addPattern(
									new AdapterPattern(target.getClassName(),
											"\\<\\<target\\>\\>"));
							c0.addPattern(new AdapterPattern(c0.getClassName(),
									"\\<\\<adapter\\>\\>"));
						}
						for (IRelation r : m.getRelations()) {
							if (r.getFromObject()
									.equals(c0.getClassName().replace("/", ""))
									&& r.getToObject().equals(
											fieldType.replace(".", ""))) {
								r.addProperty("label=\"\\<\\<adapts\\>\\>\"");
							}
						}
					}

				});
	}
}
```

###### DesignParser

```java
		AdapterVisitor adapterVisitor = new AdapterVisitor();
		adapterVisitor.write(model);
```

------------------------------------------------------------

#### Contributions

Luke Danielson

1. Worked on Decorator Visitor
2. Helped on Adapter Visitor

Larry Gates

1. Worked on Adapter Visitor
2. Cleaned up code

Aaron Mercier

1. Started Integration testing
2. Helped with Decorator Visitor
3. Helped with Adapter Visitor

------------------------------------------------------------

## Professor Feedback and Fixes

After feedback from Chandan's review:
* [x] : Make UML Diagram easier to read
* [x] : Fix integration testing

------------------------------------------------------------

## Computer Science Joke

![Computer coding joke](http://24.media.tumblr.com/tumblr_mec79tyDj71rix0qmo1_400.jpg)

-----------------------------------------------------------

## Milestone 4

#### Deliverables and Location

1. Diagrams:
  1. [Manually Created UML](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone4/manual_generated_UML.png)
  2. [Generated UML Project Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone4/generated_UML.png)
  3. [Generated Singleton Patterns](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone4/generated_UML_Singleton.png)

------------------------------------------------------------

#### Design (Milestone Evolution)

![Design Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone4/manual_generated_UML.png)

As we progressed through the project, and also following Chandan's suggestion, the design of the project is overall in Visitor Pattern. Keeping most of the structure the same from the previous milestones, the visitor pattern is used for traversing through the model object and collecting information needed, then writing the output in a desired class that follows the output needed. 


The Singleton tool is designed to be a Visitor Pattern. This way, all design patterns follow an interface and will allow future design patterns to use this. Inside of the IClass object, there is a list of IPattern objects that are effected by a pattern. The pattern then can bre used to print out specific values. Singleton code is in code section of this README. The IInvoker inferface will allow different files to write with information from the IVisitor object. 

------------------------------------------------------------

#### Instructions

Instructions are still similar to previous milestone.

To test the program, there is a console available for use. 

The client would input the desired classes, such as classes in a package, in as the initial arguments. Once that object is finished loading, a console type structure is loaded. From there, follow the prompts for desired inputs. 

```
Supported operations: Generator, Help, Quit 
Input command:> Generator
Generators: Supported generators - UML, Sequence 
Input generator:> UML
Generated graph, please refresh the input_output folder
Supported operations: Generator, Help, Quit 
Input command:> quit
```

------------------------------------------------------------

#### Code (Singleton detection)

###### Singleton Object
```java
public class SingletonPattern implements IPattern {
	private String className;
	private String UMLproperty;
	private String UMLlabel;
	
	public SingletonPattern(String className) {
		this.className = className;
		this.UMLproperty = "color=blue,";
		this.UMLlabel = "\\<\\<Singleton\\>\\>";
	}
	
	@Override
	public String UMLproperty() {
		return this.UMLproperty;
	}

	public String getClassName(){
		return this.className;
	}

	@Override
	public String UMLlabel() {
		return this.UMLlabel;
	}
}
```

###### SingletonVisitor
```java
public class SingletonVisitor implements IInvoker {
	private Visitor visitor;
	private IClass currentClass;
	private boolean hasFieldInstance;
	private boolean hasMethodInstance;
	
	public SingletonVisitor(){
		this.visitor = new Visitor();
		
		setupPreVisitClass();
		visitField();
		visitMethod();
		postVisitClass();
	}
	
	private void setupPreVisitClass(){
		this.visitor.addVisit(VisitType.PreVisit, ConcreteClass.class, (ITraverser t) ->{
			this.currentClass = (IClass) t;
			this.hasFieldInstance = false;
			this.hasMethodInstance = false;
		});
	}
	
	private void visitField(){
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			IField f = (IField) t;
			String desc = f.getDesc().replace(".", "/");
			if (desc.equals(currentClass.getClassName())){
				hasFieldInstance = true;
			}
		});
	}

	private void visitMethod(){
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			IMethod m = (IMethod) t;
			Type arg = Type.getReturnType(m.getDesc());
			String arg2 = arg.toString().substring(1).replace(";", "");
			if (arg2.equals(currentClass.getClassName())){
				hasMethodInstance = true;
			}
		});
	}
	
	private void postVisitClass(){
		this.visitor.addVisit(VisitType.PostVisit, ConcreteClass.class, (ITraverser t) -> {
			IClass c = (IClass) t;
			if (this.hasFieldInstance && this.hasMethodInstance){
				c.addPattern(new SingletonPattern(c.getClassName()));
			}
		});
	}
	
	@Override
	public void write(IModel model) {
		ITraverser traverser = (ITraverser) model;
		traverser.accept(this.visitor);		
	}
}
```

###### IInvoker
```java
public interface IInvoker {
	public void write(IModel model);
}
```

------------------------------------------------------------

#### Testing

Added tests into the document

------------------------------------------------------------

#### Contributions

Luke Danielson

1. Got Singleton pattern working

Larry Gates

1. Unit test cases

Aaron Mercier

1. Converted Sequence Generator to Visitor Pattern

------------------------------------------------------------

## Professor Feedback and Fixes:

After feedback from Chandan's review:

* [x] Refactor the code so that the IArrow objects are outside of the IClass objects. Move them into hte IModel object. 
* [x] Handle relation objects in IModel
* [x] Change the IGenerate class to become a visitor. Work with visitor models
* [x] Method Container needs some refactoring
* [ ] Improve testing by creating an IModel object and testing the output with that
* [ ] Use a ByteArrayStream for testing instead of reading from files
* [x] Fix the Sequence Diagrams for both the project and the shuffle method

------------------------------------------------------------
## Design Pattern Joke

A programmer and a business analyst are sitting in the break room one day eating lunch when suddenly the microwave catches fire. Thinking quickly, the analyst leaps up, unplugs the microwave, grabs the trash can, fills it with water from sink, and dumps the water on the microwave to put out the flames.

A few weeks later the two are again having lunch in the break room when suddenly the coffee maker bursts into flames. The programmer leaps up, grabs the coffee maker, shoves it into the microwave oven, and then hands the trash can to the business analyst, thus re-using the solution developed for the previous project.

------------------------------------------------------------

## Milestone 3

#### Deliverables and location

1. Test code:
  1. [Testing Folder](https://github.com/Mercieral/374BlueShells/tree/master/BlueShells374/test)
2. Project's New Tool 
  1. [Generated Sequence Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/Generated_main_sd.png)
  2. [Manually Created Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/manual_generated_main_sd.png)
3. Collections 
  1. [Generated Sequence Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/Generated_collections_sd.png)
  2. [Manually Created Diagram](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/manual_generated_collection_sd.png)

#### Design (Milestone Evolution)

![Design Drawing](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/manual_generated_UML.png)

The design patterns stayed mostly the same. Visitor Pattern used for ASM and Strategy Pattern used for the rest. We created an [IGenerator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/interfaces/IGenerator.java) object, which took the UML generator code and became an IGenerator object. Doing this allowed to implement future output files without making changes to our exisiting code.

#### Instructions

Due to the improvement in running the program, the instructions are different from previous milestones.

To test the program, there is a console available for use. 

The client would input the desired classes, such as classes in a package, in as the initial arguments. Once that object is finished loading, a console type structure is loaded. From there, follow the prompts for desired inputs. 

For this milestone, the instructions would involve the shuffle method in Collections. Load  ``` java.util.Collections ``` into arguments, as in Eclipse and the run configuration. 

```
Supported operations: Generator, Help, Quit 
Input command:> Generator 
Generators: Supported generators - UML, Sequence 
Input generator:> Sequence
Input Class Name:>java.util.Collections
Input Method Name:>shuffle
Input Parameters (split by commas):>java.util.List
Input CallDepth (optional enter skip for default, default is 5)
class: java.util.Collections
method: shuffle
params: [java.util.List]
depth: 5
generating sequence diagram file
ERROR: The provided arguments do match any existing arguments
generating for method shuffle
generating for method swap
generating for method swap
Generated graph, please refresh the input_output folder
Supported operations: Generator, Help, Quit 
Input command:> Quit
```

#### Contributions

Aaron Mercier 

1. Created [Sequence Diagram Generator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/SequenceGenerator.java) object
2. Started unit testing
3. Completed [Sequence Diagram Generator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/SequenceGenerator.java)

Luke Danielson (Commit log is low due to fact he was capped. Worked with us, we forgot to include him in commit messages)

1. Helped create [Sequence Diagram Generator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/SequenceGenerator.java) object 
2. Fixed Has Arrow dominance and duplicate arrow
3. Made a console layout to allow easy running by any type of user
4. Completed integrated testing

Larry Gates

1. Created the [container class](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/MethodContainer.java) to be used within methods.
2. Finished unit testing
3. Updated README

------------------------------------------------------------

## Professor Feedback and Fixes:

After feedback from Chandan's review:

* [x] Create test classes for each class and appropriate method in the class
* [x] Move the generate from IModel object and into its own class
* [x] Ignore uses arrows when there is an association arrow (has)
* [x] Add pictures to the Design sections of milestones
* [x] Talk more about the type of Design Pattern(s) and other design implementations
* [x] Continue to do integration testing

------------------------------------------------------------

## Milestone 2

#### Deliverables and location

1. Test code:
  1. [Test File](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/test/problem/blueshells/testing/Milestone2Testing.java "JUnit Test Code") 
2. Abstract Factory PizzaStore
  1. [Generated UML picture](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone2/Generated_Pizzaaf.png "Picture from master branch") 
3. Project Code
  1. [Designed UML Picture](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone2/Manually_created_Project_UML.png "Picture from master branch") 
  2. [Generated UML Picture](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone2/Generated_Project_UML.png "Picture from master branch") 

#### Design (Milestone Evolution)

![Design Drawing](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone2/Manually_created_Project_UML.png)

While working on Milestone 2, it was important to use the information suggested by Dr. Rupakheti in lecture. Such as: visitMethodInsn and visitFieldInsn. The arrow objects were changed from just if statements and string to being a Strategy Pattern, which turned out to to be useful in the long run. 

From reading the specifications and searching the given code, the team could not figure out how to find the inner types for arguments and return type of methods, such as for lists or collections.

The design pattern stayed the same, using Visitor Pattern for ASM and Strategy Pattern for the rest of it. 

#### Instructions

To run the program, run designParser (where the main method is located) with the 
classes you wish to have the UML generated for as arguments. If running the program 
from the command line, separate each argument with a space. If running the program 
through eclipse, right click on designParser, click on run configurations, go to the 
arguments tab, and fill in the classes in the "program arguments", separated by a 
space or a newline.

#### Contributions

Luke Danielson

1. Worked on testcases for Milestone 2
2. Helped rewrite generateArrows(IClass obj) for **Model.java** with Larry

Larry Gates

1. Constructed the Strategy Pattern for the 4 arrows for the classes.
2. Helped rewrite generateArrows(IClass obj) for **Model.java** with Luke

Aaron Mercier

1. Worked on visitors to create arrows
2. Original design of handling arrows and values need for GraphViz to show the arrows.

------------------------------------------------------------

## Milestone 1

#### Design

![Design Picture](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone1/Manually_Created_Project_UML.png)

The program is designed to create a coded model of the classes given as arguments.
The model contains a list of classes, each class has it's info and a list of 
fields/methods, and each field/method has it's info. When ASM has finished visiting
all of the given classes with the decorated visitors, the model's generateGraph 
method is called, which writes all of the stored information from the model into
a graph.gv file. When the graph.gv file is finished, GraphViz generates a .PNG 
file using it.

The design patterns we used were: Visitor Pattern (ASM), and Strategy Pattern (remain parts)

#### Instructions

To run the program, run designParser (where the main method is located) with the 
classes you wish to have the UML generated for as arguments. If running the program 
from the command line, separate each argument with a space. If running the program 
through eclipse, right click on designParser, click on run configurations, go to the 
arguments tab, and fill in the classes in the "program arguments", separated by a 
space or a newline.

The graph.gv and graph.png files are saved in the [input_output](https://github.com/Mercieral/374BlueShells/tree/master/BlueShells374/input_output "input_output for master branch")  folder.

There are example argument text files that can be copied and pasted into the argument
box. AaronArguments.txt is the list of arguments for the generated UML for AaronsLab1-3 and MilestoneArguments.txt
is the list of arguments for the generated UML for the project in project.asm package.

#### Contributions

Aaron Mercier 

1. Set up the git repository
2. implemented the method, field, and declaration visitors to create and fill in our model
3. implemented/added the arrows to the .gv file

Luke Danielson

1. Implemented the IClass and interface and the three subclasses ConcreteClass, AbstractClass, and InterfaceClass along with testing for each one. 
2. Implented/added fields to the .gv file. 

Larry Gates

1. Implemented the Field class and Method class.
2. Implemented corresponding tests for the created classes.
3. Implemented/added methods to the .gv file
4. Made the .gv file more human readable. 

------------------------------------------------------------

## Milestone 0 

Team Blue Shells:
* Luke Danielson
* Larry Gates
* Aaron Mercier
