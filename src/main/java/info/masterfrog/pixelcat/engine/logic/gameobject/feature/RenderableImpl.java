package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.animation.AnimationSequence;
import info.masterfrog.pixelcat.engine.logic.camera.Camera;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObject;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

class RenderableImpl extends FeatureImpl implements Renderable {
    private Point position;
    private Integer layer;
    private Double scaleFactor;

    RenderableImpl(Point position, Integer layer) {
        this(position, layer, 1.0);
    }

    RenderableImpl(Point position, Integer layer, Double scaleFactor) {
        this.position = position;
        this.layer = layer;
        this.scaleFactor = scaleFactor;
    }

    public Resource getRenderableResource(GameObject gameObject) throws TransientGameException {
        // setup
        Resource resource;

        // determine use case
        if (gameObject.isFeatureAvailable(CameraLibrary.class)) {
            // setup
            String resourceId;

            // fetch camera library
            CameraLibrary cameraLibrary = gameObject.getFeature(CameraLibrary.class);

            // fetch current camera
            Camera camera = cameraLibrary.getCurrent();

            // fetch camera view
            if (camera.getType().equals(AnimationSequence.class) && gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
                // fetch animation sequence ID
                String animationSequenceId = camera.getView();

                // fetch resource ID from animation sequence
                resourceId = gameObject.getFeature(AnimationSequenceLibrary.class).get(animationSequenceId).getCurrentCel();
            } else if (gameObject.isFeatureAvailable(ResourceLibrary.class)) {
                // fetch resource ID
                resourceId = camera.getView();
            } else {
                throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
            }

            // fetch resource
            resource = gameObject.getFeature(ResourceLibrary.class).get(resourceId);
        } else if (gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
            // fetch animation sequence library
            AnimationSequenceLibrary animationSequenceLibrary = gameObject.getFeature(AnimationSequenceLibrary.class);

            // fetch animation sequence
            AnimationSequence animationSequence = animationSequenceLibrary.getCurrent();

            // fetch resource
            String resourceId = animationSequence.getCurrentCel();

            // fetch resource
            resource = gameObject.getFeature(ResourceLibrary.class).get(resourceId);
        } else if (gameObject.isFeatureAvailable(ResourceLibrary.class)) {
            // fetch resource library
            ResourceLibrary resourceLibrary = gameObject.getFeature(ResourceLibrary.class);

            // fetch resource
            resource = resourceLibrary.getCurrent();
        } else {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        return resource;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getLayer() {
        return layer;
    }

    public Double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(Double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public String toString() {
        return "RenderableImpl{" +
            "position=" + position +
            ", layer=" + layer +
            ", scaleFactor=" + scaleFactor +
            '}';
    }
}
