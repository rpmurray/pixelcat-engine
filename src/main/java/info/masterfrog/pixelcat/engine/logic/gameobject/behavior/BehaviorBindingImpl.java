package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.clock.GameClock;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockFactory;
import info.masterfrog.pixelcat.engine.logic.clock.SimpleGameClock;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;

public class BehaviorBindingImpl<E extends BindableEvent> extends IdGeneratorImpl implements IdGenerator, BehaviorBinding<E> {
    private Behavior behavior;
    private E boundEvent;
    private Long coolDownInMS;
    private SimpleGameClock clock;

    BehaviorBindingImpl(Behavior behavior, E boundEvent) {
        this(behavior, boundEvent, 0L);
    }

    BehaviorBindingImpl(Behavior behavior, E boundEvent, Long coolDownInMS) {
        super(BehaviorBindingImpl.class.getSimpleName());
        this.behavior = behavior;
        this.boundEvent = boundEvent;
        this.coolDownInMS = coolDownInMS;
        if (this.coolDownInMS > 0L) {
            this.clock = GameClockFactory.getInstance().createSimpleGameClock();
        } else {
            this.clock = null;
        }
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public E getBoundEvent() {
        return boundEvent;
    }

    public Long getCoolDownInMS() {
        return coolDownInMS;
    }

    public Boolean hasCoolDown() {
        return coolDownInMS > 0L;
    }

    public void recordTriggerEvent() {
        if (hasCoolDown()) {
            clock.reset();
        }
    }

    public Boolean canBehaviorBeTriggered() throws TransientGameException {
        if (hasCoolDown()) {
            return GameClock.toMS(clock.getElapsed()) > coolDownInMS;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BehaviorBindingImpl)) return false;

        BehaviorBindingImpl that = (BehaviorBindingImpl) o;

        if (!behavior.equals(that.behavior)) return false;
        if (boundEvent != that.boundEvent) return false;

        return true;
    }

    @Override
    public String toString() {
        return "BehaviorBindingImpl{" +
            "behavior=" + behavior +
            ", boundEvent=" + boundEvent +
            ", coolDownInMS=" + coolDownInMS +
            ", clock=" + clock +
            '}';
    }
}
