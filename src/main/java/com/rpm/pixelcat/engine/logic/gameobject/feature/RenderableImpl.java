package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.camera.Camera;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

class RenderableImpl extends FeatureImpl implements Renderable {
    private Point position;
    private Integer layer;

    public RenderableImpl(Point position, Integer layer) {
        this.position = position;
        this.layer = layer;
    }

    public Resource getRenderableResource(GameObject gameObject) throws GameException {
        // setup
        Resource resource;

        // determine use case
        if (gameObject.isFeatureActive(CameraLibrary.class)) {
            // setup
            String resourceId;

            // fetch camera library
            CameraLibrary cameraLibrary = gameObject.getFeature(CameraLibrary.class);

            // fetch current camera
            Camera camera = cameraLibrary.getCurrent();

            // fetch camera view
            if (camera.getType().equals(AnimationSequence.class) && gameObject.isFeatureActive(AnimationSequenceLibrary.class)) {
                // fetch animation sequence ID
                String animationSequenceId = camera.getView();

                // fetch resource ID from animation sequence
                resourceId = gameObject.getFeature(AnimationSequenceLibrary.class).get(animationSequenceId).getCurrentCel();
            } else if (gameObject.isFeatureActive(ResourceLibrary.class)){
                // fetch resource ID
                resourceId = camera.getView();
            } else {
                throw new GameException(GameErrorCode.LOGIC_ERROR);
            }

            // fetch resource
            resource = gameObject.getFeature(ResourceLibrary.class).get(resourceId);
        } else if (gameObject.isFeatureActive(AnimationSequenceLibrary.class)) {
            // fetch animation sequence library
            AnimationSequenceLibrary animationSequenceLibrary = gameObject.getFeature(AnimationSequenceLibrary.class);

            // fetch animation sequence
            AnimationSequence animationSequence = animationSequenceLibrary.getCurrent();

            // fetch resource
            String resourceId = animationSequence.getCurrentCel();

            // fetch resource
            resource = gameObject.getFeature(ResourceLibrary.class).get(resourceId);
        } else if (gameObject.isFeatureActive(ResourceLibrary.class)) {
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
