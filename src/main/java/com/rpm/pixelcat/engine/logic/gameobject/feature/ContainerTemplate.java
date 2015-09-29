package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

import java.util.Map;

public interface ContainerTemplate<C extends IdGenerator> extends Feature {
    public Boolean has(String id);

    public C get(String id) throws GameException;

    public ContainerTemplate<C> add(C object) throws GameException;

    public void update(C object);

    public void remove(String id) throws GameException;

    public Map<String, C> getAll();
}
