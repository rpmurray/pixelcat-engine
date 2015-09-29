package com.rpm.pixelcat.engine.logic.common;

public abstract class IdGeneratorImpl {
    private String id;

    public IdGeneratorImpl() {
        this.id = IdGeneratorUtil.generateId("");
    }

    public IdGeneratorImpl(String base) {
        this.id = IdGeneratorUtil.generateId(base);
    }

    public String getId() {
        return id;
    }
}
