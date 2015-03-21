package com.rpm.pixelcat.engine.logic.gameobject;

public enum ScreenBoundsHandlingTypeEnum {
    // no handling, the resource can be positioned anywhere
    NONE,
    // use outer edge of the resource, keeping the resource fully in bounds
    OUTER_RESOURCE_EDGE,
    // use inner edge of the resource, allowing the resource to be fully out of bounds, but still constrained
    INNER_RESOURCE_EDGE,
    // use the center of the resource, allowing the resource to be half out of bounds
    CENTER_RESOURCE,
    ;
}
