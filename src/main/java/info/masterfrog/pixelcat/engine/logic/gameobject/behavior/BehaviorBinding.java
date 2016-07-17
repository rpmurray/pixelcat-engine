package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;

public interface BehaviorBinding<E extends BindableEvent> {
    Behavior getBehavior();

    E getBoundEvent();
}
