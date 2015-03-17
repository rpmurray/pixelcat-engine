package com.rpm.pixelcat.logic.gameobject;

public enum GameObjectLogicBehaviorEnum {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    ANIMATION_PLAY,
    ANIMATION_STOP,
    ANIMATION_SEQUENCE_SWITCH,
    ;

    @Override
    public String toString() {
        return "GameObjectLogicBehaviorEnum{" +
            "name=" + name() +
            '}';
    }
}
