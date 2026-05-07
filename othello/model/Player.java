package ca.yorku.eecs3311.othello.model;

/**
 * Strategy Pattern interface for move selection.
 * 
 * Concrete strategies implement different play styles
 * such as random or greedy decision-making.
 */
public abstract class Player {
	protected Othello othello;
	protected char player;

	public Player(Othello othello, char player) {
		this.othello=othello;
		this.player=player;
	}
	public char getPlayer() {
		return this.player;
	}
	public abstract Move getMove();
}
