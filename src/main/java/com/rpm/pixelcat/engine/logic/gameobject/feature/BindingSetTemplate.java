package com.rpm.pixelcat.engine.logic.gameobject.feature;

import java.util.Set;

public interface BindingSetTemplate<B> extends Feature {
    public void add(String id, B binding);

    public void remove(String id);

    public <C extends B> void remove(Class<C> bindingClass);

    public Boolean has(String id);

    public Boolean has(Class bindingClass);

    public B get(String id);

    public <C extends B> C get(Class<C> bindingClass);

    public Set<B> getAll();
}
