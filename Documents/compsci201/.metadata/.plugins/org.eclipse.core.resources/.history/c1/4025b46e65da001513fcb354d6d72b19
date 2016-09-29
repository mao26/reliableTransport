package Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class CommandNode {

	private CommandsDataStruct commandsClass;
	private List<?> possibleCommands;
	
	public CommandNode(){
		commandsClass = new CommandsDataStruct();
		possibleCommands = commandsClass.getInstance().grabPossibleCommands();
	}
	
	public abstract void evaluate(String myCommand);
	
	public abstract void intermediateCanvasUpdate();
	
	public void determineTypeNArgs(){
	}
	
	private List<?> getCommands(){
		return possibleCommands;
	}
	
	

}
