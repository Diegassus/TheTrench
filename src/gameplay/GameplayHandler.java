package gameplay;

import main.GamePanel;

public class GameplayHandler {
	private GamePanel gp;
	public Battle currentBattle;
	
	public GameplayHandler(GamePanel gp) {
		this.gp = gp;
		
		
	}
	
	public void loadBattle(String nombre, String enemigos, String mapName) {
		String[] enemies = loadEnemies(enemigos);
		this.currentBattle = new Battle(enemies, nombre, mapName);
	}
	
	public String[] loadEnemies(String enemigos) {
		// Este metodo va a leer el enemigo y cargarlo a una lista para subirlo al juego
		String[] enemies = enemigos.split("-");
		
		
		return enemies;
	}
}
