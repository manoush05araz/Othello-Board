package ca.yorku.eecs3311.othello.viewcontroller;
import ca.yorku.eecs3311.othello.model.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
/**
 * This class initializes the MVC setup.
 * It constructs the Model (Othello), the Views (VOthelloBoard, VOthelloStatusBar),
 * and the Controller (OthelloGameController). 
 * It also wires Model→View and View→Controller connections.
 */
public class OthelloApplication extends Application {
	// REMEMBER: To run this in the lab put 
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well.
	
	@Override
	public void start(Stage stage) throws Exception {
		// Create and hook up the Model, View and the controller
		
		// MODEL
		Othello othello =new Othello();
		
		// CONTROLLER
		OthelloGameController controller = new OthelloGameController(othello);
		// CONTROLLER->MODEL hookup
	
		// VIEW
		VOthelloBoard boardView = new VOthelloBoard(othello, controller);
		VOthelloStatusBar statusView = new VOthelloStatusBar(othello, controller);
		
		// VIEW->CONTROLLER hookup
		
		// MODEL->VIEW hookup
		othello.attach(boardView);
		othello.attach(statusView);

		// LAYOUT
		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setAlignment(Pos.TOP_CENTER); //center the board
		
		HBox boardContainer = new HBox(boardView);
		boardContainer.setAlignment(Pos.CENTER);

		root.getChildren().addAll(statusView, boardContainer);
		
		// SCENE
		Scene scene = new Scene(root); 
		stage.setTitle("Othello");
		stage.setScene(scene);
				
		// LAUNCH THE GUI
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
