package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;

public interface BindingSetTemplate<B extends IdGenerator> extends ContainerTemplate<B> {
    BindingSetTemplate<B> add(B object) throws TransientGameException;

    <C extends B> void remove(Class<C> bindingClass) throws TransientGameException;

    Boolean has(Class bindingClass);

    <C extends B> C get(Class<C> bindingClass) throws TransientGameException;
}
