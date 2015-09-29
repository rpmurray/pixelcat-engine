package com.rpm.pixelcat.engine.logic.camera;

import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

public class CameraImpl extends IdGeneratorImpl implements Camera {
    private String name;
    private String view;
    private Class<RenderableContainer> type;

    CameraImpl(String name, String view, Class<RenderableContainer> type) {
        super(Camera.class.toString());
        this.name = name;
        this.view = view;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getView() {
        return view;
    }

    public Class<RenderableContainer> getType() {
        return type;
    }
}
