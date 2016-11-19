package info.masterfrog.pixelcat.engine.logic.gameobject.util.library;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.container.ContainerTemplateImpl;

public abstract class LibraryTemplateImpl<L extends Aspect> extends ContainerTemplateImpl<L> implements LibraryTemplate<L> {
    private String current = null;

    public LibraryTemplateImpl() {
        super();
    }

    @Override
    public LibraryTemplate<L> add(L object) throws TransientGameException {
        super.add(object);

        if (current == null) {
            current = object.getId();
        }

        return this;
    }

    public LibraryTemplate<L> setCurrent(String id) throws TransientGameException {
        if (!has(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        current = id;

        return this;
    }

    public L getCurrent() throws TransientGameException {
        if (current == null || !getAll().containsKey(current)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, this);
        }

        return getAll().get(current);
    }
}
