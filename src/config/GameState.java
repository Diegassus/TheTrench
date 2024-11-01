package config;

public class GameState {
	public boolean gameSaved;
	public int currentMissionIndex;
	public Mission[] missions;
	
	public GameState() {
		this.missions = new Mission[3]; // Despues cargar las 25 misiones
	}
	
	public void setGameSaved(boolean gameSaved) {
		this.gameSaved = gameSaved;
	}
	
	public boolean getGameSaved() {
		return this.gameSaved;
	}
}
