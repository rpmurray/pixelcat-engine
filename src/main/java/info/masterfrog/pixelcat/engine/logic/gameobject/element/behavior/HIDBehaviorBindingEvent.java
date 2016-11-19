package info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;
import info.masterfrog.pixelcat.engine.hid.HIDEventStateEnum;

public interface HIDBehaviorBindingEvent extends BindableEvent {
    HIDEvent getHIDEvent();

    HIDEventStateEnum getHIDEventState();
}
