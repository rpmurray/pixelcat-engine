package com.rpm.pixelcat.engine.logic.gameobject.behavior;

import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;

public class HIDBehaviorBinding extends IdGeneratorImpl implements IdGenerator {
    private HIDEventEnum hidEventEnum;
    private Behavior behavior;

    public HIDBehaviorBinding(HIDEventEnum hidEventEnum, Behavior behavior) {
        super(HIDBehaviorBinding.class.toString());
        this.hidEventEnum = hidEventEnum;
        this.behavior = behavior;
    }

    public HIDEventEnum getHidEventEnum() {
        return hidEventEnum;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HIDBehaviorBinding)) return false;

        HIDBehaviorBinding that = (HIDBehaviorBinding) o;

        if (!behavior.equals(that.behavior)) return false;
        if (hidEventEnum != that.hidEventEnum) return false;

        return true;
    }

    @Override
    public String toString() {
        return "HIDBehaviorBinding{" +
            "hidEventEnum=" + hidEventEnum +
            ", behavior=" + behavior +
            '}';
    }
}
