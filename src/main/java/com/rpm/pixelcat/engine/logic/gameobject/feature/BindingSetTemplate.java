package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;

public interface BindingSetTemplate<B> extends ContainerTemplate<B> {
    <C extends B> void remove(Class<C> bindingClass) throws GameException;

    Boolean has(Class bindingClass);

    <C extends B> C get(Class<C> bindingClass) throws GameException;
}
