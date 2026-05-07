package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class COthelloMoveHandler implements EventHandler<ActionEvent>{

    private Othello othello;
    private int row;
    private int col;

    public COthelloMoveHandler(Othello othello, int row, int col){
        this.othello = othello;
        this.row = row;
        this.col = col;
    }

    @Override
    public void handle(ActionEvent event){
        //try to make move and if it is illegal, Othello.move returns false
        othello.move(row, col); 
    }
}
