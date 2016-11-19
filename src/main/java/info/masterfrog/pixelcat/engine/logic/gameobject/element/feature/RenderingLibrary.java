package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering.Rendering;

import java.util.Map;

public interface RenderingLibrary extends Feature {
    Boolean has(String id);

    Rendering get(String id) throws TransientGameException;

    RenderingLibrary add(Rendering object) throws TransientGameException;

    void update(Rendering object);

    void remove(String id) throws TransientGameException;

    Map<String, Rendering> getAll();

    Rendering getCurrent(Canvas canvas) throws TransientGameException;

    static RenderingLibrary create() throws TransientGameException {
        RenderingLibraryImpl renderingLibrary = Feature.create(RenderingLibraryImpl.class);

        return renderingLibrary;
    }
}
