package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

import java.util.HashMap;
import java.util.Map;

abstract class ContainerTemplateImpl<C extends IdGenerator> extends FeatureImpl implements ContainerTemplate<C> {
    private Map<String, C> map;

    public ContainerTemplateImpl() {
        this.map = new HashMap<>();
    }

    public Boolean has(String id) {
        return map.containsKey(id);
    }

    public C get(String id) throws GameException {
        if (!map.containsKey(id)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        return map.get(id);
    }

    public ContainerTemplate<C> add(C object) throws GameException {
        if (map.containsKey(object.getId())) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        map.put(object.getId(), object);

        return this;
    }

    public void update(C object) {
        map.put(object.getId(), object);
    }

    public void remove(String id) throws GameException {
        if (!map.containsKey(id)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        map.remove(id);
    }

    public Map<String, C> getAll() {
        return map;
    }
}
