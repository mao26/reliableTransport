package Controller;

import Model.CommandNode;
import Model.CommandTree;

public class TextParser {
	private String text;
	private String[] commandParts;
	CommandTree currtree;

	public TextParser(String text) {
		this.text = text;
		parseCommands(text);
		setUpTree();
	}

	private void parseCommands(String text) {
		commandParts = text.split(" ");
		setUpTree();
		currtree.createTree(commandParts);
	}
	
	public String[] grabCurrCommands(){
		return commandParts;
	}

	public void setUpTree() {
		// either set up the tree structure
			if (currtree == null) {
				currtree = new CommandTree();
			}
		// or set the tree up so that it can create a new tree structure for a
		// new set of commands
		else {
			currtree = null;
		}
	}
}