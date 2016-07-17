package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;

public interface LibraryTemplate<L extends IdGenerator> extends ContainerTemplate<L> {
    LibraryTemplate<L> add(L object) throws TransientGameException;

    LibraryTemplate<L> setCurrent(String id);

    L getCurrent() throws TransientGameException;
}
