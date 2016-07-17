package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import java.util.List;

public class BehaviorParameterGenerator implements BehaviorParameter {
    private BehaviorParameterGeneratorDefinition generatorDefinition;
    private List<Object> inputs;

    BehaviorParameterGenerator(BehaviorParameterGeneratorDefinition generatorDefinition, List<Object> inputs) {
        this.generatorDefinition = generatorDefinition;
        this.inputs = inputs;
    }

    public BehaviorParameterGeneratorDefinition getGeneratorDefinition() {
        return generatorDefinition;
    }

    public List<Object> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return "BehaviorParameterGenerator{" +
            "generatorDefinition=" + generatorDefinition +
            ", inputs=" + inputs +
            '}';
    }
}
