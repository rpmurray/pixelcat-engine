package com.rpm.pixelcat.engine.logic.physics.screen;

import com.rpm.pixelcat.engine.common.logic.IdGeneratorUtil;
import com.rpm.pixelcat.engine.logic.physics.PhysicsBinding;

public enum ScreenBoundsHandlingTypeEnum implements PhysicsBinding {
    // use outer edge of the resource, keeping the resource fully in bounds
    OUTER_RESOURCE_EDGE,
    // use inner edge of the resource, allowing the resource to be fully out of bounds, but still constrained
    INNER_RESOURCE_EDGE,
    // use the center of the resource, allowing the resource to be half out of bounds
    CENTER_RESOURCE,
    ;
    private String id;

    ScreenBoundsHandlingTypeEnum() {
        this.id = IdGeneratorUtil.generateId(ScreenBoundsHandlingTypeEnum.class.toString());
    }

    public String getId() {
        return id;
    }
}
