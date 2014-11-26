package com.rpm.pixelcat.constants;

public enum ResourceConstants {
    BASE_PATH("Z:\\Google Drive\\Code\\Dev\\PixelCat\\"),
    RESOURCE_PATH("assets\\"),
    IMAGE_RESOURCE_PATH("images"),
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
