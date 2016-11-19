package info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior;

import java.util.List;

public class BehaviorParameterFactory {
    private static BehaviorParameterFactory instance;

    private BehaviorParameterFactory() {
        // do nothing
    }

    public static BehaviorParameterFactory getInstance() {
        if (instance == null) {
            instance = new BehaviorParameterFactory();
        }

        return instance;
    }

    public BehaviorParameterMagnitude createMagnitudeParameter(Double magnitude) {
        BehaviorParameterMagnitude parameter = new BehaviorParameterMagnitude(
            magnitude
        );

        return parameter;
    }

    public BehaviorParameterId createIdParameter(String cameraId) {
        BehaviorParameterId parameter = new BehaviorParameterId(
            cameraId
        );

        return parameter;
    }

    public BehaviorParameterLogicRoutine createLogicRoutineParameter(BehaviorParameterLogicRoutineDefinition logicRoutineDefinition,
                                                                     List<Object> inputs) {
        BehaviorParameterLogicRoutine parameter = new BehaviorParameterLogicRoutine(
            logicRoutineDefinition,
            inputs
        );

        return parameter;
    }

    public BehaviorParameter createBehaviorParameterGenerator(BehaviorParameterGeneratorDefinition generator, List<Object> inputs) {
        BehaviorParameterGenerator parameter = new BehaviorParameterGenerator(
            generator,
            inputs
        );

        return parameter;
    }
}
