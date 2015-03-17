package com.rpm.pixelcat.logic.gameobject;

public class GameObjectLogicParameterOrientation implements GameObjectLogicParameter {
    private OrientationEnum orientation;

    public GameObjectLogicParameterOrientation(OrientationEnum orientation) {
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
