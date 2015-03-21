package com.rpm.pixelcat.engine.logic.gameobject;

class GameObjectLogicBehaviorParameterMagnitude implements GameObjectLogicBehaviorParameter {
    private Double magnitude;

    GameObjectLogicBehaviorParameterMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "GameObjectLogicParameterMagnitude{" +
            "magnitude=" + magnitude +
            '}';
    }
}
