package com.rpm.pixelcat.engine.constants;

public enum ResourceConstants {
    IMAGE_RESOURCE_PATH("images"),
    FONT_RESOURCE_PATH("fonts"),
    ;

    private final String value;

    ResourceConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ResourceConstants{" +
            "value='" + value + '\'' +
            '}';
    }
}
