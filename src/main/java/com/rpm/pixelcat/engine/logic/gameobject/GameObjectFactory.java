package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.util.Map;
import java.util.Set;

public class GameObjectFactory {
    private static GameObjectFactory instance;

    private GameObjectFactory() {
        // do nothing
    }

    public static GameObjectFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectFactory();
        }

        return instance;
    }

    public GameObjectManager createGameObjectManager(Integer layers) throws GameException {
        GameObjectManager gameObjectManager = new GameObjectManagerImpl(layers);

        return gameObjectManager;
    }

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                                       OrientationEnum currentOrientation,
                                       Resource currentResource,
                                       Boolean animationEnabled,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        GameObject gameObject = new GameObjectImpl(
            x, y,
            layer,
            gameObjectHIDEventLogicBehaviorBindings,
            orientationBoundAnimationSequences,
            currentOrientation,
            currentResource,
            animationEnabled,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );

        return gameObject;
    }

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Resource currentResource,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        GameObject gameObject = new GameObjectImpl(
            x, y,
            layer,
            gameObjectHIDEventLogicBehaviorBindings,
            currentResource,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );

        return gameObject;
    }
}
