SLogo: Design Document (Team 18)
===================


----------
Introduction
-----------------------
Our team is creating an integrated development environment (IDE) that supports SLogo programs. SLogo is a simplified version of Logo, a computer programming language designed to teach programming to children. SLogo programs consist of command line prompts and user-defined functions. In this program, commands will be interpreted and executed through a graphical user interface (GUI) containing a command history and a display ground. The display ground will create a design and "draw" the user commands by moving a turtle, and the command history will display commands already run and results from executions. The primary goals of this project are to interpret/parse user commands, and execute these commands to display the results through a user interface. In terms of architecture, our program can be easily extended through adding new commands and creating user-defined functions that make new designs to be drawn by the turtle. Parsing previously defined commands, command window prints, and other menus within the program will be closed. Menus and windows can be extended but will be closed to changing previously implemented functionalities.


Design Overview
-----------------------
Front-End:
![UML Diagram](https://github.com/duke-compsci308-spring2016/slogo_team18/blob/master/UML_frontend.png?raw=true)

View is the master-view that keeps track of a list of projects. Projects are like workspaces—different projects that the user can work on. It is named after Eclipse IDE’s projects. A project contains a list of Turtles, a Canvas object, and History, and customCommands and possibly more. myTurtles variable is actually a List<Turtle>--UML Lab program was buggy so it’s shown as of Turtle. The back-end and the front-end communicate through the list of Turtles.

Back-End:
![UML Diagram](https://github.com/duke-compsci308-spring2016/slogo_team18/blob/master/UML_backend.png?raw=true)

The back-end of our program is controlled by the MainDriver class, which is used to link the front-end and back-end components of the project. The MainDriver initiate the program's front-end creation, which would have an event listener for the back-end to receive an unparsed command from the front-end console. The MainDriver will then send in a String (the command) to the TextParser class which builds a tree upon the root when the parser is called. The CommandTree is created when the TextParser passes its root. This way, the root can be passed to other classes that need it as well. The TextParser will translate foreign commands to English using the resources file provided by Professor Duvall for this project and be controlled in a translate method. Each node in the tree is a CommandNode, which is an interface we used for our command inheritance structure. We plan on implementing CommandNode with different types of commands (TurtleQuery, TurtleCommand, MathCommand, etc.) that serve as intermediary levels between Node and the subclasses, which are individual commands. The intermediary levels will be abstract classes with instance variables and methods of their own that are accessable by its subnodes. The TextParser will use Reflection to call upon subnode command classes, accessing specific commands based on the parsed text (ex. command typed in "Forward" links to ForwardCommand class). The back-end also has access to a Character inheritance hierarchy, for which Turtle is a member. Turtle's backend representation uses the State class which holds various Turtle states, such as its direction, coordinates, etc. After the tree is built, the MainDriver will call the Tree's traverse method that will execute commands as it goes through the tree. At each execution, the front-end representation of the Turtle will receive the updated State from the back-end via MainDriver, which uses the step method in Character. 


User Interface
-------------------
When the user runs the program, the first screen they see will have a dropdown menu for them to choose their language, and a button for them to upload their turtle image. 

![startscreen](https://lh3.googleusercontent.com/-r3kpd8pERO0/VspMSY9Mb2I/AAAAAAAAB0E/cz8HANkJa3c/s0/12443923_10209178229038072_1817189894_o.jpg "startscreen")

Once they've selected a language and uploaded their turtle image, they are taken to GUI. They can select the project that they're working on from the dropdown menu (all others are hidden), see their command history, click the help button to direct them to an HTML page with a resource guide, and enter in their command. Once they hit run the program will interpret their command and draw on the screen. To the left of the screen the user will be able to track his/her variables, and customize their environment (background color, pen color).

![UI](https://lh3.googleusercontent.com/-YX-AUE2tDiY/Vsok9Fac7PI/AAAAAAAAByU/QZDjQ_GdrqM/s0/IMG_0356.JPG "UI")

In the case that the user enters a command that cannot be interpreted, a popup will show up notifying the user of their faulty input. The command will be saved in the command history and displayed in red text, and the user will be able to edit their command. The UI protects against empty data as the "Run" button will not be clickable until there is text in the command box.

API Details
--------------
Another API that the front-end is going to build is the History API. This API would be classified as an external API because it interacts with both the front-end and the back-end. This API supports the specification that the SLogo IDE should enable the user to see commands previously run in the environment (even better, make them directly clickable to execute). The resources it is going to use, which will be a parameter to this API, is a list of nodes (command objects) from the back-end. It returns a pane that has JavaFX Text elements inside it so that each line of test could be clicked to generate an ActionEvent that executes the same command again by sending the same command to the back-end. Because the main feature of this API is to visualize a list of things and to take in mouse click ActionEvents to notify the back-end, other additions that want to use this feature can easily extend this API. This API belongs to the front-end, and it satisfies one of our key goals of separating the back-end and the front-end. This abstracts out the idea of taking what the back-end has in data and turning it into visual JavaFX elements. Also, it aims to emulate the black-box model of hiding how things are implemented in the inside. Because it fulfills a simple purpose, it is hard to misuse, easy to use, and sufficiently powerful. So this API follows the principles of abstraction, encapsulation, and possibly inheritance (this functionality could be used by others).

The frontend will also be building the Character API, which is an internal API as it interacts with the front-end. This API supports the specification that the SLogo IDE should see the results of the turtle executing commands displayed visually. The resources it will use is a list of character interfaces. It doesn't return anything, but there will a trail from the movement of the turtle. The goal of this API is to have an object that takes in parsed information and physically moves/updates the location of the turtle using visual JavaFX elements. It further follows principles of encapsulation by hiding how things are implemented on the inside. The Character interface moves the turtle, rotates, sets pen position, image, and pen color. The Turtle class has five instance variables: boolean isPenDown, Image myImage Color myPenColor, double myX, double myY, double orientation. myX and myY represent turtle coordinates, and orientation is a number from [0, 2pi) corresponding to the turtle's orientation, described by an angle counterclockwise from the positive x-axis. When isPenDown is true, the turtle leaves a trail, when pen is false, it doesn't. Turtle class needs get and set methods for each instance variable. The Turtle class will have constructors and move, rotate, and togglePen functions. The turtle object will start in the center of the screen but its image will be user uploaded at the launch of the program. 

API Example Code
-------------------
The following is the HistoryViewer interface:
```
public interface HistoryViewer<E> {
	   public Region getPane();
	   public <E> void getHistory(List<E> history);
	   public String getClickedCommand(ActionEvent e);
}
```
And the following is a class that implements this interface:
```
public class CommandHistoryViewer<E> implements HistoryViewer {
	List<E> myHistory;

	public Region getPane() {

		ScrollPane scrollDisplay = new ScrollPane();
		Pane innerDisplay = new Pane();
		scrollDisplay.setContent(innerDisplay);

		innerDisplay.setStyle("-fx-background-color: white;");

		for (E node : myHistory) {
			innerDisplay.getChildren().add(createText(node));
		}
		Scene scene = new Scene(innerDisplay, 200,200);
		return scrollDisplay;
	}

	private Text createText(E commandNode) {
		Text newCommand = new Text(commandNode.toString());
		// Code for ActionEvent when clicked
		return newCommand;
	}

	@Override
	public void getHistory(List history) {
		// TODO Auto-generated method stub
		myHistory = history;
		
	}

	@Override
	public String getClickedCommand(ActionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
}
```
And this snippet of code demonstrates how each command, when parsed and processed, gets added to command history.

The following is the Character interfact:

```
public interface Character {
	public abstract void move();
	public abstract void rotate();
	public abstract void penDown();
	public abstract void penUp();
	public abstract void setTurtleImage();
	public abstract void setPenColor();
}
```


Design Considerations 
--------------
A big external design decision that we made was to use a CharacterContainer class that contains a list of Character interfaces. Turtle implements the Character interface, so if there happened to be different kinds of objects, they could be included in the list (polymorphism). The CharacterContainer is unique to each Project and will provide a getter method so that both front-end and back-end could access the list. This defines the interaction between front and back-ends. Alternate designs include passing a list of states back and forth.

Another design consideration we had was with error checking. We initially had the user pressing the enter key to send the command to the backend, but then switched over to pressing a "run" button that isn't clickable until the user has typed something into the console to avoid sending the backend several empty strings.

A design consideration on the back-end side was related to the text parsing and tree creation. Initially, we were going to parse the commands by looking at the type of command and grabbing the information following and passing this in via parameters to the creation of an object via Reflection. However, we realized that the program should be more flexible to support more complicated instructions, such as combined instructions. An example of this would be "forward add 50 50" where the previous design consideration would not be powerful enough and would not be able to interpet the information following forward, which this time is an actual command. Therefore, we decided to design a tree structure in which our text parser would create a tree that would allow the commands to be executed in pieces. Our tree structure allows the execution of the current node to be based on the execution of its subnode(s). For example, forward looks for one subnode and excutes that subnode. If the subnode is a simple number, the execution returns the actual value (ex. forward 100 would be interpreted to move the turtle forward). Otherwise, the subnode would be executed itself and this excution can look for other subnodes. For example in "forward add 50 50", the turtle is moved forward the evaluation of "add 50 50" so it moves forward 100. This holds true for "forward forward subtract 200 add 50 50" which would move the turtle forward 100 twice (subtract 200 add 50 50 = 100) and the first forward would move the turtle forward the execution of the next forward.

Because there is such an extensive list of commands that must be implemented in the backend, it is important to consider how to organize the commands such that they are reusable. Initially, we considered having command-type classes such as MathCommand and TutleQuery from which specific commands could be instantiated using a flexible constructor. These command constructors would take in a String identifier that would correspond to the desired command, like add or forward. Each command-type class would implement a larger command interface. We determined, however, that it would be more extendable for each individual command to have a separate class that extends or implements the command-type classes. Because each command, despite its complexity, will be broken down into fundamental command-types, we do not anticipate one command requiring instance variables or methods of multiple command-types. As a result, we made the decision to represent the command-types as abstract classes rather than interfaces. For this same reason, we determined that it would not be necessary to have user-defined command type, since these compound commands would be broken down during parsing. 

In order to animate the movement of the turtle such that a path could be traced showing each step the turtle makes, it is necessary to split compound commands into increments. This division would avoid having to base the path solely off of the overall starting and ending positions of the object, which could be the same even for complex commands. Initially, we considered having a step() method in the back-end turtle class that would be called many times per second from the front-end using a KeyFrame class. This strategy was an intuitive first solution as many of the group members had implemented a similar algorithm for the first project. The advantage of time-based increments over command-based increments is that the object on the screen would move more fluidly across the screen. We determined that the added implementation complexity of KeyFrame animation, especially in terms of communication between the front-end and back-end, was not worth fluid motion, since we do not anticipate this being a requirement. Incrementing based on individual commands also became a clearer alternative after deciding that all commands would be broken down into their individual components. In this way, tracing out a circle could be accomplished through tracing each of the incremental forward-turn commands that make up the overall motion. This strategy requires minimal additional implementation details.

Another back-end design consideration we made was with regards to when we wanted to update the actual movement of the turtle and its states/tracing. When we learned that there was a "clear" functionality that back-end was responsible for, we had to reconsider how we were going to previously pass the updated turtle state. Previously, we were going to pass the new turtle state at the end of the command executions, but we decided to update the turtle after every execution. This way, traces can be created more efficiently to be cleared and accessed individually for the front-end.

Team Responsibilities
-----------------
For Front-End, Hunter will be building View and Projects. View is the master-view class where projects, and additional visualization features are housed. The Project class contains turtles, canvas, history, and possibly new commands. Michelle will be in charge of creating subclass elements such as Turtle, Canvas, History, HTML Help, and Languages.

Aaron is a member of the back-end subteam. His responsibilities will primarily relate to using the parsed command trees, developed by Adam, to update the state of the turtle (internal API). Additionally, he will be responsible for communicating the updated state of the objects on the screen to the front-end (external API) in a ready-to-use format, such as a list of character objects. The classes that correspond to these responsibilities include character, turtle and state.

Adam is also a member of the back-end subteam and is working toward the creation of the Command tree, parsing the text commands, and interpreting and executing the commands. He is responsible for working with classes such as MainDriver, CommandTree, and individual command classes.
