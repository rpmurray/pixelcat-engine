package com.rpm.pixelcat.engine.logic.gameobject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.engine.logic.animation.AnimationFactory;
import com.rpm.pixelcat.engine.logic.resource.ResourceFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.resource.Resource;
import com.rpm.pixelcat.engine.logic.resource.SpriteSheet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObjectManagerImpl implements GameObjectManager {
    private List<GameObject> gameObjects;

    public GameObjectManagerImpl(KernelState kernelState) throws GameException {
        // init game objects
        gameObjects = new ArrayList<>();

        // register game objects
        registerGameObjects(kernelState);
    }

    private void registerGameObjects(KernelState kernelState) throws GameException {
        // setup
        ResourceFactory resourceFactory = ResourceFactory.getInstance();
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();
        AnimationFactory animationFactory = AnimationFactory.getInstance();
        Rectangle bounds = kernelState.getBounds();

        // layer setup
        LayerManager.getInstance().addLayers(2);

        // game objects creation
        SpriteSheet nyanCatSpriteSheet = resourceFactory.createSpriteSheet("nyancat_sprite_sheet.png", 60, 30);
        Resource nyanCatInitialResource = resourceFactory.createImageResource(0, 0, nyanCatSpriteSheet);
        gameObjects.add(
            gameObjectFactory.createGameObject(
                50, 50,
                0,
                ImmutableSet.of(
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.UP,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_UP,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.UP,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.DOWN,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_DOWN,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.DOWN,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_LEFT,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(
                                new GameObjectLogicParameterOrientation(OrientationEnum.LEFT)
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_RIGHT,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(new GameObjectLogicParameterOrientation(OrientationEnum.RIGHT))
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.NO_DIRECTION,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_STOP,
                            ImmutableSet.<GameObjectLogicParameter>of()
                        )
                    )
                ),
                ImmutableMap.of(
                    OrientationEnum.LEFT,
                    animationFactory.createAnimationSequence(
                        ImmutableList.of(
                            resourceFactory.createImageResource(0, 1, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(1, 1, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(2, 1, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(3, 1, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(4, 1, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(5, 1, nyanCatSpriteSheet)
                        ),
                        100L
                    ),
                    OrientationEnum.RIGHT,
                    animationFactory.createAnimationSequence(
                        ImmutableList.of(
                            nyanCatInitialResource,
                            resourceFactory.createImageResource(1, 0, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(2, 0, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(3, 0, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(4, 0, nyanCatSpriteSheet),
                            resourceFactory.createImageResource(5, 0, nyanCatSpriteSheet)
                        ),
                        100L
                    )
                ),
                OrientationEnum.RIGHT,
                nyanCatInitialResource,
                false
            )
        );

        // title
        SpriteSheet pixelCatTitleSpriteSheet = resourceFactory.createSpriteSheet("pixelcat_title_sprite_sheet.png", 200, 80);
        Resource pixelCatTitleInitialResource = resourceFactory.createImageResource(0, 0, pixelCatTitleSpriteSheet);
        gameObjects.add(
            gameObjectFactory.createGameObject(
                (bounds.width - 200) / 2, (bounds.height - 100) / 2 - 100,
                1,
                ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
                ImmutableMap.of(
                    OrientationEnum.FRONT,
                    animationFactory.createAnimationSequence(
                        ImmutableList.of(
                            pixelCatTitleInitialResource,
                            resourceFactory.createImageResource(0, 1, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 2, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 3, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 4, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 5, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 6, pixelCatTitleSpriteSheet),
                            resourceFactory.createImageResource(0, 7, pixelCatTitleSpriteSheet)
                        ),
                        50L
                    )
                ),
                OrientationEnum.FRONT,
                pixelCatTitleInitialResource,
                true
            )
        );

        // subtitle
        Resource pixelCatBlurbInitialResource = resourceFactory.createTextResource(
            "The 2D Sprite Base Video Game Engine",
            new Font("Courier New", Font.BOLD, 20)
        );
        gameObjects.add(
            gameObjectFactory.createGameObject(
                bounds.width / 2 - 200, bounds.height / 2 - 30,
                1,
                ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
                pixelCatBlurbInitialResource
            )
        );
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
