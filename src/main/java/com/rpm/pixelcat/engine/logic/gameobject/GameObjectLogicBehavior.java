package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

import java.util.Set;

public class GameObjectLogicBehavior {
    private GameObjectLogicBehaviorEnum behaviorType;
    private Set<GameObjectLogicBehaviorParameter> gameObjectLogicBehaviorParameters;

    public GameObjectLogicBehavior(GameObjectLogicBehaviorEnum behaviorType,
                                   Set<GameObjectLogicBehaviorParameter> gameObjectLogicBehaviorParameters) {
        this.behaviorType =behaviorType;
        this.gameObjectLogicBehaviorParameters = gameObjectLogicBehaviorParameters;
    }

    public GameObjectLogicBehaviorEnum getBehaviorType() {
        return behaviorType;
    }

    public Set<GameObjectLogicBehaviorParameter> getGameObjectLogicBehaviorParameters() {
        return gameObjectLogicBehaviorParameters;
    }

    public GameObjectLogicBehaviorParameter getGameObjectLogicParameterByClass(Class gameObjectLogicParameterClass)
           throws GameException {
        for (GameObjectLogicBehaviorParameter gameObjectLogicBehaviorParameter : gameObjectLogicBehaviorParameters) {
            if (gameObjectLogicBehaviorParameter.getClass().equals(gameObjectLogicParameterClass)) {
                return gameObjectLogicBehaviorParameter;
            }
        }

        throw new GameException(GameErrorCode.LOGIC_ERROR);
    }

    @Override
    public String toString() {
        return "GameObjectLogicBehavior{" +
            "behaviorType=" + behaviorType +
            ", gameObjectLogicParameters=" + gameObjectLogicBehaviorParameters +
            '}';
    }
}
