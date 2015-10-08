package com.rpm.pixelcat.engine.logic.gameobject.behavior;

public enum BehaviorEnum {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    ANIMATION_PLAY,
    ANIMATION_STOP,
    CAMERA_SWITCH,
    ;

    @Override
    public String toString() {
        return "BehaviorEnum{" +
            "name=" + name() +
            '}';
    }
}
