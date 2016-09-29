# cellsociety
Duke CompSci 308 Cell Society Project

###Introduction



### Overview

Possible Classes:
* User Object
* Grid Object
* GUI Object
* Cell Object
* ScannerXML Object 

	The XML object will have 
	*an author
	*Case type
	*Settings specific to the case which includes dimensions of the grid space and threshold
	

* An extendable amount of rule object 
	
	*This will be an abstract rule object
	*Other rules will extend this class with the Case specific rules
	*This 

What needs to be extendable is:

1. the ability to add new rules
2. and new  parameters

Our features that will not change are: 
	
1. The ability to read our XML file
2. The ability to update our cell's state
3. The ability to create our cell grid and continually update that grid

### User Interface
We want to first only show a splash screen with a Load button from which the user will pick from a s

Interface has to support pausing, stopping, playing the simulation, allowing the user to increase or decrease the animation rate of the cells, load new XML, a step button to increment by only one iteration.

### Design Details