package com.rpm.pixelcat.engine.logic.gameobject.behavior;

public enum BehaviorEnum {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    ANIMATION_PLAY,
    ANIMATION_STOP,
    CAMERA_SWITCH,
    LOGIC_ROUTINE,
    SOUND_PLAY,
    SOUND_PAUSE,
    SOUND_STOP,
    ;

    @Override
    public String toString() {
        return "BehaviorEnum{" +
            "name=" + name() +
            '}';
    }
}
