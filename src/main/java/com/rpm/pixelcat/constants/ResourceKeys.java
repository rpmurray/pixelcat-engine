package com.rpm.pixelcat.constants;

public enum ResourceKeys {
    NUM_OBJS(3),
    CHARACTER(0),
    TITLE(1),
    SUBTITLE(2),
    ;

    private final Integer value;

    ResourceKeys(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GameObjectQuantities{" +
            "value='" + value + '\'' +
            '}';
    }
}
