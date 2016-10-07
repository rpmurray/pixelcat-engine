package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.behavior.BehaviorBindingImpl;

public interface BehaviorBindingLibrary<E extends BindableEvent> extends BindingSetTemplate<BehaviorBindingImpl<E>> {
    static BehaviorBindingLibrary create() throws TransientGameException {
        return Feature.create(BehaviorBindingLibraryImpl.class);
    }
}
