package com.rpm.pixelcat.engine.logic.gameobject.behavior;

public class BehaviorParameterCamera implements BehaviorParameter {
    private String cameraId;

    BehaviorParameterCamera(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraId() {
        return cameraId;
    }

    @Override
    public String toString() {
        return "BehaviorParameterCamera{" +
            "cameraId=" + cameraId +
            '}';
    }
}
