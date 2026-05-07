package ca.yorku.eecs3311.othello.model;

/**
 * Command Pattern interface.
 * Supports execute() and undo() for game actions.
 */
public interface Command {
	
	/** execute the command
	 * 
	 * @return true if the command succesfully changed the model,
	 * false if nothing was done (for example, an invalid move).
	 */
    boolean execute();
    
    /**
     * Undo the effects of execute()
     * 
     */
    void undo();
}
