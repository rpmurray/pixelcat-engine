package com.rpm.pixelcat.engine.logic.camera;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

public class CameraImpl extends IdGeneratorImpl implements Camera {
    private String view;
    private Class<? extends RenderableContainer> type;

    CameraImpl(String view, Class<? extends RenderableContainer> type) throws GameException {
        super(Camera.class.getSimpleName());
        this.view = view;
        this.type = generateType(type);
    }

    private Class<? extends RenderableContainer> generateType(Class<? extends RenderableContainer> type) throws GameException {
        // setup
        Class<? extends RenderableContainer> typeIntf = null;

        // if an interface, use it, otherwise try to derive its interface
        if (type.isInterface()) {
            typeIntf = type;
        } else {
            // derive appropriate interface
            for (Class intf : type.getInterfaces()) {
                if (type.getSimpleName().equals(intf.getSimpleName() + "Impl") &&
                    intf.isInterface()) {
                    typeIntf = intf;
                }
            }
        }

        // if we didn't find a matching interface, throw an error
        if (typeIntf == null) {
            throw new GameException(GameErrorCode.LOGIC_ERROR, "Renderable container type interface does not exist", type);
        }

        return typeIntf;
    }

    public String getView() {
        return view;
    }

    public Class<? extends RenderableContainer> getType() {
        return type;
    }
}
