package info.masterfrog.pixelcat.engine.logic.gameobject;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;
import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventEnum;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStatePropertyEnum;
import info.masterfrog.pixelcat.engine.logic.animation.AnimationSequence;
import info.masterfrog.pixelcat.engine.logic.camera.Camera;
import info.masterfrog.pixelcat.engine.logic.gameobject.behavior.*;
import info.masterfrog.pixelcat.engine.logic.gameobject.feature.*;
import info.masterfrog.pixelcat.engine.logic.physics.screen.ScreenBoundsHandlingTypeEnum;
import info.masterfrog.pixelcat.engine.logic.resource.MeasurableResource;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;

import java.awt.*;
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

    void update(GameObject gameObject, KernelState kernelState) throws TransientGameException {
        updateForHIDEventGameLogicBindings(gameObject, kernelState);
        if (gameObject.hasFeature(AnimationSequenceLibrary.class)) {
            updateAnimation(gameObject, kernelState);
        }
    }

    private void updateForHIDEventGameLogicBindings(GameObject gameObject, KernelState kernelState) throws TransientGameException {
        // validate
        if (!gameObject.isFeatureAvailable(BehaviorBindingLibrary.class)) {
            return;
        }

        // setup
        Map<String, BehaviorBindingImpl<BindableEvent>> behaviorBindings = gameObject.getFeature(BehaviorBindingLibrary.class).getAll();
        HashSet<HIDEventEnum> triggeredHIDEvents = kernelState.getHIDTriggeredEvents();
        HashSet<HIDEventEnum> sustainedHIDEvents = kernelState.getHIDSustainedEvents();

        // update object based on HID events
        for (BehaviorBindingImpl<BindableEvent> behaviorBinding : behaviorBindings.values()) {
            // setup
            Behavior behavior = behaviorBinding.getBehavior();

            if (behaviorBinding.getBoundEvent() instanceof HIDEventEnum) {
                // setup
                HIDEventEnum boundHIDEvent = (HIDEventEnum) behaviorBinding.getBoundEvent();

                // check that bound event was triggered
                if (!triggeredHIDEvents.contains(boundHIDEvent)) {
                    continue;
                }
            } else {
                throw new TransientGameException(GameErrorCode.UNSUPPORTED_FUNCTIONALITY, "Behavior bindings only supported for HID Events");
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
                            move(gameObject, kernelState, behavior);
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
                    gameObject.verifyFeatureIsAvailable(SoundLibrary.class);

                    // get sound resource
                    SoundResource soundResource = gameObject.getFeature(SoundLibrary.class).get(
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
                                throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Camera parameter not found for camera switch behavior binding", gameObject);
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
                                throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Unsupported camera type", gameObject);
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
                                throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Logic routine not found for behavior binding", gameObject);
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

    private void updateAnimation(GameObject gameObject, KernelState kernelState) throws TransientGameException {
        // precondition check
        gameObject.verifyFeatureIsAvailable(AnimationSequenceLibrary.class);

        // setup
        AnimationSequence animationSequence = gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent();

        // step the animation forward as needed
        animationSequence.advanceSequenceByTime();
    }

    private void move(GameObject gameObject, KernelState kernelState, Behavior behavior)
            throws TransientGameException {
        // validate
        gameObject.verifyFeatureIsAvailable(Renderable.class);
        gameObject.verifyFeatureIsAvailable(PhysicsBindingSet.class);

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
        Point initialPosition = gameObject.getFeature(Renderable.class).getPosition();
        Point finalPosition = new Point(
            (int) (initialPosition.getX() + xVel),
            (int) (initialPosition.getY() + yVel)
        );

        // screen bounds checking
        handleScreenBounds(gameObject, kernelState, finalPosition);

        // debug
        PRINTER.printTrace(
            " SB: " + kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS) +
                " GO_IP: " + initialPosition +
                " GO_FP: " + finalPosition +
                " SBH:" + gameObject.getFeature(PhysicsBindingSet.class).get(ScreenBoundsHandlingTypeEnum.class)
        );

        // set position
        gameObject.getFeature(Renderable.class).setPosition(finalPosition);
    }

    private void handleScreenBounds(GameObject gameObject, KernelState kernelState, Point finalPosition)
            throws TransientGameException {
        // validate
        gameObject.verifyFeatureIsAvailable(Renderable.class);

        // setup
        Resource resource = gameObject.getFeature(Renderable.class).getRenderableResource(gameObject);
        Rectangle resourceBounds;
        Rectangle screenBounds = ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS));
        Rectangle collisionSpace;
        Point collisionOffset;
        Point positionOffset;

        // check that resource is measurable
        if (resource instanceof MeasurableResource) {
            resourceBounds = ((MeasurableResource) resource).getCelSize();
        } else {
            return;
        }

        // validate
        gameObject.verifyFeatureIsAvailable(PhysicsBindingSet.class);

        // check if screen bounds handling is not defined
        if (!gameObject.isFeatureAvailable(PhysicsBindingSet.class) ||
            !gameObject.getFeature(PhysicsBindingSet.class).has(ScreenBoundsHandlingTypeEnum.class)) {
            return;
        }

        // determine checkable position of resource
        switch (gameObject.getFeature(PhysicsBindingSet.class).get(ScreenBoundsHandlingTypeEnum.class)) {
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
                throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        // check bounds
        if (collisionSpace.getX() + collisionSpace.getWidth() - collisionOffset.getX() > screenBounds.getWidth()) {
            finalPosition.x = (int) (screenBounds.getWidth() - positionOffset.getX());
        }
        if (collisionSpace.getY() + collisionSpace.getHeight() - collisionOffset.getY() > screenBounds.getHeight()) {
            finalPosition.y = (int) (screenBounds.getHeight() - positionOffset.getY());
        }
        if (collisionSpace.getX() - collisionOffset.getX() < screenBounds.getX()) {
            finalPosition.x = (int) (screenBounds.getX() - positionOffset.getX());
        }
        if (finalPosition.getY() - collisionOffset.getY() < screenBounds.getY()) {
            finalPosition.y = (int) (screenBounds.getY() - positionOffset.getY());
        }
    }
}
