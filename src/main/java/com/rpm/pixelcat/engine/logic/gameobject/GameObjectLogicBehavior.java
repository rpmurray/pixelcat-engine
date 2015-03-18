package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

import java.util.Set;

public class GameObjectLogicBehavior {
    private GameObjectLogicBehaviorEnum behaviorType;
    private Set<GameObjectLogicParameter> gameObjectLogicParameters;

    public GameObjectLogicBehavior(GameObjectLogicBehaviorEnum behaviorType,
                                   Set<GameObjectLogicParameter> gameObjectLogicParameters) {
        this.behaviorType =behaviorType;
        this.gameObjectLogicParameters = gameObjectLogicParameters;
    }

    public GameObjectLogicBehaviorEnum getBehaviorType() {
        return behaviorType;
    }

    public Set<GameObjectLogicParameter> getGameObjectLogicParameters() {
        return gameObjectLogicParameters;
    }

    public GameObjectLogicParameter getGameObjectLogicParameterByClass(Class gameObjectLogicParameterClass)
           throws GameException {
        for (GameObjectLogicParameter gameObjectLogicParameter: gameObjectLogicParameters) {
            if (gameObjectLogicParameter.getClass().equals(gameObjectLogicParameterClass)) {
                return gameObjectLogicParameter;
            }
        }

        throw new GameException(GameErrorCode.LOGIC_ERROR);
    }

    @Override
    public String toString() {
        return "GameObjectLogicBehavior{" +
            "behaviorType=" + behaviorType +
            ", gameObjectLogicParameters=" + gameObjectLogicParameters +
            '}';
    }
}
