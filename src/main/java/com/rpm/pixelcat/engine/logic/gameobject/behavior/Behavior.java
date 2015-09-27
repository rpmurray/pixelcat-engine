package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

import java.util.Set;

public class Behavior {
    private BehaviorEnum behaviorType;
    private Set<BehaviorParameter> behaviorParameters;

    public Behavior(BehaviorEnum behaviorType,
                    Set<BehaviorParameter> behaviorParameters) {
        this.behaviorType =behaviorType;
        this.behaviorParameters = behaviorParameters;
    }

    public BehaviorEnum getBehaviorType() {
        return behaviorType;
    }

    public Set<BehaviorParameter> getBehaviorParameters() {
        return behaviorParameters;
    }

    public BehaviorParameter getBehaviorParameter(Class behaviorParameterClass)
           throws GameException {
        for (BehaviorParameter behaviorParameter : behaviorParameters) {
            if (behaviorParameter.getClass().equals(behaviorParameterClass)) {
                return behaviorParameter;
            }
        }

        throw new GameException(GameErrorCode.LOGIC_ERROR);
    }

    @Override
    public String toString() {
        return "GameObjectLogicBehavior{" +
            "behaviorType=" + behaviorType +
            ", gameObjectLogicParameters=" + behaviorParameters +
            '}';
    }
}
