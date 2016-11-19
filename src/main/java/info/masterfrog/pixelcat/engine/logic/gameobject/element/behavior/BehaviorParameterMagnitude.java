package info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior;

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
