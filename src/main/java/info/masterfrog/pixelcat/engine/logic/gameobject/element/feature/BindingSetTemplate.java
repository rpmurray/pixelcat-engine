package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.container.ContainerTemplate;

public interface BindingSetTemplate<B extends Aspect> extends ContainerTemplate<B> {
    BindingSetTemplate<B> add(B object) throws TransientGameException;

    <C extends B> void remove(Class<C> bindingClass) throws TransientGameException;

    Boolean has(Class bindingClass);

    <C extends B> C get(Class<C> bindingClass) throws TransientGameException;
}
