# 374BlueShells
Repository for our CSSE 374 Software Design project. 


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
  1. Generated Sequence Diagram TODO
  2. Manually Created Diagram TODO
3. Collections 
  1. Generated Sequence Diagram TODO
  2. Manually Created Diagram TODO

#### Design (Milestone Evolution)

![Design Drawing](https://raw.githubusercontent.com/Mercieral/374BlueShells/master/BlueShells374/docs/Milestone3/manual_generated_UML.png)

The design patterns stayed mostly the same. Visitor Pattern used for ASM and Strategy Pattern used for the rest. We created a [IGenerator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/interfaces/IGenerator.java) object, which took the UML generator code and became a IGenerator object. Doing this allowed to implement future output files without making changes to our exisiting code.

#### Instructions

Due to the improvement in running the program, the instructions are different from previous milestones.

To test the program, there is a console available for use. 

TODO

#### Contributions

Aaron Mercier 

1. Create [Sequence Diagram Generator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/SequenceGenerator.java) object
2. Started unit testing

Luke Danielson

1. Helped create [Sequence Diagram Generator](https://github.com/Mercieral/374BlueShells/blob/master/BlueShells374/src/problem/javaClasses/SequenceGenerator.java) object 
2. Fixed Has Arrow dominance and duplicate arrow
3. Made a console

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
