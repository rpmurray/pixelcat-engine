package info.masterfrog.pixelcat.engine.logic.gameobject.manager;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.Behavior;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorBindingImpl;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorParameter;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorParameterGenerator;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorParameterId;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorParameterLogicRoutine;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.behavior.BehaviorParameterMagnitude;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.AnimationSequenceLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.BehaviorBindingLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.PhysicsBindingSet;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.CameraLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.RenderingLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.ResourceLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.bounds.BoundsHandler;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.bounds.CanvasBoundsHandler;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.physics.bounds.ScreenBoundsHandler;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.animation.AnimationSequence;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.camera.Camera;
import info.masterfrog.pixelcat.engine.logic.resource.MeasurableResource;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Map;

class GameObjectUpdater {
    private static GameObjectUpdater instance;
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(GameObjectUpdater.class);

    private GameObjectUpdater() {
    }

    static GameObjectUpdater getInstance() {
        if (instance == null) {
            instance = new GameObjectUpdater();
        }

        return instance;
    }

    void update(GameObject gameObject) throws TransientGameException {
        updateForHIDEventGameLogicBindings(gameObject);
        if (gameObject.hasFeature(AnimationSequenceLibrary.class)) {
            updateAnimation(gameObject);
        }
    }

    private void updateForHIDEventGameLogicBindings(GameObject gameObject) throws TransientGameException {
        // validate
        if (!gameObject.isFeatureAvailable(BehaviorBindingLibrary.class)) {
            return;
        }

        // setup
        Map<String, BehaviorBindingImpl<BindableEvent>> behaviorBindings = gameObject.getFeature(BehaviorBindingLibrary.class).getAll();
        HashSet<HIDEvent> triggeredHIDEvents = KernelState.getInstance().getHIDTriggeredEvents();
        HashSet<HIDEvent> sustainedHIDEvents = KernelState.getInstance().getHIDSustainedEvents();

        // update object based on HID events
        for (BehaviorBindingImpl<BindableEvent> behaviorBinding : behaviorBindings.values()) {
            // setup
            Behavior behavior = behaviorBinding.getBehavior();

            if (behaviorBinding.getBoundEvent() instanceof HIDEvent) {
                // setup
                HIDEvent boundHIDEvent = (HIDEvent) behaviorBinding.getBoundEvent();

                // check that bound event was triggered
                if (!triggeredHIDEvents.contains(boundHIDEvent)) {
                    continue;
                }
            } else {
                throw new TransientGameException(GameEngineErrorCode.UNSUPPORTED_FUNCTIONALITY, "Behavior bindings only supported for HID Events");
            }

            // check that behavior can be triggered (i.e. not in a cool down period, etc.)
            if (!behaviorBinding.canBehaviorBeTriggered()) {
                continue;
            }

            // if so, handle its update action
            switch (behavior.getBehaviorType().getBehaviorClass()) {
                case MOVEMENT:
                    switch (behavior.getBehaviorType()) {
                        case MOVE_UP:
                        case MOVE_DOWN:
                        case MOVE_LEFT:
                        case MOVE_RIGHT:
                            move(gameObject, behavior);
                            break;
                    }
                    break;
                case ANIMATION:
                    // validate
                    gameObject.verifyFeatureIsAvailable(AnimationSequenceLibrary.class);
                    AnimationSequence animationSequence = gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent();
                    switch (behavior.getBehaviorType()) {
                        case ANIMATION_PLAY:
                            animationSequence.play();
                            break;
                        case ANIMATION_STOP:
                            animationSequence.pause();
                            break;
                    }
                    break;
                case SOUND:
                    // validate
                    gameObject.verifyFeatureIsAvailable(ResourceLibrary.class);
                    if (!(gameObject.getFeature(ResourceLibrary.class).get(behavior.getBehaviorParameter(BehaviorParameterId.class).getId()) instanceof SoundResource)) {
                        throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                    }

                    // get sound resource
                    SoundResource soundResource = (SoundResource) gameObject.getFeature(ResourceLibrary.class).get(
                        behavior.getBehaviorParameter(BehaviorParameterId.class).getId()
                    );
                    switch (behavior.getBehaviorType()) {
                        case SOUND_PLAY:
                            soundResource.play();
                            break;
                        case SOUND_PAUSE:
                            soundResource.pause();
                            break;
                        case SOUND_STOP:
                            soundResource.stop();
                            break;
                        case SOUND_VOLUME:
                            float volume = behavior.getBehaviorParameter(BehaviorParameterMagnitude.class).getMagnitude().floatValue();
                            soundResource.setVolume(volume);
                            break;
                    }
                    break;
                case CAMERA:
                    switch (behavior.getBehaviorType()) {
                        case CAMERA_SWITCH:
                            // validate
                            gameObject.verifyFeatureIsAvailable(CameraLibrary.class);

                            // generate dynamic behavior parameter if appropriate
                            BehaviorParameter dynamicBehaviorParameter = null;
                            if (behavior.hasBehaviorParameter(BehaviorParameterGenerator.class)) {
                                BehaviorParameterGenerator generator = behavior.getBehaviorParameter(BehaviorParameterGenerator.class);
                                dynamicBehaviorParameter = generator.getGeneratorDefinition().generateBehaviorParameter(generator.getInputs());
                            }

                            // derive behavior parameter for camera
                            BehaviorParameterId cameraParameter;
                            if (dynamicBehaviorParameter != null && dynamicBehaviorParameter instanceof BehaviorParameterId) {
                                cameraParameter = (BehaviorParameterId) dynamicBehaviorParameter;
                            } else if (behavior.hasBehaviorParameter(BehaviorParameterId.class)) {
                                cameraParameter = behavior.getBehaviorParameter(BehaviorParameterId.class);
                            } else {
                                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, "Camera parameter not found for camera switch behavior binding", gameObject);
                            }

                            // assign new camera
                            gameObject.getFeature(CameraLibrary.class).setCurrent(
                                cameraParameter.getId()
                            );

                            // fetch new camera
                            Camera camera = gameObject.getFeature(CameraLibrary.class).getCurrent();

                            // assign new animation sequence, if applicable, and get new resource ID
                            String resourceId;
                            if (camera.getType().equals(AnimationSequence.class)) {
                                // validate
                                gameObject.verifyFeatureIsAvailable(AnimationSequenceLibrary.class);

                                // setup
                                AnimationSequenceLibrary animationSequenceLibrary = gameObject.getFeature(AnimationSequenceLibrary.class);

                                // stop old animation
                                animationSequenceLibrary.getCurrent().pause();

                                // assign new animation sequence
                                animationSequenceLibrary.setCurrent(camera.getView());

                                // fetch new resource ID
                                resourceId = animationSequenceLibrary.getCurrent().getCurrentCel();

                                // play new animation
                                animationSequenceLibrary.getCurrent().play();
                            } else if (camera.getType().equals(Resource.class)) {
                                // fetch new resource ID
                                resourceId = camera.getView();
                            } else {
                                // unsupported case
                                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, "Unsupported camera type", gameObject);
                            }

                            // validate
                            gameObject.verifyFeatureIsAvailable(ResourceLibrary.class);

                            // assign new resource
                            gameObject.getFeature(ResourceLibrary.class).setCurrent(resourceId);
                            break;
                    }
                    break;
                case LOGIC:
                    switch (behavior.getBehaviorType()) {
                        case LOGIC_ROUTINE:
                            // validate
                            if (!behavior.hasBehaviorParameter(BehaviorParameterLogicRoutine.class)) {
                                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, "Logic routine not found for behavior binding", gameObject);
                            }

                            // fetch logic routine
                            BehaviorParameterLogicRoutine logicRoutine = behavior.getBehaviorParameter(
                                BehaviorParameterLogicRoutine.class
                            );

                            // execute logic routine
                            logicRoutine.getLogicRoutineDefinition().execute(logicRoutine.getInputs());
                            break;
                    }
                    break;
            }

            // record trigger event for hid behavior binding
            behaviorBinding.recordTriggerEvent();
        }
    }

    private void updateAnimation(GameObject gameObject) throws TransientGameException {
        // precondition check
        gameObject.verifyFeatureIsAvailable(AnimationSequenceLibrary.class);

        // setup
        AnimationSequence animationSequence = gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent();

        // step the animation forward as needed
        animationSequence.advanceSequenceByTime();
    }

    private void move(GameObject gameObject, Behavior behavior)
            throws TransientGameException {
        // validate
        gameObject.verifyFeatureIsAvailable(RenderingLibrary.class);

        // setup
        BehaviorParameterMagnitude magnitudeParameter;
        Double xVel = 0.0, yVel = 0.0;

        // derive move magnitude
        Double magnitude;
        try {
            magnitudeParameter = behavior.getBehaviorParameter(BehaviorParameterMagnitude.class);
            magnitude = magnitudeParameter.getMagnitude();
        } catch (TransientGameException e) {
            PRINTER.printWarning("Logic behavior parameter lookup failed during game object movement for {GameObjectLogicBehaviorParameterMagnitude}");
            magnitude = 1.0;
        }

        // map event to velocity changes
        switch (behavior.getBehaviorType()) {
            case MOVE_UP:
                yVel = -magnitude;
                break;
            case MOVE_DOWN:
                yVel = magnitude;
                break;
            case MOVE_RIGHT:
                xVel = magnitude;
                break;
            case MOVE_LEFT:
                xVel = -magnitude;
                break;
            default:
                // do nothing
                break;
        }

        // debug
        PRINTER.printTrace("B:" + behavior);

        // initialize moved position
        Point initialPosition = gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas()).getPosition();
        Point finalPosition = new Point(
            (int) (initialPosition.getX() + xVel),
            (int) (initialPosition.getY() + yVel)
        );

        // bounds checking
        handleScreenBounds(gameObject, finalPosition);
        handleCanvasBounds(gameObject, finalPosition);

        // debug
        PRINTER.printTrace(
            " SB: " + KernelState.getInstance().getProperty(KernelStateProperty.SCREEN_BOUNDS) +
                " GO_IP: " + initialPosition +
                " GO_FP: " + finalPosition +
                (gameObject.hasFeature(PhysicsBindingSet.class) && gameObject.getFeature(PhysicsBindingSet.class).has(ScreenBoundsHandler.class) ?
                    " SBH:" + gameObject.getFeature(PhysicsBindingSet.class).get(ScreenBoundsHandler.class) :
                    "") +
                (gameObject.hasFeature(PhysicsBindingSet.class) && gameObject.getFeature(PhysicsBindingSet.class).has(CanvasBoundsHandler.class) ?
                    " CBH:" + gameObject.getFeature(PhysicsBindingSet.class).get(CanvasBoundsHandler.class) :
                    "")
        );

        // set position
        gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas()).setPosition(finalPosition);
    }

    private void handleScreenBounds(GameObject gameObject, Point finalPosition)
            throws TransientGameException {
        // check if bounds bounds handling is not defined
        if (!gameObject.isFeatureAvailable(PhysicsBindingSet.class) ||
            !gameObject.getFeature(PhysicsBindingSet.class).has(ScreenBoundsHandler.class)) {
            return;
        }

        // validate
        gameObject.verifyFeatureIsAvailable(RenderingLibrary.class);

        // setup
        Rectangle screenBounds = KernelState.getInstance().getProperty(KernelStateProperty.SCREEN_BOUNDS);

        // handle bounds check
        handleBounds(gameObject, gameObject.getFeature(PhysicsBindingSet.class).get(ScreenBoundsHandler.class), screenBounds, finalPosition);
    }

    private void handleCanvasBounds(GameObject gameObject, Point finalPosition)
            throws TransientGameException {
        // check if bounds bounds handling is not defined
        if (!gameObject.isFeatureAvailable(PhysicsBindingSet.class) ||
            !gameObject.getFeature(PhysicsBindingSet.class).has(CanvasBoundsHandler.class)) {
            return;
        }

        // validate
        gameObject.verifyFeatureIsAvailable(RenderingLibrary.class);

        // setup
        Rectangle canvasBounds = gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas()).getCanvas().getBounds();

        // handle bounds check
        handleBounds(gameObject, gameObject.getFeature(PhysicsBindingSet.class).get(CanvasBoundsHandler.class), canvasBounds, finalPosition);
    }

    private void handleBounds(GameObject gameObject, BoundsHandler boundsHandler, Rectangle bounds, Point finalPosition)
            throws TransientGameException {
        // validate
        gameObject.verifyFeatureIsAvailable(RenderingLibrary.class);

        // setup
        Resource resource = gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas()).getRenderableResource(gameObject);
        Rectangle resourceBounds;
        Rectangle collisionSpace;
        Point collisionOffset;
        Point positionOffset;

        // check that resource is measurable
        if (resource instanceof MeasurableResource) {
            resourceBounds = ((MeasurableResource) resource).getCelSize();
        } else {
            return;
        }

        // determine checkable position of resource
        switch (boundsHandler.getBoundsType()) {
            case OUTER_RESOURCE_EDGE:
                collisionSpace = new Rectangle(
                    finalPosition.x,
                    finalPosition.y,
                    resourceBounds.width,
                    resourceBounds.height
                );
                collisionOffset = new Point(
                    0,
                    0
                );
                positionOffset = new Point(
                    0,
                    0
                );
                break;
            case INNER_RESOURCE_EDGE:
                collisionSpace = new Rectangle(
                    finalPosition.x,
                    finalPosition.y,
                    resourceBounds.width,
                    resourceBounds.height
                );
                collisionOffset = new Point(
                    resourceBounds.width,
                    resourceBounds.height
                );
                positionOffset = new Point(
                    0,
                    0
                );
                break;
            case CENTER_RESOURCE:
                collisionSpace = new Rectangle(
                    finalPosition.x + (resourceBounds.width / 2),
                    finalPosition.y + (resourceBounds.height / 2),
                    0,
                    0
                );
                collisionOffset = new Point(
                    0,
                    0
                );
                positionOffset = new Point(
                    resourceBounds.width / 2,
                    resourceBounds.height / 2
                );
                break;
            default:
                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        // check bounds
        if (collisionSpace.getX() + collisionSpace.getWidth() - collisionOffset.getX() > bounds.getWidth()) {
            finalPosition.x = (int) (bounds.getWidth() - positionOffset.getX());
        }
        if (collisionSpace.getY() + collisionSpace.getHeight() - collisionOffset.getY() > bounds.getHeight()) {
            finalPosition.y = (int) (bounds.getHeight() - positionOffset.getY());
        }
        if (collisionSpace.getX() - collisionOffset.getX() < bounds.getX()) {
            finalPosition.x = (int) (bounds.getX() - positionOffset.getX());
        }
        if (finalPosition.getY() - collisionOffset.getY() < bounds.getY()) {
            finalPosition.y = (int) (bounds.getY() - positionOffset.getY());
        }
    }
}
