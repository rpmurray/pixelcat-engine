package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.util.List;

public interface BehaviorParameterLogicRoutineDefinition {
    void execute(List<Object> inputs) throws TransientGameException;
}
