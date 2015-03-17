package com.rpm.pixelcat.logic.gameobject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.constants.GameObjectKey;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.hid.HIDEventEnum;
import com.rpm.pixelcat.kernel.KernelState;
import com.rpm.pixelcat.logic.GameObjectUpdater;
import com.rpm.pixelcat.logic.animation.AnimationFactory;
import com.rpm.pixelcat.logic.animation.AnimationSequence;
import com.rpm.pixelcat.logic.resource.Resource;
import com.rpm.pixelcat.logic.resource.ResourceFactory;
import com.rpm.pixelcat.logic.resource.SpriteSheet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        // setup
        ResourceFactory resourceFactory = ResourceFactory.getInstance();
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();
        AnimationFactory animationFactory = AnimationFactory.getInstance();

        // game objects creation
        SpriteSheet spriteSheet = resourceFactory.createSpriteSheet("nyancat_sprite_sheet.png", 60, 30);
        gameObjects.add(
            gameObjectFactory.createGameObject(
                50, 50,
                GameObjectKey.LAYER1.getValue(),
                ImmutableSet.of(
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.UP,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.MOVE_UP, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.UP,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.MOVE_UP, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.DOWN,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.MOVE_DOWN, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.MOVE_LEFT, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.MOVE_RIGHT, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.UP,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.ANIMATION_PLAY, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.DOWN,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.ANIMATION_PLAY, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.ANIMATION_PLAY, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.ANIMATION_PLAY, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.NO_DIRECTION,
                        new GameObjectLogicBehavior(GameObjectLogicBehaviorEnum.ANIMATION_STOP, ImmutableSet.<GameObjectLogicParameter>of())
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(new GameObjectLogicParameterOrientation(OrientationEnum.RIGHT))
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(new GameObjectLogicParameterOrientation(OrientationEnum.LEFT))
                        )
                    )
                ),
                ImmutableMap.of(
                    OrientationEnum.RIGHT,
                    animationFactory.createAnimationSequence(
                        ImmutableList.of(
                            resourceFactory.createImageResource(0, 0, spriteSheet),
                            resourceFactory.createImageResource(1, 0, spriteSheet),
                            resourceFactory.createImageResource(2, 0, spriteSheet),
                            resourceFactory.createImageResource(3, 0, spriteSheet),
                            resourceFactory.createImageResource(4, 0, spriteSheet),
                            resourceFactory.createImageResource(5, 0, spriteSheet)
                        ),
                        100L
                    ),
                    OrientationEnum.LEFT,
                    animationFactory.createAnimationSequence(
                        ImmutableList.of(
                            resourceFactory.createImageResource(0, 1, spriteSheet),
                            resourceFactory.createImageResource(1, 1, spriteSheet),
                            resourceFactory.createImageResource(2, 1, spriteSheet),
                            resourceFactory.createImageResource(3, 1, spriteSheet),
                            resourceFactory.createImageResource(4, 1, spriteSheet),
                            resourceFactory.createImageResource(5, 1, spriteSheet)
                        ),
                        100L
                    )
                ),
                OrientationEnum.RIGHT,
                resourceFactory.createImageResource(1, 0, spriteSheet)
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
            GameObjectUpdater.getInstace().update(gameObject, kernelState);
        }
    }
}
