package info.masterfrog.pixelcat.engine.logic.gameobject.util.container;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.FeatureImpl;

import java.util.HashMap;
import java.util.Map;

public abstract class ContainerTemplateImpl<C extends Aspect> extends FeatureImpl implements ContainerTemplate<C> {
    private Map<String, C> map;

    public ContainerTemplateImpl() {
        this.map = new HashMap<>();
    }

    public Boolean has(String id) {
        return map.containsKey(id);
    }

    public C get(String id) throws TransientGameException {
        if (!map.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return map.get(id);
    }

    public ContainerTemplate<C> add(C object) throws TransientGameException {
        put(object.getId(), object);

        return this;
    }

    private ContainerTemplate<C> put(String id, C object) throws TransientGameException {
        if (map.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        map.put(id, object);

        return this;
    }

    public void update(C object) {
        map.put(object.getId(), object);
    }

    public void remove(String id) throws TransientGameException {
        if (!map.containsKey(id)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        map.remove(id);
    }

    public Map<String, C> getAll() {
        return map;
    }
}
