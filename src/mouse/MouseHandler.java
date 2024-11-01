package mouse;

import config.Mission;
import main.Constants;
import main.GamePanel;

public class MouseHandler { // Por ahora maneja los cambios de estado del juego hechos por el usuario
	private GamePanel gp;
	
	public MouseHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	public void newGameClickHandler() {
		this.gp.status = this.gp.config.defaultGameState();
		Mission mission = this.gp.status.missions[this.gp.status.currentMissionIndex];
		this.gp.gameplay.loadBattle(mission.nombre, mission.enemigos, mission.mapName);
		this.gp.gameState = Constants.STATE_PLAY_LEVEL;
	}
	
	public void closeGameClickHandler() {
		System.exit(0);
	}
}
