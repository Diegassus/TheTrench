package main;

import java.awt.Cursor;

import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;

	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setTitle(Constants.TITLE_ES);
		window.setUndecorated(true);
		// Muestra el cursor
		window.setCursor(Cursor.getDefaultCursor());


		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
