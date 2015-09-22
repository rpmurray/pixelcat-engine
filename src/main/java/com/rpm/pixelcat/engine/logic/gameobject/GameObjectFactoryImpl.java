package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesDB;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.util.Map;
import java.util.Set;

class GameObjectFactoryImpl implements GameObjectFactory {
    private PropertiesDB defaultPropertiesDB;

    public GameObjectFactoryImpl(PropertiesDB defaultPropertiesDB) {
        this.defaultPropertiesDB = defaultPropertiesDB;
    }

    public GameObjectProperties createGameObjectProperties() {
        GameObjectProperties gameObjectProperties = new GameObjectPropertiesImpl(defaultPropertiesDB);

        return gameObjectProperties;
    }

    public GameObjectProperties createGameObjectProperties(PropertiesDB propertiesDB) {
        GameObjectProperties gameObjectProperties = new GameObjectPropertiesImpl(propertiesDB);

        return gameObjectProperties;
    }

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       GameObjectProperties properties,
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
            properties,
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
                                       GameObjectProperties properties,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Resource currentResource,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException {
        GameObject gameObject = new GameObjectImpl(
            x, y,
            layer,
            properties,
            gameObjectHIDEventLogicBehaviorBindings,
            currentResource,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );

        return gameObject;
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
            createGameObjectProperties(),
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
            createGameObjectProperties(),
            gameObjectHIDEventLogicBehaviorBindings,
            currentResource,
            collisionHandlingTypeEnum,
            screenBoundsHandlingTypeEnum
        );

        return gameObject;
    }
}
