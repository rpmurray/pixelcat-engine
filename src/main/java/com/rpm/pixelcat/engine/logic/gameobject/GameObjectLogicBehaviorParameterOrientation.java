package com.rpm.pixelcat.engine.logic.gameobject;

class GameObjectLogicBehaviorParameterOrientation implements GameObjectLogicBehaviorParameter {
    private OrientationEnum orientation;

    GameObjectLogicBehaviorParameterOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "GameObjectLogicParameterOrientation{" +
            "orientation=" + orientation +
            '}';
    }
}
