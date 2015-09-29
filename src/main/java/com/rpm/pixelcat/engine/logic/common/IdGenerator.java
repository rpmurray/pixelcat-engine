package com.rpm.pixelcat.engine.logic.common;

public interface IdGenerator {
    public String getId();

    static String generateId() {
        String id = IdGeneratorUtil.generateId("");

        return id;
    }

    static String generateId(String base) {
        String id = IdGeneratorUtil.generateId(base);

        return id;
    }
}
