package info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;

public interface BehaviorBinding<E extends BindableEvent> extends Aspect {
    Behavior getBehavior();

    E getBoundEvent();

    static <E extends BindableEvent> BehaviorBinding create(Behavior behavior, E boundEvent) {
        BehaviorBinding behaviorBinding = new BehaviorBindingImpl(behavior, boundEvent);

        return behaviorBinding;
    }

    static <E extends BindableEvent> BehaviorBinding create(Behavior behavior, E boundEvent, Long coolDownInMS) {
        BehaviorBinding behaviorBinding = new BehaviorBindingImpl(behavior, boundEvent, coolDownInMS);

        return behaviorBinding;
    }
}
