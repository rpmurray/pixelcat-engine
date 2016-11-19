package info.masterfrog.pixelcat.engine.logic.gameobject.manager;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering.Rendering;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.RenderingLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.GameObjectProperties;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao.HashMapPropertiesDB;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao.PropertiesDB;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.dao.PropertiesStorageEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class GameObjectManagerImpl extends IdGeneratorImpl implements GameObjectManager {
    private PropertiesDB propertiesDB;
    private Map<String, GameObject> gameObjects;
    private LayerManager layerManager;

    GameObjectManagerImpl(Integer layers, PropertiesStorageEnum propertiesStorageEnum) throws TransientGameException {
        // init game objects
        gameObjects = new HashMap<>();

        // layer setup
        layerManager = LayerManagerFactory.create(layers);

        // game object factory setup
        switch (propertiesStorageEnum) {
            case XML:
                throw new TransientGameException(GameEngineErrorCode.INTERNAL_ERROR, "Unsupported game object properties DB");
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
        if (gameObject.hasFeature(RenderingLibrary.class)) {
            for (String id : gameObject.getFeature(RenderingLibrary.class).getAll().keySet()) {
                Rendering rendering = gameObject.getFeature(RenderingLibrary.class).get(id);
                if (!layerManager.isValidLayer(rendering.getLayer())) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, gameObject);
                }
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

    public GameObjectManager remove(String id) throws TransientGameException {
            if (!has(id)) {
                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, id);
            }

        gameObjects.remove(id);

        return this;
    }

    public GameObjectManager remove(Set<String> ids) throws TransientGameException {
        // add one at a time
        for (String id : ids) {
            remove(id);
        }

        return this;
    }

    public Boolean has(String id) {
        return gameObjects.containsKey(id);
    }

    public GameObject get(String id) throws TransientGameException {
        if (!has(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return gameObjects.get(id);
    }

    public Integer count() {
        return gameObjects.size();
    }

    public Map<String, GameObject> getGameObjects() {
        return gameObjects;
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
            if (gameObject.hasFeature(RenderingLibrary.class)) {
                layerIndex = gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas()).getLayer();
            }

            // add game object to layered set of game objects
            layeredGameObjects.get(layerIndex).add(gameObject);
        }

        return layeredGameObjects;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void process() throws TransientGameException {
        for (String id : gameObjects.keySet()) {
            // setup
            GameObject gameObject = gameObjects.get(id);

            // update
            GameObjectUpdater.getInstance().update(gameObject);
        }
    }
}
