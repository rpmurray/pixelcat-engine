package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;

import java.util.HashMap;
import java.util.Map;

abstract class ContainerTemplateImpl<C extends IdGenerator> extends FeatureImpl implements ContainerTemplate<C> {
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
        if (map.containsKey(object.getId())) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        map.put(object.getId(), object);

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
