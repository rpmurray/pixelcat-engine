package com.rpm.pixelcat.logic.gameobject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.constants.GameObjectKey;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.logic.GameObjectMover;
import com.rpm.pixelcat.logic.animation.AnimationFactory;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.resource.Resource;
import com.rpm.pixelcat.logic.resource.ResourceFactory;
import com.rpm.pixelcat.logic.resource.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class GameObjectManagerImpl implements GameObjectManager {
    private List<GameObject> gameObjects;

    public GameObjectManagerImpl(KernelState kernelState) {
        // setup
        LayerManager layerManager = LayerManager.getInstance();

        // define layers
        layerManager.addLayers(2);

        // init game objects
        gameObjects = new ArrayList<>();

        // register game objects
        registerGameObjects(kernelState);
    }

    private void registerGameObjects(KernelState kernelState) {
        // character
        SpriteSheet spriteSheet = ResourceFactory.getInstance().createSpriteSheet("nyancat_sprite_sheet.png", 60, 30);
        Resource initialResource = ResourceFactory.getInstance().createImageResource(0, 0, spriteSheet);
        gameObjects.add(
            GameObjectFactory.getInstance().createGameObject(
                50, 50,
                GameObjectKey.LAYER1.getValue(),
                ImmutableSet.of(
                    HIDEventEnum.UP,
                    HIDEventEnum.DOWN,
                    HIDEventEnum.LEFT,
                    HIDEventEnum.RIGHT
                ),
                ImmutableMap.of(
                    OrientationEnum.FRONT,
                    AnimationFactory.getInstance().createAnimationSequence(
                        ImmutableList.of(
                            initialResource,
                            ResourceFactory.getInstance().createImageResource(1, 0, spriteSheet),
                            ResourceFactory.getInstance().createImageResource(2, 0, spriteSheet),
                            ResourceFactory.getInstance().createImageResource(3, 0, spriteSheet),
                            ResourceFactory.getInstance().createImageResource(4, 0, spriteSheet),
                            ResourceFactory.getInstance().createImageResource(5, 0, spriteSheet)
                        ),
                        100L
                    )
                ),
                OrientationEnum.FRONT,
                initialResource
            )
        );

        // title
        //registerGameObject(new Title(kernelState.getBounds()), GameObjectKey.LAYER2, GameObjectKey.TITLE);
        //Title(Rectangle bounds) {
        //    super((bounds.width - 200) / 2, (bounds.height - 100) / 2 - 100);
        //    resource = ResourceFactory.getInstance().createImageResource(0, 0, "pixelcat.png", 200, 100);
        //    setCurrentResource(resource);
        //}

        // subtitle
        //registerGameObject(new Subtitle(kernelState.getBounds()), GameObjectKey.LAYER2, GameObjectKey.SUBTITLE);
        //Subtitle(Rectangle bounds) {
        //    super(bounds.width / 2 - 200, bounds.height / 2 - 30);
        //    resource = ResourceFactory.getInstance().createTextResource(
        //        "The 2D Sprite Base Video Game Engine", new Font("Courier New", Font.BOLD, 20)
        //    );
        //    setCurrentResource(resource);
        //}
    }

    public int getCount() {
        return gameObjects.size();
    }

    public ArrayList<ArrayList<GameObject>> getLayeredGameObjects() {
        // setup
        ArrayList<ArrayList<GameObject>> layeredGameObjects = new ArrayList<>();
        for (Integer i = 0; i < LayerManager.getInstance().getLayerCount(); i++) {
            layeredGameObjects.add(i, new ArrayList<>());
        }

        // build layered game objects
        for (GameObject gameObject: gameObjects) {
            layeredGameObjects.get(gameObject.getLayer()).add(gameObject);
        }

        return layeredGameObjects;
    }

    public void process(KernelState kernelState) throws GameException {
        for (GameObject gameObject : gameObjects) {
            updateAnimation(gameObject, kernelState);
            moveObject(gameObject, kernelState);
        }
    }

    private void updateAnimation(GameObject gameObject, KernelState kernelState) throws GameException {
        // setup
        AnimationSequence animationSequence = gameObject.getCurrentAnimationSequence();

        if (kernelState.hasHIDEvent(HIDEventEnum.RIGHT)) {
            animationSequence.play();
        } else {
            animationSequence.pause();
        }

        // step the animation forward as needed
        animationSequence.advanceSequenceByTime(kernelState.getClockTime());

        // record new current resource
        gameObject.setCurrentResource(animationSequence.getCurrentCel());
    }

    private void moveObject(GameObject gameObject, KernelState kernelState) {
        GameObjectMover.getInstace().move(gameObject, kernelState, gameObject.getBoundHIDEvents());
    }
}
