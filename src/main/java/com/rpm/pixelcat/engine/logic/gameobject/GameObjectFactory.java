package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.gameobject.dao.PropertiesDB;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.util.Map;
import java.util.Set;

public interface GameObjectFactory {
    public GameObjectProperties createGameObjectProperties();

    public GameObjectProperties createGameObjectProperties(PropertiesDB propertiesDB);

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       GameObjectProperties properties,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                                       OrientationEnum currentOrientation,
                                       Resource currentResource,
                                       Boolean animationEnabled,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException;

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       GameObjectProperties properties,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Resource currentResource,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException;

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                                       OrientationEnum currentOrientation,
                                       Resource currentResource,
                                       Boolean animationEnabled,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException;

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       Set<GameObjectHIDEventLogicBehaviorBinding> gameObjectHIDEventLogicBehaviorBindings,
                                       Resource currentResource,
                                       CollisionHandlingTypeEnum collisionHandlingTypeEnum,
                                       ScreenBoundsHandlingTypeEnum screenBoundsHandlingTypeEnum) throws GameException;
}
