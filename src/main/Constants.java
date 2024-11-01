package main;

public class Constants {
	
	// GAME STATES
	public static final int STATE_GAME_LOADING = 0;
	public static final int STATE_PRINCIPAL_MENU = 1;
	public static final int STATE_PLAY_LEVEL = 2;
	
	// UI VARIABLES
	public static final String CURSOR = "cursor";
	
	// GENERAL
	public static final String TITLE_ES = "La Trinchera";
	
	
	// FPS
	public static final int FPS = 60;
	
	// WINDOW VARIABLES
	public static final int WORLD_MAX_COLUMNS = 16;
	public static final int WORLD_MAX_ROWS = 9;
	public static final int ORIGINAL_TILE_SIZE = 80;
	public static final int MINIMUM_SCREEN_WIDTH = WORLD_MAX_COLUMNS * ORIGINAL_TILE_SIZE;
	public static final int MINIMUM_SCREEN_HEIGHT = WORLD_MAX_ROWS * ORIGINAL_TILE_SIZE;
	
	
}
