package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.engine.logic.animation.AnimationFactory;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;
import com.rpm.pixelcat.engine.logic.camera.Camera;
import com.rpm.pixelcat.engine.logic.gameobject.*;
import com.rpm.pixelcat.engine.logic.gameobject.behavior.*;
import com.rpm.pixelcat.engine.logic.gameobject.feature.*;
import com.rpm.pixelcat.engine.logic.physics.screen.ScreenBoundsHandlingTypeEnum;
import com.rpm.pixelcat.engine.logic.resource.Resource;
import com.rpm.pixelcat.engine.logic.resource.ResourceFactory;
import com.rpm.pixelcat.engine.logic.resource.SpriteSheet;
import com.rpm.pixelcat.engine.test.enumeration.GameObjectHandle;
import com.rpm.pixelcat.engine.test.enumeration.GameObjectManagerHandle;
import com.rpm.pixelcat.engine.test.enumeration.LevelHandle;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameObjectsHandler {
    private KernelState kernelState;
    private Map<GameObjectHandle, GameObjectIdentifier> gameObjects;
    private Map<GameObjectManagerHandle, String> gameObjectManagerHandles;
    private Map<String, GameObjectManager> gameObjectManagers;
    private Map<LevelHandle, List<String>> levels;

    private static ResourceFactory resourceFactory = ResourceFactory.getInstance();
    private static AnimationFactory animationFactory = AnimationFactory.getInstance();
    private static Random randomGenerator = new Random();

    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectsHandler.class);

    GameObjectsHandler(KernelState kernelState) {
        this.kernelState = kernelState;
        this.gameObjects = new HashMap<>();
        this.gameObjectManagers = new HashMap<>();
        this.levels = new HashMap<>();
    }

    private GameObjectsHandler setGameObject(GameObjectHandle handle, String objectId, String managerId) {
        // generate identifier
        GameObjectIdentifier gameObjectIdentifier = new GameObjectIdentifier(objectId, managerId);

        // store identifier against handle
        gameObjects.put(handle, gameObjectIdentifier);

        return this;
    }

    public GameObject getGameObject(GameObjectHandle gameObjectHandle) throws GameException {
        // fetch identifier
        GameObjectIdentifier gameObjectIdentifier = gameObjects.get(gameObjectHandle);

        // fetch game object manager
        GameObjectManager gameObjectManager = getGameObjectManager(gameObjectIdentifier.getParentId());

        // fetch game object from game manager
        GameObject gameObject = gameObjectManager.get(gameObjectIdentifier.id);

        return gameObject;
    }

    public GameObjectsHandler setGameObjectManager(GameObjectManagerHandle handle, GameObjectManager manager) {
        // store id against handle
        gameObjectManagerHandles.put(handle, manager.getId());

        // store manager against id
        gameObjectManagers.put(manager.getId(), manager);

        return this;
    }

    public GameObjectManager getGameObjectManager(String id) throws GameException {
        // validate
        if (!gameObjectManagers.containsKey(id)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch manager from id
        GameObjectManager gameObjectManager = gameObjectManagers.get(id);

        return gameObjectManager;
    }

    public GameObjectManager getGameObjectManager(GameObjectManagerHandle handle) throws GameException {
        // validate
        if (!gameObjectManagerHandles.containsKey(handle)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch id from handle
        String id = gameObjectManagerHandles.get(handle);

        // fetch manager from id
        GameObjectManager manager = getGameObjectManager(id);

        return manager;
    }

    private GameObjectsHandler setGameObjectManagerList(LevelHandle handle, List<GameObjectManager> managers) {
        // setup
        List<String> managerIds = new ArrayList<>();

        // iterate through and build list of IDs from managers
        for (GameObjectManager manager : managers) {
            managerIds.add(manager.getId());
        }

        // store list of manager IDs
        levels.put(handle, managerIds);

        return this;
    }

    public List<GameObjectManager> getGameObjectManagerList(LevelHandle handle) throws GameException {
        // setup
        List<GameObjectManager> managers = new ArrayList<>();

        // validate
        if (!levels.containsKey(handle)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch list of manager IDs
        List<String> managerIds = levels.get(handle);

        // iterate through and build list of managers from IDs
        for (String managerId : managerIds) {
            managers.add(getGameObjectManager(managerId));
        }

        return managers;
    }

    public void init() throws GameException {
        // common elements
        setGameObjectManager(GameObjectManagerHandle.COMMON, generateCommonElements());

        // start screen elements
        setGameObjectManager(GameObjectManagerHandle.START_SCREEN, generateStartScreenElements());

        // level one elements
        setGameObjectManager(GameObjectManagerHandle.L1, generateLevelOneElements());

        // start screen
        setGameObjectManagerList(
            LevelHandle.START_SCREEN,
            ImmutableList.of(
                getGameObjectManager(GameObjectManagerHandle.COMMON),
                getGameObjectManager(GameObjectManagerHandle.START_SCREEN)
            )
        );

        // level one
        setGameObjectManagerList(
            LevelHandle.L1,
            ImmutableList.of(
                getGameObjectManager(GameObjectManagerHandle.COMMON),
                getGameObjectManager(GameObjectManagerHandle.L1)
            )
        );
    }

    private GameObjectManager generateCommonElements() throws GameException {
        // init game object manager
        GameObjectManager gameObjectManager = GameObjectManager.create(5);

        // cat character
        GameObject cat = generateCatCharacterGameObject(gameObjectManager);

        // add object to game objects manager
        gameObjectManager.add(cat);

        // store game object ID for game processing
        setGameObject(GameObjectHandle.CAT_CHARACTER, cat.getId(), gameObjectManager.getId());

        return gameObjectManager;
    }

    private GameObject generateCatCharacterGameObject(GameObjectManager gameObjectManager) throws GameException {
        // generate sprite sheet
        SpriteSheet spriteSheet = resourceFactory.createSpriteSheet(
            "cat_sprite_sheet.png",
            60, 30,
            0, 25,
            0, 10
        );

        // create game object
        GameObject gameObject = gameObjectManager.createGameObject();

        // register rendering properties
        gameObject.registerFeature(
            Renderable.create(new Point(50, 50), 2)
        );

        // define resources
        List<Resource> catResourcesRight = ImmutableList.of(
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(0, 0, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(1, 0, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(2, 0, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(3, 0, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(4, 0, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(5, 0, spriteSheet), null)
        );
        List<Resource> catResourcesLeft = ImmutableList.of(
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(0, 1, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(1, 1, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(2, 1, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(3, 1, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(4, 1, spriteSheet), null),
            resourceFactory.createImageResource(resourceFactory.createSpriteResource(5, 1, spriteSheet), null)
        );

        // register resources
        gameObject.registerFeature(
            ResourceLibrary.create().add(
                catResourcesRight.get(0)
            ).add(
                catResourcesRight.get(1)
            ).add(
                catResourcesRight.get(2)
            ).add(
                catResourcesRight.get(3)
            ).add(
                catResourcesRight.get(4)
            ).add(
                catResourcesRight.get(5)
            ).add(
                catResourcesLeft.get(0)
            ).add(
                catResourcesLeft.get(1)
            ).add(
                catResourcesLeft.get(2)
            ).add(
                catResourcesLeft.get(3)
            ).add(
                catResourcesLeft.get(4)
            ).add(
                catResourcesLeft.get(5)
            )
        );

        // define animations
        AnimationSequence catAnimationRight = animationFactory.createAnimationSequence(
            100L
        ).addCel(
            catResourcesRight.get(0).getId()
        ).addCel(
            catResourcesRight.get(1).getId()
        ).addCel(
            catResourcesRight.get(2).getId()
        ).addCel(
            catResourcesRight.get(3).getId()
        ).addCel(
            catResourcesRight.get(4).getId()
        ).addCel(
            catResourcesRight.get(5).getId()
        );
        AnimationSequence catAnimationLeft = animationFactory.createAnimationSequence(
            100L
        ).addCel(
            catResourcesLeft.get(0).getId()
        ).addCel(
            catResourcesLeft.get(1).getId()
        ).addCel(
            catResourcesLeft.get(2).getId()
        ).addCel(
            catResourcesLeft.get(3).getId()
        ).addCel(
            catResourcesLeft.get(4).getId()
        ).addCel(
            catResourcesLeft.get(5).getId()
        );

        // register animations
        gameObject.registerFeature(
            AnimationSequenceLibrary.create().add(
                catAnimationRight
            ).add(
                catAnimationLeft
            )
        );

        // define cameras
        Camera catCameraRight = Camera.create(catAnimationRight.getId(), catAnimationRight.getClass());
        Camera catCameraLeft = Camera.create(catAnimationLeft.getId(), catAnimationLeft.getClass());

        // register cameras
        gameObject.registerFeature(
            CameraLibrary.create().add(
                catCameraRight
            ).add(
                catCameraLeft
            )
        );

        // register physics
        gameObject.registerFeature(
            PhysicsBindingSet.create().add(
                ScreenBoundsHandlingTypeEnum.CENTER_RESOURCE
            )
        );

        // register hid behaviors
        BehaviorParameter catMovementMagnitude = BehaviorParameterFactory.getInstance().createMagnitudeParameter(2.0);
        gameObject.registerFeature(
            HIDBehaviorBindingSet.create().add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_UP,
                    new Behavior(
                        BehaviorEnum.MOVE_UP,
                        ImmutableSet.of(catMovementMagnitude)
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_UP,
                    new Behavior(BehaviorEnum.ANIMATION_PLAY)
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_DOWN,
                    new Behavior(
                        BehaviorEnum.MOVE_DOWN,
                        ImmutableSet.of(catMovementMagnitude)
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_DOWN,
                    new Behavior(BehaviorEnum.ANIMATION_PLAY)
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_LEFT,
                    new Behavior(
                        BehaviorEnum.MOVE_LEFT,
                        ImmutableSet.of(catMovementMagnitude)
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_LEFT,
                    new Behavior(BehaviorEnum.ANIMATION_PLAY)
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_LEFT,
                    new Behavior(
                        BehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                        ImmutableSet.of(BehaviorParameterFactory.getInstance().createCameraParameter(catCameraLeft.getId()))
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_RIGHT,
                    new Behavior(
                        BehaviorEnum.MOVE_RIGHT,
                        ImmutableSet.of(catMovementMagnitude)
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_RIGHT,
                    new Behavior(BehaviorEnum.ANIMATION_PLAY)
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_RIGHT,
                    new Behavior(
                        BehaviorEnum.ANIMATION_SEQUENCE_SWITCH,
                        ImmutableSet.of(BehaviorParameterFactory.getInstance().createCameraParameter(catCameraRight.getId()))
                    )
                )
            ).add(
                new HIDBehaviorBinding(
                    HIDEventEnum.PRIMARY_NO_DIRECTION,
                    new Behavior(BehaviorEnum.ANIMATION_STOP)
                )
            )
        );

        return gameObject;
    }

    private GameObjectManager generateLevelOneElements() throws GameException {
        // init game object manager
        GameObjectManager gameObjectManager = GameObjectManager.create(((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).height);

        // dynamically generate grass background
        generateGrassBGGameObjects(gameObjectManager);

        // dynamically generate bushes
        generateBushGameObjects(gameObjectManager);

        return gameObjectManager;
    }

    private void generateGrassBGGameObjects(GameObjectManager gameObjectManager) throws GameException {
        // generate sprite sheet
        SpriteSheet grassBGSpriteSheet = resourceFactory.createSpriteSheet("grass_bg.png", 1950, 2);

        // generate resources
        Resource grassBGResourceGreen = resourceFactory.createSpriteResource(0, 0, grassBGSpriteSheet);
        Resource grassBGResourceBrown = resourceFactory.createSpriteResource(0, 1, grassBGSpriteSheet);

        // generate game objects
        for (Integer i = 0; i < ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).height / 2; i++) {
            // generate game object
            GameObject gameObject = generateGrassBGGameObject(
                gameObjectManager,
                i % 2 == 0 ? grassBGResourceGreen : grassBGResourceBrown,
                i
            );

            // add game object
            gameObjectManager.add(gameObject);
        }
    }

    private GameObject generateGrassBGGameObject(GameObjectManager gameObjectManager, Resource resource, Integer yIndex) throws GameException {
        // generate game object
        GameObject gameObject = gameObjectManager.createGameObject();

        // register render properties
        gameObject.registerFeature(Renderable.create(new Point(0, yIndex * 2), 0));

        // register resources
        gameObject.registerFeature(ResourceLibrary.create().add(resource));

        return gameObject;
    }

    private void generateBushGameObjects(GameObjectManager gameObjectManager) throws GameException {
        // generate sprite sheet
        SpriteSheet spriteSheet = resourceFactory.createSpriteSheet("bush_sprite_sheet.png", 18, 19, 1);

        // generate game objects
        for (Integer i = 0; i < 1000; i++) {
            // generate game object
            GameObject gameObject = generateBushObject(gameObjectManager, spriteSheet);

            // add game object
            gameObjectManager.add(gameObject);
        }
    }

    private GameObject generateBushObject(GameObjectManager gameObjectManager, SpriteSheet bushSpriteSheet)
            throws GameException {
        // setup
        Rectangle screenBounds = (Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS);
        Integer x = randomGenerator.nextInt(screenBounds.width);
        Integer y = randomGenerator.nextInt(screenBounds.height);
        Integer animationKey = randomGenerator.nextInt(3);

        // generate game object
        GameObject bush = gameObjectManager.createGameObject();

        // register render properties
        bush.registerFeature(
            Renderable.create(new Point(x, y), y)
        );

        // generate resources
        Resource bushLeftResource = resourceFactory.createSpriteResource(0, 0, bushSpriteSheet);
        Resource bushCenterResource = resourceFactory.createSpriteResource(1, 0, bushSpriteSheet);
        Resource bushRightResource = resourceFactory.createSpriteResource(2, 0, bushSpriteSheet);

        // generate current resource + animation cel
        String currentCel;
        switch (animationKey) {
            case 0:
                currentCel = bushLeftResource.getId();
                break;
            case 1:
                currentCel = bushRightResource.getId();
                break;
            case 2:
            default:
                PRINTER.printWarning("Animation key miss on random generation... [" + animationKey + "]");
                currentCel = bushCenterResource.getId();
                break;
        }

        // register resources
        bush.registerFeature(
            ResourceLibrary.create().add(
                bushLeftResource
            ).add(
                bushCenterResource
            ).add(
                bushRightResource
            ).setCurrent(
                currentCel
            )
        );

        // generate animation
        AnimationSequence animation = AnimationFactory.getInstance().createAnimationSequence(
            (randomGenerator.nextInt(3) + 2) * 100L
        ).addCel(
            bushLeftResource.getId()
        ).addCel(
            bushCenterResource.getId()
        ).addCel(
            bushRightResource.getId()
        ).setCurrentCel(
            currentCel
        );

        // register animation
        bush.registerFeature(
            AnimationSequenceLibrary.create().add(animation)
        );

        return bush;
    }

    private GameObjectManager generateStartScreenElements() throws GameException {
        // setup
        Rectangle screenBounds = ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS));
        Font instructionsFont = new Font("Courier New", Font.BOLD, 12);
        String instructions1Text = "- Or, just have fun moving the nyan cat with the arrow keys! -";
        Point instructions1Position = new Point(-140, 50);
        String instructions2Text = "Press enter to continue, escape to exit...";
        Point instructions2Position = new Point(-220, 65);

        // init game object manager
        GameObjectManager gameObjectManager = GameObjectManager.create(2);

        // title
        GameObject title = generateTitleGameObject(gameObjectManager, screenBounds);

        // subtitle
        Font subtitleFont = new Font("Courier New", Font.BOLD, 20);
        String subtitleText = "The 2D Sprite Base Video Game Engine";
        Point subtitlePosition = new Point(-220, -30);
        GameObject subtitle = generateTextGameObject(
            gameObjectManager,
            screenBounds,
            subtitleFont,
            subtitleText,
            subtitlePosition
        );

        // instructions1
        GameObject instructions1 = generateTextGameObject(
            gameObjectManager,
            screenBounds,
            instructionsFont,
            instructions1Text,
            instructions1Position
        );

        // instructions2
        GameObject instructions2 = generateTextGameObject(
            gameObjectManager,
            screenBounds,
            instructionsFont,
            instructions2Text,
            instructions2Position
        );

        // add game objects
        gameObjectManager.add(
            title
        ).add(
            subtitle
        ).add(
            instructions1
        ).add(
            instructions2
        );

        // store game object IDs for game processing
        setGameObject(
            GameObjectHandle.START_SCREEN_TITLE, title.getId(), gameObjectManager.getId()
        ).setGameObject(
            GameObjectHandle.START_SCREEN_SUBTITLE, subtitle.getId(), gameObjectManager.getId()
        ).setGameObject(
            GameObjectHandle.START_SCREEN_INS1, instructions1.getId(), gameObjectManager.getId()
        ).setGameObject(
            GameObjectHandle.START_SCREEN_INS2, instructions2.getId(), gameObjectManager.getId()
        );

        return gameObjectManager;
    }

    private GameObject generateTitleGameObject(GameObjectManager gameObjectManager, Rectangle screenBounds) throws GameException {
        // generate game object
        GameObject title = gameObjectManager.createGameObject();

        // register render properties
        title.registerFeature(
            Renderable.create(new Point(screenBounds.width / 2 - 95, screenBounds.height / 2 - 150), 1)
        );

        // generate sprite sheet
        SpriteSheet spriteSheet = resourceFactory.createSpriteSheet("pixelcat_title_sprite_sheet.png", 190, 80, 0, 0, 0, 5);

        // generate resources
        List<Resource> resources = ImmutableList.of(
            resourceFactory.createSpriteResource(0, 0, spriteSheet),
            resourceFactory.createSpriteResource(0, 1, spriteSheet),
            resourceFactory.createSpriteResource(0, 2, spriteSheet),
            resourceFactory.createSpriteResource(0, 3, spriteSheet),
            resourceFactory.createSpriteResource(0, 4, spriteSheet),
            resourceFactory.createSpriteResource(0, 5, spriteSheet),
            resourceFactory.createSpriteResource(0, 6, spriteSheet),
            resourceFactory.createSpriteResource(0, 7, spriteSheet)
        );

        // register resources
        title.registerFeature(
            ResourceLibrary.create().add(
                resources.get(0)
            ).add(
                resources.get(1)
            ).add(
                resources.get(2)
            ).add(
                resources.get(3)
            ).add(
                resources.get(4)
            ).add(
                resources.get(5)
            ).add(
                resources.get(6)
            ).add(
                resources.get(7)
            )
        );

        // generate animation
        AnimationSequence animation = animationFactory.createAnimationSequence(
            50L
        ).addCel(
            resources.get(0).getId()
        ).addCel(
            resources.get(1).getId()
        ).addCel(
            resources.get(2).getId()
        ).addCel(
            resources.get(3).getId()
        ).addCel(
            resources.get(4).getId()
        ).addCel(
            resources.get(5).getId()
        ).addCel(
            resources.get(6).getId()
        ).addCel(
            resources.get(7).getId()
        );

        // register animation
        title.registerFeature(
            AnimationSequenceLibrary.create().add(
                animation
            )
        );

        return title;
    }

    private GameObject generateTextGameObject(GameObjectManager gameObjectManager,
                                              Rectangle screenBounds,
                                              Font font,
                                              String text,
                                              Point positionOffset)
                       throws GameException {
        // generate game object
        GameObject textObject = gameObjectManager.createGameObject().registerFeature(
            Renderable.create(
                new Point(screenBounds.width / 2 + positionOffset.x, screenBounds.height / 2 + positionOffset.y),
                1
            )
        ).registerFeature(
            ResourceLibrary.create().add(
                resourceFactory.createTextResource(text, font)
            )
        );

        return textObject;
    }

    protected class GameObjectIdentifier {
        private String id;
        private String parentId;

        protected GameObjectIdentifier(String id, String parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        public String getId() {
            return id;
        }

        public String getParentId() {
            return parentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof GameObjectIdentifier)) {
                return false;
            }

            GameObjectIdentifier that = (GameObjectIdentifier) o;

            if (!id.equals(that.id)) {
                return false;
            }

            return parentId.equals(that.parentId);
        }

        @Override
        public String toString() {
            return "GameObjectIdentifier{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
        }
    }
}
