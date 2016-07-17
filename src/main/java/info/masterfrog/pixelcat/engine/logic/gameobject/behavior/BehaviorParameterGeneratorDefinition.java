package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.util.List;

public interface BehaviorParameterGeneratorDefinition {
    BehaviorParameter generateBehaviorParameter(List<Object> inputs) throws TransientGameException;
}
