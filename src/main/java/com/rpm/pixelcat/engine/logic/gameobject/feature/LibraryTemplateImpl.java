package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

abstract class LibraryTemplateImpl<L extends IdGenerator> extends ContainerTemplateImpl<L> implements LibraryTemplate<L> {
    private String current = null;

    @Override
    public LibraryTemplate<L> add(L object) throws GameException {
        super.add(object);

        if (current == null) {
            current = object.getId();
        }

        return this;
    }

    public LibraryTemplate<L> setCurrent(String id) {
        current = id;

        return this;
    }

    public L getCurrent() throws GameException {
        if (current == null || !getAll().containsKey(current)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return getAll().get(current);
    }
}
