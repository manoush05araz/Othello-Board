package ca.yorku.eecs3311.othello.model;

/**
 * Concrete Visitor in the Visitor Pattern.
 * 
 * Counts the number of tokens belonging to a given player.
 */
public class TokenCountVisitor implements OthelloBoardVisitor {
	private final char player;
	private int count;
	
	public TokenCountVisitor(char player) {
		this.player = player;
		this.count = 0;
	}
	
	@Override
	public void visitCell(int row, int col, char token) {
		if (token == player) {
			count++;
		}
	}
	
	public int getCount() {
		return count;
	}
}
