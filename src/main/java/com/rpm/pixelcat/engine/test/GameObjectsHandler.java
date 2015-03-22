package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.logic.animation.AnimationFactory;
import com.rpm.pixelcat.engine.logic.gameobject.*;
import com.rpm.pixelcat.engine.logic.resource.Resource;
import com.rpm.pixelcat.engine.logic.resource.ResourceFactory;
import com.rpm.pixelcat.engine.logic.resource.SpriteSheet;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameObjectsHandler {
    private KernelState kernelState;
    Map<String, GameObject> gameObjects;
    private Map<String, GameObjectManager> gameObjectManagers;
    private Map<String, List<GameObjectManager>> gameObjectManagerSets;

    private static ResourceFactory resourceFactory = ResourceFactory.getInstance();
    private static GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();
    private static AnimationFactory animationFactory = AnimationFactory.getInstance();
    private static Random randomGenerator = new Random();

    public static final String GAME_OBJECT_MANAGER_COMMON = "common";
    public static final String GAME_OBJECT_MANAGER_START_SCREEN = LevelHandler.START_SCREEN;
    public static final String GAME_OBJECT_MANAGER_LEVEL_ONE = LevelHandler.LEVEL_ONE;

    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectsHandler.class);

    GameObjectsHandler(KernelState kernelState) {
        this.kernelState = kernelState;
        this.gameObjects = new HashMap<>();
        this.gameObjectManagers = new HashMap<>();
        this.gameObjectManagerSets = new HashMap<>();
    }

    public GameObject getGameObject(String key) {
        return gameObjects.get(key);
    }

    public GameObjectManager getGameObjectManager(String key) {
        return gameObjectManagers.get(key);
    }

    public List<GameObjectManager> getGameObjectManagerSet(String key) {
        return gameObjectManagerSets.get(key);
    }

    public void init() throws GameException {
        // common elements
        gameObjectManagers.put(GAME_OBJECT_MANAGER_COMMON, generateCommonElements());

        // start screen elements
        gameObjectManagers.put(GAME_OBJECT_MANAGER_START_SCREEN, generateStartScreenElements());

        // level one elements
        gameObjectManagers.put(GAME_OBJECT_MANAGER_LEVEL_ONE, generateLevelOneElements());

        // start screen
        gameObjectManagerSets.put(
            LevelHandler.START_SCREEN,
            ImmutableList.of(
                gameObjectManagers.get(GAME_OBJECT_MANAGER_COMMON),
                gameObjectManagers.get(GAME_OBJECT_MANAGER_START_SCREEN)
            )
        );

        // level one
        gameObjectManagerSets.put(
            LevelHandler.LEVEL_ONE,
            ImmutableList.of(
                gameObjectManagers.get(GAME_OBJECT_MANAGER_COMMON),
                gameObjectManagers.get(GAME_OBJECT_MANAGER_LEVEL_ONE)
            )
        );
    }

    private GameObjectManager generateCommonElements() throws GameException {
        // init game object manager
        GameObjectManager gameObjectManager = GameObjectFactory.getInstance().createGameObjectManager(5);

        // nyan cat character
        SpriteSheet nyanCatSpriteSheet = resourceFactory.createSpriteSheet("nyancat_sprite_sheet.png", 60, 30, 0, 25, 0, 10);
        Resource nyanCatInitialResource = resourceFactory.createImageResource(0, 0, nyanCatSpriteSheet);
        gameObjects.put(
            "nyanCat",
            gameObjectFactory.createGameObject(
                50, 50,
                0,
                ImmutableSet.of(
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_UP,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_UP,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createMagnitudeParameter(
                                    2.0
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_UP,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_DOWN,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_DOWN,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createMagnitudeParameter(
                                    2.0
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_DOWN,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_LEFT,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createMagnitudeParameter(
                                    2.0
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_LEFT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createOrientationParameter(
                                    OrientationEnum.LEFT
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.MOVE_RIGHT,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createMagnitudeParameter(
                                    2.0
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_PLAY,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of()
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_RIGHT,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                            ImmutableSet.of(
                                GameObjectLogicBehaviorParameterFactory.getInstance().createOrientationParameter(
                                    OrientationEnum.RIGHT
                                )
                            )
                        )
                    ),
                    new GameObjectHIDEventLogicBehaviorBinding(
                        HIDEventEnum.PRIMARY_NO_DIRECTION,
                        new GameObjectLogicBehavior(
                            GameObjectLogicBehaviorEnum.ANIMATION_STOP,
                            ImmutableSet.<GameObjectLogicBehaviorParameter>of()
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
                false,
                CollisionHandlingTypeEnum.FULL,
                ScreenBoundsHandlingTypeEnum.CENTER_RESOURCE
            )
        );
        gameObjectManager.addGameObject(getGameObject("nyanCat"));

        return gameObjectManager;
    }

    private GameObjectManager generateLevelOneElements() throws GameException {
        // init game object manager
        GameObjectManager gameObjectManager = GameObjectFactory.getInstance().createGameObjectManager(kernelState.getBounds().height);

        // dynamically generate grass background
        SpriteSheet grassBGSpriteSheet = resourceFactory.createSpriteSheet("grass_bg.png", 1950, 2);
        for (Integer i = 0; i < kernelState.getBounds().height / 2; i++) {
            String gameObjectName = "grassBG" + (i % 2 == 0 ? "Green" : "Brown") + i;
            gameObjects.put(gameObjectName, generateGrassBGObject(grassBGSpriteSheet, i));
            gameObjectManager.addGameObject(gameObjects.get(gameObjectName));
        }

        // dynamically generate bushes
        SpriteSheet bushSpriteSheet = resourceFactory.createSpriteSheet("bush_sprite_sheet.png", 18, 19, 1);
        for (Integer i = 0; i < 1000; i++) {
            String gameObjectName = "bush" + i;
            gameObjects.put(gameObjectName, generateBushObject(bushSpriteSheet));
            gameObjectManager.addGameObject(gameObjects.get(gameObjectName));
        }

        return gameObjectManager;
    }

    private GameObject generateGrassBGObject(SpriteSheet grassBGSpriteSheet, Integer yIndex) throws GameException {
        // define resource
        Resource resource = resourceFactory.createImageResource(
            0, yIndex % 2 == 0 ? 0 : 1,
            grassBGSpriteSheet
        );

        // generate game object
        GameObject gameObject = gameObjectFactory.createGameObject(
            0, yIndex * 2,
            0,
            ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
            resource,
            CollisionHandlingTypeEnum.NONE,
            ScreenBoundsHandlingTypeEnum.NONE
        );

        return gameObject;
    }

    private GameObject generateBushObject(SpriteSheet bushSpriteSheet)
            throws GameException {
        // initial resource + animation cels
        Integer animationKey = randomGenerator.nextInt(3);
        Resource initialResource;
        List<Resource> animationCels;
        switch (animationKey) {
            case 0:
                initialResource = resourceFactory.createImageResource(0, 0, bushSpriteSheet);
                animationCels = ImmutableList.of(
                    initialResource,
                    resourceFactory.createImageResource(1, 0, bushSpriteSheet),
                    resourceFactory.createImageResource(2, 0, bushSpriteSheet)
                );
                break;
            case 1:
                initialResource = resourceFactory.createImageResource(1, 0, bushSpriteSheet);
                animationCels = ImmutableList.of(
                    resourceFactory.createImageResource(0, 0, bushSpriteSheet),
                    initialResource,
                    resourceFactory.createImageResource(2, 0, bushSpriteSheet)
                );
                break;
            case 2:
                initialResource = resourceFactory.createImageResource(2, 0, bushSpriteSheet);
                animationCels = ImmutableList.of(
                    resourceFactory.createImageResource(0, 0, bushSpriteSheet),
                    resourceFactory.createImageResource(1, 0, bushSpriteSheet),
                    initialResource
                );
                break;
            default:
                PRINTER.printWarning("Animation key miss on random generation... [" + animationKey + "]");
                initialResource = resourceFactory.createImageResource(0, 0, bushSpriteSheet);
                animationCels = ImmutableList.of(
                    initialResource
                );
                break;
        }

        // game object
        Integer y = randomGenerator.nextInt(kernelState.getBounds().height);
        GameObject gameObject = gameObjectFactory.createGameObject(
            randomGenerator.nextInt(kernelState.getBounds().width), y,
            y,
            ImmutableSet.of(),
            ImmutableMap.of(
                OrientationEnum.FRONT,
                animationFactory.createAnimationSequence(
                    animationCels,
                    (randomGenerator.nextInt(3) + 2) * 100L
                )
            ),
            OrientationEnum.FRONT,
            initialResource,
            true,
            CollisionHandlingTypeEnum.MASK,
            ScreenBoundsHandlingTypeEnum.NONE
        );

        return gameObject;
    }

    private GameObjectManager generateStartScreenElements() throws GameException {
        // setup
        Rectangle bounds = kernelState.getBounds();

        // init game object manager
        GameObjectManager gameObjectManager = GameObjectFactory.getInstance().createGameObjectManager(2);

        // title
        SpriteSheet pixelCatTitleSpriteSheet = resourceFactory.createSpriteSheet("pixelcat_title_sprite_sheet.png", 190, 80, 0, 0, 0, 5);
        Resource pixelCatTitleInitialResource = resourceFactory.createImageResource(0, 0, pixelCatTitleSpriteSheet);
        gameObjects.put(
            "pixelCatTitle",
            gameObjectFactory.createGameObject(
                bounds.width / 2 - 95, bounds.height / 2 - 150,
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
                true,
                CollisionHandlingTypeEnum.NONE,
                ScreenBoundsHandlingTypeEnum.NONE
            )
        );
        gameObjectManager.addGameObject(gameObjects.get("pixelCatTitle"));

        // subtitle
        Resource pixelCatBlurbInitialResource = resourceFactory.createTextResource(
            "The 2D Sprite Base Video Game Engine",
            new Font("Courier New", Font.BOLD, 20)
        );
        gameObjects.put(
            "pixelCatBlurb",
            gameObjectFactory.createGameObject(
                bounds.width / 2 - 220, bounds.height / 2 - 30,
                1,
                ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
                pixelCatBlurbInitialResource,
                CollisionHandlingTypeEnum.NONE,
                ScreenBoundsHandlingTypeEnum.NONE
            )
        );
        gameObjectManager.addGameObject(gameObjects.get("pixelCatBlurb"));

        // instructions1
        Resource instructionsInitialResource1 = resourceFactory.createTextResource(
            "Press enter to continue, escape to exit...",
            new Font("Courier New", Font.BOLD, 12)
        );
        String instructionsGameObjectName1 = "instructions1";
        gameObjects.put(
            instructionsGameObjectName1,
            gameObjectFactory.createGameObject(
                bounds.width / 2 - 140, bounds.height / 2 + 50,
                1,
                ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
                instructionsInitialResource1,
                CollisionHandlingTypeEnum.NONE,
                ScreenBoundsHandlingTypeEnum.NONE
            )
        );
        gameObjectManager.addGameObject(gameObjects.get(instructionsGameObjectName1));

        // instructions2
        Resource instructionsInitialResource2 = resourceFactory.createTextResource(
            "- Or, just have fun moving the nyan cat with the arrow keys! -",
            new Font("Courier New", Font.BOLD, 12)
        );
        String instructionsGameObjectName2 = "instructions2";
        gameObjects.put(
            instructionsGameObjectName2,
            gameObjectFactory.createGameObject(
                bounds.width / 2 - 220, bounds.height / 2 + 65,
                1,
                ImmutableSet.<GameObjectHIDEventLogicBehaviorBinding>of(),
                instructionsInitialResource2,
                CollisionHandlingTypeEnum.NONE,
                ScreenBoundsHandlingTypeEnum.NONE
            )
        );
        gameObjectManager.addGameObject(gameObjects.get(instructionsGameObjectName2));

        return gameObjectManager;
    }
}
