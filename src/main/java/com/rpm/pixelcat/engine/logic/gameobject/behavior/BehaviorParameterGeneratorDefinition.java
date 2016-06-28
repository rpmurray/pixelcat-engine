package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.util.List;

public interface BehaviorParameterGeneratorDefinition {
    BehaviorParameter generateBehaviorParameter(List<Object> inputs) throws TransientGameException;
}
