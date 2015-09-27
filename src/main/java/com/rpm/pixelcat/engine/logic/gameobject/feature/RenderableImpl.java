package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.camera.Camera;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

public class RenderableImpl extends FeatureImpl implements Renderable {
    private Point position;
    private Integer layer;

    public Resource getRenderableResource(GameObject gameObject) throws GameException {
        // setup
        Resource resource;

        // determine use case
        if (gameObject.hasFeature(CameraLibrary.class)) {
            // fetch camera library
            CameraLibrary cameraLibrary = gameObject.getFeature(CameraLibrary.class);

            // fetch current camera
            Camera camera = cameraLibrary.getCurrent();

            // determine case
            if (true) {
                // fetch animation sequence
                //TODO

                // fetch resource
                // TODO
            } else {
                // fetch resource
                //TODO
            }
            //TODO: remove
            throw new GameException(GameErrorCode.UNSUPPORTED_FUNCTIONALITY);
        } else if (gameObject.hasFeature(AnimationSequenceLibrary.class)) {
            // fetch animation sequence library
            AnimationSequenceLibrary animationSequenceLibrary = gameObject.getFeature(AnimationSequenceLibrary.class);

            // fetch animation sequence
            AnimationSequence animationSequence = animationSequenceLibrary.getCurrent();

            // fetch resource
            resource = animationSequence.getCurrentCel();
        } else if (gameObject.hasFeature(ResourceLibrary.class)) {
            // fetch resource library
            ResourceLibrary resourceLibrary = gameObject.getFeature(ResourceLibrary.class);

            // fetch resource
            resource = resourceLibrary.getCurrent();
        } else {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
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
}
