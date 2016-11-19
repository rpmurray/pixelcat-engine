package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;

import java.util.HashMap;
import java.util.Map;

class CanvasManagerImpl implements CanvasManager {
    private Map<String, Canvas> canvases;
    private Map<String, Boolean> canvasStatus;
    private String defaultCanvas = null;

    CanvasManagerImpl() {
        this.canvases = new HashMap<>();
        this.canvasStatus = new HashMap<>();
    }

    public Boolean has(String id) {
        return canvases.containsKey(id);
    }

    public Canvas get(String id) throws TransientGameException {
        if (!canvases.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return canvases.get(id);
    }

    public CanvasManager add(Canvas object) throws TransientGameException {
        if (canvases.containsKey(object.getId())) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        canvases.put(object.getId(), object);
        canvasStatus.put(object.getId(), true);

        if (defaultCanvas == null) {
            defaultCanvas = object.getId();
        }

        return this;
    }

    public void update(Canvas object) {
        canvases.put(object.getId(), object);
    }

    public void remove(String id) throws TransientGameException {
        if (!canvases.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        canvases.remove(id);
        canvasStatus.remove(id);
    }

    public Map<String, Canvas> getAll() {
        return canvases;
    }

    public CanvasManager setDefault(String id) throws TransientGameException {
        if (!canvases.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        defaultCanvas = id;

        return this;
    }

    public Canvas getDefault() throws TransientGameException {
        if (defaultCanvas == null || !canvases.containsKey(defaultCanvas)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, this);
        }

        return canvases.get(defaultCanvas);
    }

    public CanvasManager deactivate(String id) throws TransientGameException {
        // validate
        if (!has(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        // deactivate
        canvasStatus.put(id, false);

        return this;
    }

    public CanvasManager activate(String id) throws TransientGameException {
        // validate
        if (!has(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        // activate
        canvasStatus.put(id, true);

        return this;
    }

    public Boolean isActive(String id) {
        // check existence
        if (!has(id)) {
            return false;
        }

        // fetch status
        Boolean status = canvasStatus.get(id);

        return status;
    }

    public void verifyIsActive(String id) throws TransientGameException {
        // check existence
        if (!has(id)) {
            return;
        }

        // validate
        if (!canvasStatus.get(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, "canvas " + id + " is not active", this);
        }
    }
}
