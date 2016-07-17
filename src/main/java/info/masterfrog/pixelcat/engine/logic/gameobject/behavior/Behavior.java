package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import com.google.common.collect.ImmutableSet;
import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
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

    public BehaviorParameter getBehaviorParameter(Class<? extends BehaviorParameter> behaviorParameterClass)
           throws TransientGameException {
        for (BehaviorParameter behaviorParameter : behaviorParameters) {
            if (behaviorParameter.getClass().equals(behaviorParameterClass)) {
                return behaviorParameter;
            }
        }

        throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
    }

    @Override
    public String toString() {
        return "GameObjectLogicBehavior{" +
            "behaviorType=" + behaviorType +
            ", gameObjectLogicParameters=" + behaviorParameters +
            '}';
    }
}
