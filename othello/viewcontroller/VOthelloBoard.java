package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.OthelloBoard;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Displays the game board using a grid of buttons.
 * This view observes the Othello model and updates whenever the model changes.
 * 
 * User interactions are forwarded to the controller.
 * Observes the Othello model (Observer pattern).
 */
public class VOthelloBoard extends GridPane implements Observer {
    private Othello othello;
    private OthelloGameController controller;
    private Button[][] cells;

    public VOthelloBoard(Othello othello, OthelloGameController controller){
        this.othello = othello;
        this.controller = controller;

        int dim = Othello.DIMENSION;
        this.cells = new Button[dim][dim];

        this.setHgap(2);
        this.setVgap(2);

        for (int row = 0; row < dim; row++){
            for (int col = 0; col < dim; col++){
                Button b = new Button();
                b.setMinSize(40, 40); 

                //what happens when this square is clicked
                final int r = row;
                final int c = col;
                b.setOnAction(e -> controller.handleHumanClick(r, c));

                this.add(b, col, row); // (node, column, row)
                cells[row][col] = b;
            }
        }
        refresh();
    }

    @Override
    public void update(Observable o){
        //redraw board if model changes
        refresh();
    }

    private void refresh(){
        int dim = Othello.DIMENSION;
        for (int row = 0; row < dim; row++){
            for (int col = 0; col < dim; col++){
                char token = othello.getToken(row, col);
                Button b = cells[row][col];

                if (token == OthelloBoard.P1){
                    b.setText("X");
                } else if (token == OthelloBoard.P2){
                    b.setText("O");
                } else {
                    b.setText("");
                }
            }
        }
    }
}
