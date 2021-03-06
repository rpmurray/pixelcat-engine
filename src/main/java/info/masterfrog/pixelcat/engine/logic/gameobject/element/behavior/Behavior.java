package info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior;

import com.google.common.collect.ImmutableSet;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.util.Set;

public class Behavior {
    private BehaviorEnum behaviorType;
    private Set<BehaviorParameter> behaviorParameters;

    public Behavior(BehaviorEnum behaviorType) {
        this(behaviorType, ImmutableSet.of());
    }

    public Behavior(BehaviorEnum behaviorType,
                    Set<BehaviorParameter> behaviorParameters) {
        this.behaviorType = behaviorType;
        this.behaviorParameters = behaviorParameters;
    }

    public BehaviorEnum getBehaviorType() {
        return behaviorType;
    }

    public Set<BehaviorParameter> getBehaviorParameters() {
        return behaviorParameters;
    }

    public Boolean hasBehaviorParameter(Class behaviorParameterClass) {
        for (BehaviorParameter behaviorParameter : behaviorParameters) {
            if (behaviorParameter.getClass().equals(behaviorParameterClass)) {
                return true;
            }
        }

        return false;
    }

    public <B extends BehaviorParameter> B getBehaviorParameter(Class<B> behaviorParameterClass)
           throws TransientGameException {
        for (BehaviorParameter behaviorParameter : behaviorParameters) {
            if (behaviorParameter.getClass().equals(behaviorParameterClass)) {
                return (B) behaviorParameter;
            }
        }

        throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
    }

    @Override
    public String toString() {
        return "Behavior{" +
            "behaviorType=" + behaviorType +
            ", behaviorParameters=" + behaviorParameters +
            '}';
    }
}
