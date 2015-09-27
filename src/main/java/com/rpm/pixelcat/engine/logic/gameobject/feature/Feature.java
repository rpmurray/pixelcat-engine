package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

public interface Feature {
    static <C extends Feature, I extends C> C create(Class<I> implClass) throws GameException{
        // setup
        C impl;

        // instantiate
        try {
            impl = implClass.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new GameException(GameErrorCode.LOGIC_ERROR, e);
        }

        return impl;
    }
}
