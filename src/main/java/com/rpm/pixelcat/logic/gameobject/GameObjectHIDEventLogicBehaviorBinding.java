package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.hid.HIDEventEnum;

public class GameObjectHIDEventLogicBehaviorBinding {
    private HIDEventEnum hidEventEnum;
    private GameObjectLogicBehavior gameObjectLogicBehavior;

    public GameObjectHIDEventLogicBehaviorBinding(HIDEventEnum hidEventEnum, GameObjectLogicBehavior gameObjectLogicBehavior) {
        this.hidEventEnum = hidEventEnum;
        this.gameObjectLogicBehavior = gameObjectLogicBehavior;
    }

    public HIDEventEnum getHidEventEnum() {
        return hidEventEnum;
    }

    public GameObjectLogicBehavior getGameObjectLogicBehavior() {
        return gameObjectLogicBehavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameObjectHIDEventLogicBehaviorBinding)) return false;

        GameObjectHIDEventLogicBehaviorBinding that = (GameObjectHIDEventLogicBehaviorBinding) o;

        if (!gameObjectLogicBehavior.equals(that.gameObjectLogicBehavior)) return false;
        if (hidEventEnum != that.hidEventEnum) return false;

        return true;
    }

    @Override
    public String toString() {
        return "GameObjectHIDEventLogicBehaviorBinding{" +
            "hidEventEnum=" + hidEventEnum +
            ", gameObjectLogicBehavior=" + gameObjectLogicBehavior +
            '}';
    }
}
