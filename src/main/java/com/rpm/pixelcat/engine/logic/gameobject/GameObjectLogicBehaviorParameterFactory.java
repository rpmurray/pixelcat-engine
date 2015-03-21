package com.rpm.pixelcat.engine.logic.gameobject;

public class GameObjectLogicBehaviorParameterFactory {
    private static GameObjectLogicBehaviorParameterFactory instance;

    private GameObjectLogicBehaviorParameterFactory() {
        // do nothing
    }

    public static GameObjectLogicBehaviorParameterFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectLogicBehaviorParameterFactory();
        }

        return instance;
    }

    public GameObjectLogicBehaviorParameterMagnitude createMagnitudeParameter(Double magnitude) {
        GameObjectLogicBehaviorParameterMagnitude parameter = new GameObjectLogicBehaviorParameterMagnitude(
            magnitude
        );

        return parameter;
    }

    public GameObjectLogicBehaviorParameterOrientation createOrientationParameter(OrientationEnum orientationEnum) {
        GameObjectLogicBehaviorParameterOrientation parameter = new GameObjectLogicBehaviorParameterOrientation(
            orientationEnum
        );

        return parameter;
    }
}
