package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.resource.Resource;

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

    public GameObject createGameObject(Integer x, Integer y,
                                       Integer layer,
                                       Set<HIDEventEnum> boundHIDBehaviors,
                                       Map<OrientationEnum, AnimationSequence> orientationBoundAnimationSequences,
                                       OrientationEnum currentOrientation,
                                       Resource currentResource) {
        GameObject gameObject = new GameObjectImpl(
            x, y,
            layer,
            boundHIDBehaviors,
            orientationBoundAnimationSequences,
            currentOrientation,
            currentResource
        );

        return gameObject;
    }
}
