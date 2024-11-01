package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import config.Configuration;
import config.GameState;
import gameplay.GameplayHandler;
import mouse.MouseHandler;
import tile.TileHandler;
import ui.UiHandler;

public class GamePanel extends JPanel implements Runnable{
	
	public boolean scaledScreen;
	public int screenWidth;
	public int screenHeight;
	public double scaleFactor;
	public int tileSize;
	
	public Graphics2D g2;
	
	public Thread gameThread;
	public int loadingProgress;
	
	public TileHandler tileHandler;
	public UiHandler ui;
	public Configuration config;
	public GameState status;
	public MouseHandler mouseHandler;
	public GameplayHandler gameplay;
	
	public int gameState;
	
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
		// Definir el tamaño de la ventana dentro del constructor
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		if(screenSize.width == Constants.MINIMUM_SCREEN_WIDTH)
			this.screenWidth = Constants.MINIMUM_SCREEN_WIDTH;
		else
			this.screenWidth = screenSize.width;
		
		if(screenSize.height == Constants.MINIMUM_SCREEN_HEIGHT)
			this.screenHeight = Constants.MINIMUM_SCREEN_HEIGHT;
		else
			this.screenHeight = screenSize.height;
		
		// Definir el factor de escala que se utilizara para mantener la visual del juego
		double scaleWidth = (double) this.screenWidth / Constants.MINIMUM_SCREEN_WIDTH;
		double scaleHeight = (double) this.screenHeight / Constants.MINIMUM_SCREEN_HEIGHT;
		
		this.scaleFactor = Math.min(scaleWidth, scaleHeight);
		
		this.tileSize = (int) (Constants.ORIGINAL_TILE_SIZE * this.scaleFactor);
		
		// Instanciar estado
		this.gameState = Constants.STATE_GAME_LOADING;
		this.loadingProgress = 0;
		
		// Instanciar handlers
		this.tileHandler = new TileHandler(this);
		this.ui = new UiHandler(this);
		this.config = new Configuration(this);
		this.mouseHandler = new MouseHandler(this);
		this.gameplay = new GameplayHandler(this);
		
		// Cargar cursor personalizado
		setCursor(this.tileHandler.setCustomCursor(Constants.CURSOR));
	}
	
	// Hilo del juego
	public void startGameThread() {
		this.gameThread = new Thread(this);
		gameThread.start();
		setUpGame();
	}
	
	private void setUpGame() {
		this.status = this.config.loadGame();
		// Si no hay una partida guardada, crea un xml con la info default
		if(this.status == null) {
			this.status = this.config.defaultGameState();
		}
	}

	@Override
	public void run() {
		int fps = Constants.FPS;
		double drawInterval = 1000000000/fps;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		// Variables para medir FPS
		long lastTime = System.currentTimeMillis();
		int frameCount = 0;

		while(gameThread != null) {
			
			 // Verificar si el juego está en estado de carga
	        if (this.gameState == Constants.STATE_GAME_LOADING) {
	            if (this.loadingProgress < 100) {
	                // Actualizar progreso de carga de forma incremental
	                this.loadingProgress++;
	                try {
	                    Thread.sleep(18);  // Simula el tiempo de carga de datos
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                // Cambiar a la pantalla principal cuando la carga esté completa
	                this.gameState = Constants.STATE_PRINCIPAL_MENU;
	                this.loadingProgress = 0;  // Reiniciar el progreso si es necesario
	            }
	        } else {
	            // Actualizar elementos solo si no está en carga
	            update();
	        }

			
			// Refresh de UI
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}


				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
				// Incrementar contador de frames
		        frameCount++;

		        // Calcular y mostrar FPS cada segundo
		        if (System.currentTimeMillis() - lastTime >= 1000) {
		            System.out.println("FPS: " + frameCount);
		            frameCount = 0;
		            lastTime += 1000; // Reiniciar el tiempo para el próximo segundo
		        }
				
			}catch(InterruptedException e) {
				
			}
		}
		
	}
	
	public void update() {}
	
	// Dibujar en la pantalla
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    this.g2 = (Graphics2D) g;

	    // this will move to the handler where the maps are displayed to play
	    // this.tileHandler.loadMap("stage_1");   -- UI
	    
	    // Agregar manejo para game state. Por ahora solo se presentara pantalla de carga
	    
	    
	    // Dibujar la ui
	    this.ui.draw();
	    
	    this.g2.dispose();
	}
}
