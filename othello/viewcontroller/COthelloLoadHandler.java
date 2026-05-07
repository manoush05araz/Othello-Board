package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class COthelloLoadHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	
	public COthelloLoadHandler(Othello othello) {
		this.othello = othello;
	}
	
	@Override
	public void handle(ActionEvent event) {
		File file = new File("othello_save.txt");
		if (!file.exists()) {
			System.out.println("No saved game found.");
			return;
		}
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		othello.deserialize(sb.toString());
		System.out.println("Game loaded from " + file.getAbsolutePath());
	}
}
