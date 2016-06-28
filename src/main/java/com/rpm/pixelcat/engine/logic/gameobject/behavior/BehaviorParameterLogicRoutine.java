package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import java.util.List;

public class BehaviorParameterLogicRoutine implements BehaviorParameter {
    private BehaviorParameterLogicRoutineDefinition logicRoutineDefinition;
    private List<Object> inputs;

    BehaviorParameterLogicRoutine(BehaviorParameterLogicRoutineDefinition logicRoutineDefinition, List<Object> inputs) {
        this.logicRoutineDefinition = logicRoutineDefinition;
        this.inputs = inputs;
    }

    public BehaviorParameterLogicRoutineDefinition getLogicRoutineDefinition() {
        return logicRoutineDefinition;
    }

    public List<Object> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return "BehaviorParameterLogicRoutine{" +
            "logicRoutineDefinition=" + logicRoutineDefinition +
            ", inputs=" + inputs +
            '}';
    }
}
