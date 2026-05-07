package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class COthelloSaveHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	
	public COthelloSaveHandler(Othello othello) {
		this.othello = othello;
	}
	
	@Override
	public void handle(ActionEvent event) {
		File file = new File("othello_save.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(othello.serialize());
			System.out.println("Game saved to " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
