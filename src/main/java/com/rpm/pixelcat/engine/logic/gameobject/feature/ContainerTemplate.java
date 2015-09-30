package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

import java.util.Map;

public interface ContainerTemplate<C extends IdGenerator> extends Feature {
    Boolean has(String id);

    C get(String id) throws GameException;

    ContainerTemplate<C> add(C object) throws GameException;

    void update(C object);

    void remove(String id) throws GameException;

    Map<String, C> getAll();
}
