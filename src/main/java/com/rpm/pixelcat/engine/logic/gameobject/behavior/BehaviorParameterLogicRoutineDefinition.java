package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.util.List;

public interface BehaviorParameterLogicRoutineDefinition {
    void execute(List<Object> inputs) throws TransientGameException;
}
