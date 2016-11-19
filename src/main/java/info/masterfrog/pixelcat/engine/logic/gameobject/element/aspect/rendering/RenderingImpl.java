package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.AspectImpl;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.animation.AnimationSequence;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.camera.Camera;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.AnimationSequenceLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.CameraLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.ResourceLibrary;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;

import java.awt.Point;

class RenderingImpl extends AspectImpl implements Rendering {
    private Point position;
    private Integer layer;
    private Double scaleFactor;
    private Canvas canvas;

    RenderingImpl(Canvas canvas, Point position, Integer layer) {
        this(canvas, position, layer, 1.0);
    }

    RenderingImpl(Canvas canvas, Point position, Integer layer, Double scaleFactor) {
        this.canvas = canvas;
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
                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
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
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        return resource;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvasNormalizedPosition(Point position) {
        this.position.x = (int) (position.x / canvas.getScaleFactor());
        this.position.y = (int) (position.y / canvas.getScaleFactor());
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getCanvasNormalizedPosition() {
        return new Point(
            (int) (this.position.x * canvas.getScaleFactor()),
            (int) (this.position.y * canvas.getScaleFactor())
        );
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
        return "RenderingImpl{" +
            "position=" + position +
            ", layer=" + layer +
            ", scaleFactor=" + scaleFactor +
            ", canvas='" + canvas + '\'' +
            '}';
    }
}
