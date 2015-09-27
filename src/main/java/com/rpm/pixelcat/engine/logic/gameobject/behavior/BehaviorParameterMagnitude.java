package com.rpm.pixelcat.engine.logic.gameobject.behavior;

public class BehaviorParameterMagnitude implements BehaviorParameter {
    private Double magnitude;

    BehaviorParameterMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "BehaviorParameterMagnitude{" +
            "magnitude=" + magnitude +
            '}';
    }
}
