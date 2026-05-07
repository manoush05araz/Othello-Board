package ca.yorku.eecs3311.othello.model;
import ca.yorku.eecs3311.util.*;
import java.util.Random;
import java.util.Stack;

/**
 * Acts as the Receiver in the Command Pattern.
 * 
 * This class stores the full game state including board data,
 * current player, move history, undo/redo logic, and persistence.
 * 
 * This class is Observable and notifies all registered views
 * whenever the game state changes.
 
 * Saves and loads the game's state to/from a text file.
 * Works with the Visitor pattern to serialize the board.
 */
public class Othello extends Observable{
	public static final int DIMENSION=8; // This is an 8x8 game

	private OthelloBoard board=new OthelloBoard(Othello.DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;

	private Stack<Othello> undoStack = new Stack<>();
	private Stack<Othello> redoStack = new Stack<>();

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}
	
	/**
	 * 
	 * @param row 
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		return this.board.get(row, col);
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		//save this current state
		Othello snapshot = this.copy();

		if(this.board.move(row, col, this.whosTurn)) {
			//go back to previous state when undo
			undoStack.push(snapshot);
			//when new move, redo is no longer valid
			redoStack.clear();

			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			char allowedMove = board.hasMove();
			if(allowedMove!=OthelloBoard.BOTH)this.whosTurn=allowedMove;
			this.numMoves++;

			//notify views that the game changed:
			this.notifyObservers();
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}


	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if(!this.isGameOver())return OthelloBoard.EMPTY;
		if(this.getCount(OthelloBoard.P1)> this.getCount(OthelloBoard.P2))return OthelloBoard.P1;
		if(this.getCount(OthelloBoard.P1)< this.getCount(OthelloBoard.P2))return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}


	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return this.whosTurn==OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o= new Othello();
		o.board=this.board.copy();
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}

	//copy data from another Othello into this one
	private void copyFrom(Othello other){
		this.board = other.board.copy();
		this.whosTurn = other.whosTurn;
		this.numMoves = other.numMoves;
	}

	public void undo(){
		if (!undoStack.isEmpty()){
			//current state goes to redo
			redoStack.push(this.copy());
			//previous state becomes current
			Othello prev = undoStack.pop();
			copyFrom(prev);
			this.notifyObservers();
		}
	}

	public void redo(){
		if (!redoStack.isEmpty()){
			//current state goes back onto undo
			undoStack.push(this.copy());
			Othello next = redoStack.pop();
			copyFrom(next);
			this.notifyObservers();
		}
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString()+"\n";
	}

	/**
	 * Converts current game state into a text format for saving.
	 */
	public String serialize() {
		StringBuilder sb = new StringBuilder();

		// whos turn is it
		sb.append(this.whosTurn).append('\n');
		// number of moves
		sb.append(this.numMoves).append('\n');
		//dimension
		int dim = this.board.getDimension();
		sb.append(dim).append('\n');

		// board contents
		for (int row = 0; row < dim; row++){
			for (int col = 0; col < dim; col++){
				sb.append(this.board.get(row, col));
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	/**
	 * Restores game state from serialized text data.
	 */
	public void deserialize(String data){
		if (data == null){
			return;
		}

		String[] lines = data.split("\\R"); //split on newlines
		if (lines.length < 3) {
			return; //not enough data 
		}

		//whos turn is it
		this.whosTurn = lines[0].charAt(0);
		//number of moves
		this.numMoves = Integer.parseInt(lines[1]);
		//dimension
		int dim = Integer.parseInt(lines[2]);

		OthelloBoard newBoard = new OthelloBoard(dim);

		//board row
		for (int row = 0; row < dim; row++){
			if (3 + row >= lines.length){
				break;
			}
			String rowLine = lines[3 + row];
			for (int col = 0; col < dim && col < rowLine.length(); col++){
				char token = rowLine.charAt(col);
				if (token != OthelloBoard.P1 && token != OthelloBoard.P2){
					token = OthelloBoard.EMPTY;
				}
				newBoard.set(row, col, token);
			}
		}
		this.board = newBoard;

		//forget old history
		undoStack.clear();
		redoStack.clear();

		//notify GUI
		this.notifyObservers();
	}

	//Reset to restart the game
public void reset(){
	this.board = new OthelloBoard(DIMENSION);
	this.whosTurn = OthelloBoard.P1;
	this.numMoves = 0;

	undoStack.clear();
	redoStack.clear();
	this.notifyObservers(); 
}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Random rand = new Random();


		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while(!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if(o.move(row, col)) {
				System.out.println("makes move ("+row+","+col+")");
				System.out.println(o.getBoardString()+ o.getWhosTurn()+" moves next");
			}
		}

	}
}


