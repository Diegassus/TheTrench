package gameplay;

public class Battle {
	public String[] tropas; // Esto deberia ser una clase Entity
	public String[] enemigos; // Tambien un entity
	public String missionName;
	public String oleadas; // trabajar el objeto para oleadas
	public String mapName;
	
	public Battle(String[] enemigos, String missionName, String mapName) {
		this.enemigos = enemigos;
		this.missionName = missionName;
		this.mapName = mapName;
	}
	
	public void loadTroops(String[] tropas) {
		this.tropas = tropas;
	}
}
