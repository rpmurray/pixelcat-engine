package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

abstract class LibraryTemplateImpl<L extends IdGenerator> extends ContainerTemplateImpl<L> implements LibraryTemplate<L> {
    private String current = null;

    @Override
    public LibraryTemplate<L> add(L object) throws TransientGameException {
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

    public L getCurrent() throws TransientGameException {
        if (current == null || !getAll().containsKey(current)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, this);
        }

        return getAll().get(current);
    }
}
