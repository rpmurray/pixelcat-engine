package info.masterfrog.pixelcat.engine.logic.gameobject.util.container;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.Feature;

import java.util.Map;

public interface ContainerTemplate<C extends Aspect> extends Feature {
    Boolean has(String id);

    C get(String id) throws TransientGameException;

    ContainerTemplate<C> add(C object) throws TransientGameException;

    void update(C object);

    void remove(String id) throws TransientGameException;

    Map<String, C> getAll();
}
