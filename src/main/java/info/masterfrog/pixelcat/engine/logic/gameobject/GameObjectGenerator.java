package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.common.file.FileLoader;
import info.masterfrog.pixelcat.engine.common.logic.IdGeneratorUtil;
import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

class GameObjectGenerator {
    private static GameObjectGenerator instance = null;

    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectGenerator.class);

    public static GameObjectGenerator getInstance() {
        if (instance == null) {
            instance = new GameObjectGenerator();
        }

        return instance;
    }

    // private constructor for singleton
    private GameObjectGenerator() {
        // do nothing
    }

    public Map<String, GameObject> generateFromXml(String fileName) throws TransientGameException {
        // setup
        Map<String, GameObject> gameObjects = new HashMap<>();

        // load xml file into document object
        Document document = FileLoader.getInstance().loadXmlFile(fileName);
        NodeList nodes = document.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            // setup
            Node node = nodes.item(i);

            switch (node.getNodeName()) {
                case "gameObject":
                    // generate game object handle, by IdGeneratorUtil or from node property
                    String handle = node.getAttributes().getNamedItem("name").getNodeValue();
                    if (handle == null) {
                        handle = IdGeneratorUtil.generateId("xml-game-object");
                    }

                    // convert node to game object
                    gameObjects.put(handle, generateGameObjectFromNode(node));
                    break;
                default:
                    PRINTER.printInfo("Non game object node [" + node.getNodeName() + "] found in root of XML file during game object from XML generation.");
                    break;
            }
        }

        return gameObjects;
    }

    private GameObject generateGameObjectFromNode(Node gameObjectNode) {
        // setup
        GameObject gameObject = null;
        NamedNodeMap gameObjectAttributeNodes = gameObjectNode.getAttributes();
        NodeList gameObjectPropertyNodes = gameObjectNode.getChildNodes();

        // handle attributes
        for (int i = 0; i < gameObjectAttributeNodes.getLength(); i++) {
            switch (gameObjectAttributeNodes.item(i).getNodeName()) {
                default:
                    // do nothing
            }
        }

        // handle properties
        for (int i = 0; i < gameObjectPropertyNodes.getLength(); i++) {
            switch (gameObjectPropertyNodes.item(i).getNodeName()) {
                default:
                    // do nothing
            }
        }

        return gameObject;
    }
}
