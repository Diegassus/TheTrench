package tile;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Constants;
import main.GamePanel;
import main.UtilityTool;

public class TileHandler {
	private GamePanel gp;
	public Tile[] gameTiles;
	public int[][] mapTile;
	private UtilityTool tool;
	
	public TileHandler(GamePanel gp) {
		this.gp = gp;
		
		this.gameTiles = new Tile[150];
		this.mapTile = new int[Constants.WORLD_MAX_COLUMNS][Constants.WORLD_MAX_ROWS];
		this.tool = new UtilityTool();
		
		getTileImage();
		
	}
	
	private void getTileImage() {
		setup(0, "tile_mark");
	}
	
	public void setup(int index, String imageName) {
		try {
			this.gameTiles[index] = new Tile();
			this.gameTiles[index].image = tool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName +".png")), gp.tileSize, gp.tileSize);

		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void loadMap(String map){
		try {
			InputStream is = getClass().getResourceAsStream("/maps/" + map + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < Constants.WORLD_MAX_COLUMNS && row < Constants.WORLD_MAX_ROWS) {
				String line = br.readLine();
				
				while(col < Constants.WORLD_MAX_COLUMNS) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					this.mapTile[col][row] = num;
					col++;
				}
				if(col == Constants.WORLD_MAX_COLUMNS) {
					col = 0;
					row++;
				}
			}
			br.close();
			
			// draw map
			draw(gp.g2);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Draw the map into the screen
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < Constants.WORLD_MAX_COLUMNS && worldRow < Constants.WORLD_MAX_ROWS) {
			
			int tileNumber = this.mapTile[worldCol][worldRow];
			
			int screenX = worldCol * gp.tileSize;
			int screenY = worldRow * gp.tileSize;
			
			g2.drawImage(this.gameTiles[tileNumber].image, screenX, screenY, null);
			
			worldCol++;
			
			if(worldCol == Constants.WORLD_MAX_COLUMNS) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
	public Cursor setCustomCursor(String imageName) {
		try {
			BufferedImage originalCursor = ImageIO.read(getClass().getResourceAsStream("/objects/"+ imageName +".png"));
			int cursorWidth = (int) (originalCursor.getWidth() * gp.scaleFactor);
			int cursorHeight = (int) (originalCursor.getHeight() * gp.scaleFactor);
			
			BufferedImage cursor = tool.scaleImage(originalCursor, cursorWidth, cursorHeight);
			
			// Agregar imagen del cursor despues de escalarla
			Point hotspot = new Point(0, 0); // Punto "activo" del cursor
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotspot, "Custom Cursor");
		
            return customCursor;
		}catch(Exception e){
			e.printStackTrace();
			return Cursor.getDefaultCursor(); // Si no funciona, usara el default
		}
	}
}
