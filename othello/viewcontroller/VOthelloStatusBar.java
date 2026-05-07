package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.OthelloBoard;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/** 
 * VIEW class in MVC.
 * Displays game status including player scores, turn indicator,
 * and control buttons (undo, redo, save, load, restart).
 * 
 * Observes the model and refreshes display automatically.
 */
public class VOthelloStatusBar extends HBox implements Observer{
    private Othello othello;
    private OthelloGameController controller;
    private Label statusLabel;
    private Button restartButton;

    private ComboBox<String> p1Choice;
    private ComboBox<String> p2Choice;
    private Button setPlayersButton;

    public VOthelloStatusBar(Othello othello, OthelloGameController controller){
        this.othello = othello;
        this.controller = controller;
        this.setSpacing(10);
    

        statusLabel = new Label();
        //restart button
        restartButton = new Button("Restart Game");
        restartButton.setOnAction(new COthelloRestartHandler(othello));
        restartButton.setStyle("-fx-background-color: #FF69B4; "+ "-fx-text-fill: white;" + "-fx-background-radius: 8; " + "-fx-padding: 5 10;");

        //undo & redo button
        Button undoButton = new Button("Undo Move");
        undoButton.setStyle("-fx-background-color: #274BDB; " + "-fx-text-fill: white; " + "-fx-background-radius: 8; " + "-fx-padding: 5 10;");
        Button redoButton = new Button("Redo Move");
        redoButton.setStyle("-fx-background-color: #274BDB; " + "-fx-text-fill: white; " + "-fx-background-radius: 8; " + "-fx-padding: 5 10;");

        //save and load game button
        Button saveButton = new Button("Save Game");
        saveButton.setStyle("-fx-background-color: #6A4CE0; " + "-fx-text-fill: white; " + "-fx-background-radius: 8; " + "-fx-padding: 5 10;");
        Button loadButton = new Button("Load Game");
        loadButton.setStyle( "-fx-background-color: #6A4CE0; " + "-fx-text-fill: white; " + "-fx-background-radius: 8; " + "-fx-padding: 5 10;");

        undoButton.setOnAction(new COthelloUndoHandler(othello));
        redoButton.setOnAction(new COthelloRedoHandler(othello));
        saveButton.setOnAction(new COthelloSaveHandler(othello));
        loadButton.setOnAction(new COthelloLoadHandler(othello));

        //player type selectors
        p1Choice = new ComboBox<>();
        p1Choice.getItems().addAll("Human", "Random", "Greedy");
        p1Choice.setValue("Human");

        p2Choice = new ComboBox<>();
        p2Choice.getItems().addAll("Human", "Random", "Greedy");
        p2Choice.setValue("Human");

        setPlayersButton = new Button("Set Players");
        setPlayersButton.setOnAction(e -> {
            String p1 = p1Choice.getValue();
            String p2 = p2Choice.getValue();
            controller.setPlayerTypes(p1, p2);
        });

        this.getChildren().addAll(statusLabel, restartButton, undoButton, redoButton, saveButton, loadButton, new Label("P1:"), p1Choice, new Label("P2:"), p2Choice, setPlayersButton);

        refresh();
    }

    @Override
    public void update(Observable o){
        refresh();
    }


    private void refresh(){
        int p1 = othello.getCount(OthelloBoard.P1);
        int p2 = othello.getCount(OthelloBoard.P2);

        if (othello.isGameOver()){
            char winner = othello.getWinner();
            String winnerText;
            if(winner == OthelloBoard.EMPTY){
                winnerText = "Game over: tie";
            } else {
                winnerText = "Game over: " + winner + " wins";
            }
            statusLabel.setText(
                "X: "+p1+" O: "+ p2 + " " + winnerText
            );
        }else{
            statusLabel.setText(
                 "X: "+p1+" O: "+ p2 + " " + othello.getWhosTurn()+ " moves next"
            );
        }
    }
}
