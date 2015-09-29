package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;

public interface LibraryTemplate<L> extends ContainerTemplate<L> {
    void setCurrent(String id);

    L getCurrent() throws GameException;
}
