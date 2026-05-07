package ca.yorku.eecs3311.othello.model;

/**
 * Concrete Command for making a move.
 * Stores row/col and calls Othello.move() and Othello.undo().
 */
public class MoveCommand implements Command{
	private Othello othello;
	private int row, col;
	
	public MoveCommand(Othello othello, int row, int col) {
		this.othello = othello;
		this.row = row;
		this.col = col;
	}
	
	@Override
	public boolean execute() {
		return othello.move(row, col);
	}
	
	@Override
	public void undo() {
		othello.undo();
	}
}
