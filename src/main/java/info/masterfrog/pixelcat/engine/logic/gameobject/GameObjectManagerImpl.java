package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;
import info.masterfrog.pixelcat.engine.logic.gameobject.dao.HashMapPropertiesDB;
import info.masterfrog.pixelcat.engine.logic.gameobject.dao.PropertiesDB;
import info.masterfrog.pixelcat.engine.logic.gameobject.dao.PropertiesStorageEnum;
import info.masterfrog.pixelcat.engine.logic.gameobject.feature.Renderable;

import java.util.*;

public class GameObjectManagerImpl extends IdGeneratorImpl implements GameObjectManager {
    private PropertiesDB propertiesDB;
    private Map<String, GameObject> gameObjects;
    private LayerManager layerManager;

    public GameObjectManagerImpl(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws TransientGameException {
        // init game objects
        gameObjects = new HashMap<>();

        // layer setup
        layerManager = new LayerManagerImpl(layers);

        // game object factory setup
        switch (propertiesStorageEnum) {
            case XML:
                throw new TransientGameException(GameErrorCode.INTERNAL_ERROR, "Unsupported game object properties DB");
            case HASH_MAP:
            default:
                propertiesDB = new HashMapPropertiesDB();
                break;
        }
    }

    public GameObject createGameObject() throws TransientGameException {
        GameObject gameObject = GameObject.create(GameObjectProperties.create(propertiesDB));

        return gameObject;
    }

    public GameObjectManager add(GameObject gameObject) throws TransientGameException {
        if (gameObject.hasFeature(Renderable.class)) {
            if (!layerManager.isValidLayer(gameObject.getFeature(Renderable.class).getLayer())) {
                throw new TransientGameException(GameErrorCode.LOGIC_ERROR, gameObject);
            }
        }

        gameObjects.put(gameObject.getId(), gameObject);

        return this;
    }

    public GameObjectManager add(Set<GameObject> gameObjects) throws TransientGameException {
        // add one at a time
        for (GameObject gameObject : gameObjects) {
            add(gameObject);
        }

        return this;
    }

    public Boolean has(String id) {
        return gameObjects.containsKey(id);
    }

    public GameObject get(String id) throws TransientGameException {
        if (!has(id)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        return gameObjects.get(id);
    }

    public Integer count() {
        return gameObjects.size();
    }

    public List<List<GameObject>> getLayeredGameObjects() throws TransientGameException {
        // setup
        List<List<GameObject>> layeredGameObjects = new ArrayList<>();
        for (Integer i = 0; i < layerManager.getLayerCount(); i++) {
            layeredGameObjects.add(i, new ArrayList<>());
        }

        // build layered game objects
        for (String id : gameObjects.keySet()) {
            // setup
            Integer layerIndex = 0;
            GameObject gameObject = gameObjects.get(id);

            // set layer index if applicable
            if (gameObject.hasFeature(Renderable.class)) {
                layerIndex = gameObject.getFeature(Renderable.class).getLayer();
            }

            // add game object to layered set of game objects
            layeredGameObjects.get(layerIndex).add(gameObject);
        }

        return layeredGameObjects;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void process(KernelState kernelState) throws TransientGameException {
        for (String id : gameObjects.keySet()) {
            // setup
            GameObject gameObject = gameObjects.get(id);

            // update
            GameObjectUpdater.getInstance().update(gameObject, kernelState);
        }
    }
}
