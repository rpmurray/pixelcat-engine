package info.masterfrog.pixelcat.engine.logic.gameobject.behavior;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.hid.HIDEventEnum;
import info.masterfrog.pixelcat.engine.hid.HIDEventStateEnum;

public interface HIDBehaviorBindingEvent extends BindableEvent {
    HIDEventEnum getHIDEvent();

    HIDEventStateEnum getHIDEventState();
}
