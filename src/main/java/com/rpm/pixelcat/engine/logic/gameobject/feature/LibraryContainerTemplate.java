package com.rpm.pixelcat.engine.logic.gameobject.feature;

import java.util.Map;

public interface LibraryContainerTemplate<L> extends Feature {
    public void setCurrent(String id);

    public L getCurrent();

    public L get(String id);

    public void add(String id, L object);

    public void remove(String id);

    public Map<String, L> getLibrary();
}
