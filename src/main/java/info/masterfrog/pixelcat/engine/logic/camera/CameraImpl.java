package info.masterfrog.pixelcat.engine.logic.camera;

import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;
import info.masterfrog.pixelcat.engine.logic.common.RenderableContainer;

public class CameraImpl extends IdGeneratorImpl implements Camera {
    private String view;
    private Class<? extends RenderableContainer> type;

    CameraImpl(String view, Class<? extends RenderableContainer> type) throws TransientGameException {
        super(Camera.class.getSimpleName());
        this.view = view;
        this.type = generateType(type);
    }

    private Class<? extends RenderableContainer> generateType(Class<? extends RenderableContainer> type) throws TransientGameException {
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
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Renderable container type interface does not exist", type);
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