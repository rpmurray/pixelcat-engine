package com.rpm.pixelcat.engine.logic.camera;

import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

public class CameraImpl extends IdGeneratorImpl implements Camera {
    private String view;
    private Class<? extends RenderableContainer> type;

    CameraImpl(String view, Class<? extends RenderableContainer> type) {
        super(Camera.class.toString());
        this.view = view;
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public Class<? extends RenderableContainer> getType() {
        return type;
    }
}
