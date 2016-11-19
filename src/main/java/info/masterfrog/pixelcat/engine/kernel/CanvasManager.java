package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;

import java.util.Map;

public interface CanvasManager {
    Boolean has(String id);

    Canvas get(String id) throws TransientGameException;

    CanvasManager add(Canvas object) throws TransientGameException;

    void update(Canvas object);

    void remove(String id) throws TransientGameException;

    Map<String, Canvas> getAll();

    CanvasManager setDefault(String id) throws TransientGameException;

    Canvas getDefault() throws TransientGameException;

    CanvasManager deactivate(String id) throws TransientGameException;

    CanvasManager activate(String id) throws TransientGameException;

    Boolean isActive(String id);

    void verifyIsActive(String id) throws TransientGameException;
}
