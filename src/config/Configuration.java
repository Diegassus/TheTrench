package config;

import java.io.File;

import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.GamePanel;


public class Configuration {
	
	private static final String SAVE_FILE_PATH = "data/SavedGame.xml";
	private GamePanel gp;
	
	public Configuration(GamePanel gp) {
		this.gp = gp;
	}
	
	// Método para guardar el estado del juego
    public void saveGame(GameState gameState) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear el nodo raíz
            Element root = doc.createElement("GameState");
            doc.appendChild(root);

            // Añadir nodos 
            // GAME SAVED
            Element gameSaved = doc.createElement("GameSaved");
            gameSaved.appendChild(doc.createTextNode(String.valueOf(gameState.getGameSaved())));
            root.appendChild(gameSaved);

            Element batallas = doc.createElement("Battles");
            for (Mission mission : gameState.missions) {
                Element battleElement = doc.createElement("Battle");
                Element node;
                node = doc.createElement("Id");
                node.appendChild(doc.createTextNode(mission.id));
                battleElement.appendChild(node);
                
                node = doc.createElement("Name");
                node.appendChild(doc.createTextNode(mission.nombre));
                battleElement.appendChild(node);
                
                node = doc.createElement("Completed");
                node.appendChild(doc.createTextNode(mission.completado));
                battleElement.appendChild(node);
                
                node = doc.createElement("Enemies");
                node.appendChild(doc.createTextNode(mission.enemigos));
                battleElement.appendChild(node);
                
                node = doc.createElement("Waves");
                node.appendChild(doc.createTextNode(mission.oleadas));
                battleElement.appendChild(node);
                
                node = doc.createElement("Map");
                node.appendChild(doc.createTextNode(mission.mapName));
                battleElement.appendChild(node);
                
                batallas.appendChild(battleElement);
            }
            root.appendChild(batallas);
            /*
            // Nodo de habilidades desbloqueadas
            Element abilities = doc.createElement("UnlockedAbilities");
            for (String ability : gameState.getUnlockedAbilities()) {
                Element abilityElement = doc.createElement("Ability");
                abilityElement.appendChild(doc.createTextNode(ability));
                abilities.appendChild(abilityElement);
            }
            root.appendChild(abilities);*/

            // Guardar en archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SAVE_FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para cargar el estado del juego
    public GameState loadGame() {
    	GameState gameState = new GameState();
        try {
        	
        	File file = new File(SAVE_FILE_PATH);
            if (!file.exists()) return null;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            gameState.setGameSaved(Boolean.parseBoolean(doc.getElementsByTagName("GameSaved").item(0).getTextContent()));
            gameState.currentMissionIndex = 0;
            
            NodeList batallas = doc.getElementsByTagName("Battle");
            for(int i = 0; i < batallas.getLength(); i++) {
            	Element batalla = (Element) batallas.item(i);
            	Mission mision = new Mission();

            	mision.id = batalla.getElementsByTagName("Id").item(0).getTextContent();
            	mision.nombre = batalla.getElementsByTagName("Name").item(0).getTextContent();
            	mision.completado = batalla.getElementsByTagName("Completed").item(0).getTextContent();
            	mision.enemigos = batalla.getElementsByTagName("Enemies").item(0).getTextContent();
            	mision.oleadas = batalla.getElementsByTagName("Waves").item(0).getTextContent();
            	mision.mapName = batalla.getElementsByTagName("Map").item(0).getTextContent();
            	
            	
            	
            	if(Boolean.parseBoolean(mision.completado)) {
            		gameState.currentMissionIndex++;
            		if(gameState.currentMissionIndex == 25)
            			gameState.currentMissionIndex = 24;
            	}
            	
            	gameState.missions[i] = mision;
            }
            
            
            /*NodeList abilityNodes = doc.getElementsByTagName("Ability");
            List<String> abilities = new ArrayList<>();
            for (int i = 0; i < abilityNodes.getLength(); i++) {
                abilities.add(abilityNodes.item(i).getTextContent());
            }
            gameState.setUnlockedAbilities(abilities);*/
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return gameState;
    }
    
    // Esta funcion se utilizara para pruebas
    public GameState defaultGameState() {
    	GameState gameState = new GameState();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear el nodo raíz
            Element root = doc.createElement("GameState");
            doc.appendChild(root);
            gameState.setGameSaved(false);

            // Añadir nodos 
            // GAME SAVED
            Element gameSaved = doc.createElement("GameSaved");
            gameSaved.appendChild(doc.createTextNode(String.valueOf(gameState.getGameSaved())));
            root.appendChild(gameSaved);
            
            // Agregar las misiones y el estado de las mismas
            gameState.missions[0] = new Mission("0","Entrenamiento","False","1-2-2-1","","stage_1");
            gameState.missions[1] = new Mission("1","Primera batalla","False","1-2-2-1","","stage_1");
            gameState.missions[2] = new Mission("2","Segunda batalla","False","1-2-2-1","","stage_1");
            
            Element batallas = doc.createElement("Battles");
            for (Mission mission : gameState.missions) {
                Element battleElement = doc.createElement("Battle");
                Element node;
                node = doc.createElement("Id");
                node.appendChild(doc.createTextNode(mission.id));
                battleElement.appendChild(node);
                
                node = doc.createElement("Name");
                node.appendChild(doc.createTextNode(mission.nombre));
                battleElement.appendChild(node);
                
                node = doc.createElement("Completed");
                node.appendChild(doc.createTextNode(mission.completado));
                battleElement.appendChild(node);
                
                node = doc.createElement("Enemies");
                node.appendChild(doc.createTextNode(mission.enemigos));
                battleElement.appendChild(node);
                
                node = doc.createElement("Waves");
                node.appendChild(doc.createTextNode(mission.oleadas));
                battleElement.appendChild(node);
                
                node = doc.createElement("Map");
                node.appendChild(doc.createTextNode(mission.mapName));
                battleElement.appendChild(node);
                
                batallas.appendChild(battleElement);
            }
            root.appendChild(batallas);
            /*
             *
            // Nodo de habilidades desbloqueadas
            Element abilities = doc.createElement("UnlockedAbilities");
            for (String ability : gameState.getUnlockedAbilities()) {
                Element abilityElement = doc.createElement("Ability");
                abilityElement.appendChild(doc.createTextNode(ability));
                abilities.appendChild(abilityElement);
            }
            root.appendChild(abilities);*/

            // Guardar en archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SAVE_FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return gameState;
    }
}
