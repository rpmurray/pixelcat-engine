package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import com.rpm.pixelcat.engine.common.binding.BindableEvent;

public interface BehaviorBinding<E extends BindableEvent> {
    Behavior getBehavior();

    E getBoundEvent();
}
