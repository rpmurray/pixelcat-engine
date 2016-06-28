package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.common.binding.BindableEvent;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.gameobject.behavior.BehaviorBindingImpl;

public interface BehaviorBindingLibrary<E extends BindableEvent> extends BindingSetTemplate<BehaviorBindingImpl<E>> {
    static BehaviorBindingLibrary create() throws TransientGameException {
        return Feature.create(BehaviorBindingLibraryImpl.class);
    }
}
