package config;

public class Mission {
	public String id;
	public String nombre;
	public String completado;
	public String enemigos;
	public String oleadas;
	public String mapName;
	
	public Mission() {
	
	}
	
	public Mission(String id, String nombre, String completado, String enemigos, String oleadas, String mapName) {
		this.id = id;
		this.nombre = nombre;
		this.completado = completado;
		this.enemigos = enemigos;
		this.oleadas = oleadas;
		this.mapName = mapName;
	}
	
	@Override
	public String toString() {
		return "{"+ this.id + ", " + this.nombre + ", " + this.completado + ", " + this.enemigos + ", " + this.oleadas + ", " + this.mapName  +"}";
	}
}
