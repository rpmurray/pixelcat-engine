package com.rpm.pixelcat.engine.logic.gameobject.behavior;

public class BehaviorParameterFactory {
    private static BehaviorParameterFactory instance;

    private BehaviorParameterFactory() {
        // do nothing
    }

    public static BehaviorParameterFactory getInstance() {
        if (instance == null) {
            instance = new BehaviorParameterFactory();
        }

        return instance;
    }

    public BehaviorParameterMagnitude createMagnitudeParameter(Double magnitude) {
        BehaviorParameterMagnitude parameter = new BehaviorParameterMagnitude(
            magnitude
        );

        return parameter;
    }

    public BehaviorParameterCamera createCameraParameter(String cameraId) {
        BehaviorParameterCamera parameter = new BehaviorParameterCamera(
            cameraId
        );

        return parameter;
    }
}
