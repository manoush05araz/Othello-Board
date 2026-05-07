package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.*;
import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.OthelloBoard;
import ca.yorku.eecs3311.othello.model.Player;
import ca.yorku.eecs3311.othello.model.PlayerGreedy;
import ca.yorku.eecs3311.othello.model.PlayerRandom;

/**
 * CONTROLLER in MVC.
 * Handles user interactions and manages player strategies.
 * Decides whether moves are from humans or AI strategies.
 */
public class OthelloGameController {
    private Othello othello;
    private Player p1Strategy; //null = human
    private Player p2Strategy;

    public OthelloGameController(Othello othello){
        this.othello = othello;
        //default both human
        this.p1Strategy = null;
        this.p2Strategy = null;
    }

    public void setPlayerTypes(String p1Type, String p2Type){
        this.p1Strategy = createPlayerStrategy(p1Type, OthelloBoard.P1);
        this.p2Strategy = createPlayerStrategy(p2Type, OthelloBoard.P2);

        //restart game after changing players
        othello.reset();

        makeOneMoveIfNeeded();
    }

    private Player createPlayerStrategy(String type, char playerToken){
        if (type == null){
            return null;
        }

        switch(type){
            case "Random":
                return new PlayerRandom(othello, playerToken);
            case "Greedy":
                return new PlayerGreedy(othello, playerToken);
            case "Human":
            default:
                return null;
        }
    }

      private boolean isHumanTurn() {
    char turn = othello.getWhosTurn();
    if (turn == OthelloBoard.P1){
        return p1Strategy == null;
    } else if (turn == OthelloBoard.P2){
        return p2Strategy == null;
    }
    return true;
    }



    //when user clicks a square on the board
    public void handleHumanClick(int row, int col){
        if (othello.isGameOver()){
            return;
        }

       if (!isHumanTurn()){
           return; //computer's turn
       }

       //try human move
       Command cmd = new MoveCommand(othello, row, col);
       if (cmd.execute()){
           makeOneMoveIfNeeded();
       }
    }

    public void makeOneMoveIfNeeded() {
    if (othello.isGameOver()) return;

    //computer vs computer
    if (p1Strategy != null && p2Strategy != null){
        runComputerTurns();
        return;
    }

    //Human vs computer
    if (isHumanTurn()) return; //human's turn

    char turn = othello.getWhosTurn();
    Player current = (turn == OthelloBoard.P1) ? p1Strategy : p2Strategy;
    if (current == null){
        return;
    }
    Move m = current.getMove();
    Command cmd = new MoveCommand(othello, m.getRow(), m.getCol());
    cmd.execute();
}

    private void runComputerTurns(){
        while (!othello.isGameOver()){
             if (isHumanTurn()) break; // stop if human

            char turn = othello.getWhosTurn();
            Player current = (turn == OthelloBoard.P1) ? p1Strategy : p2Strategy;
            if (current == null) {
                break;
            };
            Move m = current.getMove();
            Command cmd = new MoveCommand(othello, m.getRow(), m.getCol());
            cmd.execute();
        }
    }
}
