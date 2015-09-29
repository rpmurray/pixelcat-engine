package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

abstract class LibraryTemplateImpl<L extends IdGenerator> extends ContainerTemplateImpl<L> implements LibraryTemplate<L> {
    private String current;

    public void setCurrent(String id) {
        current = id;
    }

    public L getCurrent() throws GameException {
        if (!getAll().containsKey(current)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return getAll().get(current);
    }
}
