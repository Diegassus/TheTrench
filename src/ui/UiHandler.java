package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gameplay.Battle;
import main.Constants;
import main.GamePanel;
import main.UtilityTool;

public class UiHandler {
	private Graphics2D g2;
	private GamePanel gp;

    private JProgressBar loadingGameBar;

    // PRINCIPAL MENU UI ELEMENTS
    private Rectangle principalMenu_newGame;
    private boolean isHooverPrincipalMenu_newGame;
    private Rectangle principalMenu_loadGame;
    private boolean isHooverPrincipalMenu_loadGame;
    private Rectangle principalMenu_closeGame;
    private boolean isHooverPrincipalMenu_closeGame;
    
    private Rectangle principalMenu_confirmNewGame;
    private Rectangle principalMenu_cancelNewGame;
    private boolean isActivePrincipalMenu_popUpConfirmNewGame;
    // private Rectangle principalMenu_popUpConfirmNewGame;
    
    private BufferedImage principalMenu_popUpConfirmNewGame;
    private BufferedImage principalMenu_confirmNewGame_image;
    private BufferedImage principalMenu_cancelNewGame_image;
    
	
	public UiHandler(GamePanel gp) {
		this.gp = gp;
		this.gp.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;             // Posición x en la cuadrícula
	    gbc.gridy = 0;             // Posición y en la cuadrícula
	    gbc.insets = new Insets(10, 10, 10, 10); // Espaciado alrededor del progressBar
	    gbc.anchor = GridBagConstraints.CENTER;  // Alineación en el centro
	    gbc.fill = GridBagConstraints.NONE;      // Evita que el progressBar se expanda
		
		// CONFIGURACION BARRA DE CARGA PANTALLA DE LOADING - INICIO
		this.loadingGameBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        this.loadingGameBar.setStringPainted(true);
        this.loadingGameBar.setString("Cargando...");
        
        // this.loadingGameBar.setBorderPainted(false);           // Sin borde
        // this.loadingGameBar.setBackground(Color.DARK_GRAY);    // Fondo oscuro
        this.loadingGameBar.setForeground(Color.GREEN);
        
        int progressBarWidth = (int) (this.gp.screenWidth * 0.4);  // 40% del ancho de la pantalla
        int progressBarHeight = 45;
        this.loadingGameBar.setPreferredSize(new Dimension(progressBarWidth, progressBarHeight));
        
        this.gp.add(this.loadingGameBar, gbc);
        
        principalMenuElements();
        
        // cargar manejo de los handlers
        this.gp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
        
        this.gp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseHover(e);
            }
        });
	}
	
	private void principalMenuElements() {
		
		try {
			UtilityTool tool = new UtilityTool();
			int x = this.gp.screenWidth/2;
			int y = this.gp.screenHeight/2;
			
			this.principalMenu_loadGame = new Rectangle(600, 200, 150, 50);  // Coordenadas y tamaño para cada botón
			this.principalMenu_newGame = new Rectangle(600, 300, 150, 50);
			this.principalMenu_closeGame = new Rectangle(600, 400, 150, 50);
			this.principalMenu_popUpConfirmNewGame = tool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/principalMenu/popup_confirm_newGame.png")), this.gp.tileSize*4, this.gp.tileSize*4);

			this.principalMenu_cancelNewGame_image = tool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/genericUi/red_button.png")), this.gp.tileSize, this.gp.tileSize/2);
			this.principalMenu_cancelNewGame = new Rectangle(0 ,0, this.gp.tileSize, this.gp.tileSize/2);
			this.principalMenu_confirmNewGame_image = tool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/genericUi/green_button.png")), this.gp.tileSize, this.gp.tileSize/2);
			this.principalMenu_confirmNewGame = new Rectangle(0 , 0, this.gp.tileSize, this.gp.tileSize/2);
			
			
			this.isHooverPrincipalMenu_newGame = false;
			this.isHooverPrincipalMenu_loadGame = false;
			this.isHooverPrincipalMenu_closeGame = false;
			this.isActivePrincipalMenu_popUpConfirmNewGame = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void draw() {
		this.g2 = this.gp.g2;
		// Dibuja la UI dependiendo del gameState
		int state = this.gp.gameState;
		
		switch(state) {
		case Constants.STATE_GAME_LOADING:
			gameLoadingScreen();
			break;
		case Constants.STATE_PRINCIPAL_MENU:
			principalMenuScreen();
			break;
		case Constants.STATE_PLAY_LEVEL:
			// consultar al gp cual es el nivel que hay que cargar
			// algo como current game. Despues si guarda/ completa el lvl se modifica el status y se guarda
			battleScreen();
			break;
		}
	}
	
	public void gameLoadingScreen() {
		this.loadingGameBar.setValue(gp.loadingProgress);
        if (this.gp.loadingProgress == 100) {
        	this.loadingGameBar.setString("Carga completa");
        	this.loadingGameBar.setVisible(false);  // Oculta la barra al terminar
        }
	}
	
	public void principalMenuScreen() {
		int x = 0;
		int y = 0;
		
		
        // DIALOG PARA CONFIRMAR NUEVA PARTIDA - BORRA LA ANTERIOR CONFIGURACION
        if(this.isActivePrincipalMenu_popUpConfirmNewGame) {
        	x = this.gp.tileSize*6;
        	y = (this.gp.tileSize*2 + (this.gp.tileSize/2));
        	
        	g2.drawImage(principalMenu_popUpConfirmNewGame, x, y, null);
        	
        	y += principalMenu_popUpConfirmNewGame.getHeight() - principalMenu_confirmNewGame_image.getHeight() - gp.tileSize/2;
        	x += gp.tileSize/2;
        	this.principalMenu_confirmNewGame.x = x;
        	this.principalMenu_confirmNewGame.y = y;
        	g2.fill(principalMenu_confirmNewGame);
        	g2.drawImage(principalMenu_confirmNewGame_image, x, y, null);
        	
        	
        	x += principalMenu_popUpConfirmNewGame.getWidth() - gp.tileSize*2;
        	this.principalMenu_cancelNewGame.x = x;
        	this.principalMenu_cancelNewGame.y = y;
        	g2.fill(principalMenu_cancelNewGame);
        	g2.drawImage(principalMenu_cancelNewGame_image, x, y, null);
        } else {
        	
        	if(this.isHooverPrincipalMenu_newGame)
    			g2.setColor(Color.darkGray);
    		else
    			g2.setColor(Color.GRAY);
    		g2.fill(principalMenu_newGame);
    		
    		if(this.isHooverPrincipalMenu_loadGame || !this.gp.status.gameSaved)
    			g2.setColor(Color.darkGray);
    		else
    			g2.setColor(Color.GRAY);
            g2.fill(principalMenu_loadGame);
    		
    		if(this.isHooverPrincipalMenu_closeGame)
    			g2.setColor(Color.darkGray);
    		else
    			g2.setColor(Color.GRAY);
            g2.fill(principalMenu_closeGame);
        	
            g2.setColor(Color.WHITE);
            g2.drawString("Cargar Partida", principalMenu_loadGame.x + 10, principalMenu_loadGame.y + 30);
            g2.drawString("Juego Nuevo", principalMenu_newGame.x + 10, principalMenu_newGame.y + 30);
            g2.drawString("Salir", principalMenu_closeGame.x + 10, principalMenu_closeGame.y + 30);
        }

        
	}
	
	// Metodo para dibujar lo que seria la UI de batalla (Menu de tropas, avance). Todo lo que es juego
	public void battleScreen() {
		// obtener el mapa del nivel que se esta jugando (seria el del currentGameIndex)
		Battle battle = this.gp.gameplay.currentBattle;
		this.gp.tileHandler.loadMap(battle.mapName); 
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
		g2.drawString(battle.missionName, 800, 500);
	}
	
	
	// Mover a un click handler personalizado.
	private void handleMouseClick(MouseEvent e) {
        if (principalMenu_loadGame.contains(e.getPoint()) && this.gp.status.gameSaved) {
            System.out.println("Cargar Partida clickeado");
            // Lógica para cargar partida
        } else if (principalMenu_newGame.contains(e.getPoint())) { // Abrir popup new game
        	//if(this.gp.status.gameSaved) {  DESCOMENTAR AL TERMINAR DESARROLLO POPUP
           		openConfirmationDialog();
        	//}else {
        	//	this.gp.mouseHandler.newGameClickHandler();
        	//}
        } else if (principalMenu_closeGame.contains(e.getPoint())) { // cerrar juego
            this.gp.mouseHandler.closeGameClickHandler();
        } else if (principalMenu_confirmNewGame.contains(e.getPoint())) { // nueva partida
        	this.gp.mouseHandler.newGameClickHandler();
        }else if (principalMenu_cancelNewGame.contains(e.getPoint())) { // nueva partida
        	closeConfirmationDialog();
        }
    }
	
	private void handleMouseHover(MouseEvent e) {
        Point mousePos = e.getPoint();

        this.isHooverPrincipalMenu_newGame = principalMenu_newGame.contains(mousePos);
		this.isHooverPrincipalMenu_loadGame = principalMenu_loadGame.contains(mousePos);
		this.isHooverPrincipalMenu_closeGame = principalMenu_closeGame.contains(mousePos);

    }
	
	// Acciones para realizar en los handlers del mouse
	private void openConfirmationDialog() {
		this.isActivePrincipalMenu_popUpConfirmNewGame = true;
	}
	
	private void closeConfirmationDialog() {
		this.isActivePrincipalMenu_popUpConfirmNewGame = false;
	}
}
