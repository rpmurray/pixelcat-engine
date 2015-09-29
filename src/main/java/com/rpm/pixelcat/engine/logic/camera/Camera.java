package com.rpm.pixelcat.engine.logic.camera;

import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

public interface Camera extends IdGenerator {
    Class<RenderableContainer> getType();

    String getName();

    String getView();

    static Camera create(String name, String view, Class<RenderableContainer> type) {
        Camera camera = new CameraImpl(name, view, type);

        return camera;
    }
}
