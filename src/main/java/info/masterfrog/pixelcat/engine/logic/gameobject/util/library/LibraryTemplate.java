package info.masterfrog.pixelcat.engine.logic.gameobject.util.library;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.container.ContainerTemplate;

public interface LibraryTemplate<L extends Aspect> extends ContainerTemplate<L> {
    LibraryTemplate<L> add(L object) throws TransientGameException;

    LibraryTemplate<L> setCurrent(String id) throws TransientGameException;

    L getCurrent() throws TransientGameException;
}
