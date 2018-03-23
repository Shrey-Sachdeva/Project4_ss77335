package assignment4;
/* CRITTERS Command.java
 * EE422C Project 4 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Kylar Osborne
 * kmo785
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

public class Command {
	public static enum CommandType {
    	ERROR, QUIT, SHOW, STEP, SEED, MAKE, STATS
    }
	private CommandType commandType;
	private int number = -1;
	private String className = "";
	
	/**
	 * Unused constructor
	 */
	public Command() {}
	
	public Command(CommandType command) {
		this.commandType = command;
	}
	
	public Command(CommandType command, String className) {
		this.className = className;
		this.commandType = command;
	}
	
	public Command(CommandType command, int number) {
		this.number = number;
		this.commandType = command;
	}
	
	public Command(CommandType command, String className, int number) {
		this.number = number;
		this.className = className;
		this.commandType = command;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public int getCount() {
		return this.number;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public CommandType getCommandType() {
		return this.commandType;
	}
	
	public String toString() {
		return "Command:: " + commandTypeToString(this.commandType) + ": "+this.className + " , " + number;
	}
	
	private String commandTypeToString(CommandType com) {
		switch(com) {
		case ERROR:
			return "Error";
		case QUIT:
			return "Quit";
		case SHOW:
			return "Show";
		case STEP:
			return "Step";
		case SEED:
			return "Seed";
		case MAKE:
			return "Make";
		case STATS:
			return "Stats";
		default:
			return "invalid";
		}
		
	}
}