# 374BlueShells
Repository for our CSSE 374 Software Design project. 

Milestone 1
=======

Design
------

The program is designed to create a coded model of the classes given as arguments.
The model contains a list of classes, each class has it's info and a list of 
fields/methods, and each field/method has it's info. When ASM has finished visiting
all of the given classes with the decorated visitors, the model's generateGraph 
method is called, which writes all of the stored information from the model into
a graph.gv file. When the graph.gv file is finished, GraphViz generates a .PNG 
file using it.

Instructions
------

To run the program, run designParser (where the main method is located) with the 
classes you wish to have the UML generated for as arguments. If running the program 
from the command line, separate each argument with a space. If running the program 
through eclipse, right click on designParser, click on run configurations, go to the 
arguments tab, and fill in the classes in the "program arguments", separated by a 
space or a newline.

The graph.gv and graph.png files are saved in the input_output folder.

There are example argument text files that can be copied and pasted into the argument
box. AaronArguments.txt is the list of arguments for the generated UML for AaronsLab1-3 and MilestoneArguments.txt
is the list of arguments for the generated UML for the project in project.asm package.

Contributions
------

Aaron Mercier
	0. Set up the git repository
	0. implemented the method, field, and declaration visitors to create and 
		fill in our model
	0. implemented/added the arrows to the .gv file

Luke Danielson
	0. Implemented the IClass and interface and the three subclasses 
		ConcreteClass, AbstractClass, and InterfaceClass along with testing for 
		each one. 
	0. Implented/added fields to the .gv file. 

Larry Gates
	0. Implemented the Field class and Method class.
	0. Implemented corresponding tests for the created classes.
	0. Implemented/added methods to the .gv file
	0. Made the .gv file more human readable. 


Milestone 0
======

Team Blue Shells:
	Luke Danielson
	Larry Gates
	Aaron Mercier