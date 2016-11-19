package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.kernel.CanvasManager;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering.Rendering;

import java.util.HashMap;
import java.util.Map;

class RenderingLibraryImpl extends FeatureImpl implements RenderingLibrary {
    private Map<String, Rendering> map;

    public RenderingLibraryImpl() {
        this.map = new HashMap<>();
    }

    public Boolean has(String id) {
        return map.containsKey(id);
    }

    public Rendering get(String id) throws TransientGameException {
        if (!map.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return map.get(id);
    }

    public RenderingLibrary add(Rendering object) throws TransientGameException {
        if (map.containsKey(object.getId())) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        map.put(object.getId(), object);

        return this;
    }

    public void update(Rendering object) {
        map.put(object.getId(), object);
    }

    public void remove(String id) throws TransientGameException {
        if (!map.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        map.remove(id);
    }

    public Map<String, Rendering> getAll() {
        return map;
    }

    public Rendering getCurrent(Canvas canvas) throws TransientGameException {
        // setup
        Rendering rendering = null;

        // find the applicable rendering
        for (String id : getAll().keySet()) {
            if (get(id).getCanvas().equals(canvas)) {
                rendering = get(id);
                break;
            }
        }

        if (rendering == null) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return rendering;
    }
}
