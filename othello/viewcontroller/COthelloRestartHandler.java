package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class COthelloRestartHandler implements EventHandler<ActionEvent> {
    private Othello othello;
    public COthelloRestartHandler(Othello othello){
        this.othello = othello;
    }

    @Override
    public void handle(ActionEvent event){
        othello.reset();
    }
}
