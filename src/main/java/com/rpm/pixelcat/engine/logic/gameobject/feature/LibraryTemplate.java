package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

public interface LibraryTemplate<L extends IdGenerator> extends ContainerTemplate<L> {
    LibraryTemplate<L> add(L object) throws TransientGameException;

    LibraryTemplate<L> setCurrent(String id);

    L getCurrent() throws TransientGameException;
}
