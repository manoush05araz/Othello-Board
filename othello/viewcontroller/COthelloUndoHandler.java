package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class COthelloUndoHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	
	public COthelloUndoHandler(Othello othello) {
		this.othello = othello;
	}
	
	@Override
	public void handle(ActionEvent e) {
		othello.undo();
	}
}
