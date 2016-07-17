package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

public interface Feature {
    static <C extends Feature, I extends C> C create(Class<I> implClass) throws TransientGameException {
        // setup
        C impl;

        // instantiate
        try {
            impl = implClass.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, e);
        }

        return impl;
    }
}
