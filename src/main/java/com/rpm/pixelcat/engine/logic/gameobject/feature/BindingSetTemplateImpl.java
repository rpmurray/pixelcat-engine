package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

import java.util.Map;

abstract class BindingSetTemplateImpl<B extends IdGenerator> extends ContainerTemplateImpl<B> implements BindingSetTemplate<B> {
    @Override
    public BindingSetTemplate<B> add(B object) throws GameException {
        super.add(object);

        return this;
    }

    public <C extends B> void remove(Class<C> bindingClass) throws GameException {
        // find by class
        String id = find(bindingClass);

        // remove value
        getAll().remove(id);
    }

    public Boolean has(Class bindingClass) {
        try {
            // find by class
            find(bindingClass);

            return true;
        } catch (GameException e) {
            return false;
        }
    }

    public <C extends B> C get(Class<C> bindingClass) throws GameException {
        // find by class
        String id = find(bindingClass);

        // fetch value
        C value = (C) getAll().get(id);

        return value;
    }

    private <C extends B> String find(Class<C> bindingClass) throws GameException {
        // setup
        Map<String, B> bindingSet = getAll();

        // look through each item to find a match by class
        for (String key : bindingSet.keySet()) {
            // fetch value
            B value = bindingSet.get(key);

            // return first match by class
            if (value.getClass().equals(bindingClass)) {
                return key;
            }
        }

        // throw error as we found no match
        throw new GameException(GameErrorCode.LOGIC_ERROR);
    }
}
