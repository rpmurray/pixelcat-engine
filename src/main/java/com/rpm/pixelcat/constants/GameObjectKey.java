package com.rpm.pixelcat.constants;

public enum GameObjectKey {
    NUM_OBJS(3),
    CHARACTER(0),
    TITLE(1),
    SUBTITLE(2),

    LAYER1(0),
    LAYER2(1),
    ;

    private final Integer value;

    GameObjectKey(Integer value) {
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
