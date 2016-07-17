package info.masterfrog.pixelcat.engine.logic.gameobject;

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
        updateAnimation(gameObject, kernelState);
    }

    private void updateForHIDEventGameLogicBindings(GameObject gameObject, KernelState kernelState) throws TransientGameException {
        // validate
        if (!gameObject.isFeatureAvailable(BehaviorBindingLibrary.class)) {
            return;
        }

        // setup
        Map<String, BehaviorBindingImpl<HIDEventEnum>> hidBehaviorBindings = gameObject.getFeature(BehaviorBindingLibrary.class).getAll();
        HashSet<HIDEventEnum> triggeredHIDEvents = kernelState.getHIDEvents();

        // update object based on HID events
        for (BehaviorBindingImpl<HIDEventEnum> hidBehaviorBinding : hidBehaviorBindings.values()) {
            // setup
            HIDEventEnum boundHIDEvent = hidBehaviorBinding.getBoundEvent();
            Behavior behavior = hidBehaviorBinding.getBehavior();

            // check that bound event was triggered
            if (!triggeredHIDEvents.contains(boundHIDEvent)) {
                continue;
            }

            // check that behavior can be triggered (i.e. not in a cool down period, etc.)
            if (!hidBehaviorBinding.canBehaviorBeTriggered()) {
                continue;
            }

            // if so, handle its update action
            switch (behavior.getBehaviorType()) {
                case MOVE_UP:
                case MOVE_DOWN:
                case MOVE_LEFT:
                case MOVE_RIGHT:
                    move(gameObject, kernelState, behavior);
                    break;
                case ANIMATION_PLAY:
                    if (!gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
                    }
                    gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent().play();
                    break;
                case ANIMATION_STOP:
                    if (!gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
                    }
                    gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent().pause();
                    break;
                case SOUND_PLAY:
                    break;
                case SOUND_PAUSE:
                    break;
                case SOUND_STOP:
                    break;
                case CAMERA_SWITCH:
                    // validate
                    if (!gameObject.isFeatureAvailable(CameraLibrary.class)) {
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "CameraLibrary feature not supported when expected", gameObject);
                    }

                    // generate dynamic behavior parameter if appropriate
                    BehaviorParameter dynamicBehaviorParameter = null;
                    if (behavior.hasBehaviorParameter(BehaviorParameterGenerator.class)) {
                        BehaviorParameterGenerator generator = (BehaviorParameterGenerator) behavior.getBehaviorParameter(BehaviorParameterGenerator.class);
                        dynamicBehaviorParameter = generator.getGeneratorDefinition().generateBehaviorParameter(generator.getInputs());
                    }

                    // derive behavior parameter for camera
                    BehaviorParameterId cameraParameter;
                    if (dynamicBehaviorParameter != null && dynamicBehaviorParameter instanceof BehaviorParameterId) {
                        cameraParameter = (BehaviorParameterId) dynamicBehaviorParameter;
                    } else if (behavior.hasBehaviorParameter(BehaviorParameterId.class)) {
                        cameraParameter = (BehaviorParameterId) behavior.getBehaviorParameter(BehaviorParameterId.class);
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
                        if (!gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
                            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "AnimationSequenceLibrary feature not supported when expected", gameObject);
                        }

                        // stop old animation
                        gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent().pause();

                        // assign new animation sequence
                        gameObject.getFeature(AnimationSequenceLibrary.class).setCurrent(camera.getView());

                        // fetch new resource ID
                        resourceId = gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent().getCurrentCel();

                        // play new animation
                        gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent().play();
                    } else if (camera.getType().equals(Resource.class)){
                        // fetch new resource ID
                        resourceId = camera.getView();
                    } else {
                        // unsupported case
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Unsupported camera type", gameObject);
                    }

                    // validate
                    if (!gameObject.isFeatureAvailable(ResourceLibrary.class)) {
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "ResourceLibrary feature not supported when expected", gameObject);
                    }

                    // assign new resource
                    gameObject.getFeature(ResourceLibrary.class).setCurrent(resourceId);
                    break;
                case LOGIC_ROUTINE:
                    // validate
                    if (!behavior.hasBehaviorParameter(BehaviorParameterLogicRoutine.class)) {
                        throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Logic routine not found for behavior binding", gameObject);
                    }

                    // fetch logic routine
                    BehaviorParameterLogicRoutine logicRoutine = (BehaviorParameterLogicRoutine) behavior.getBehaviorParameter(
                        BehaviorParameterLogicRoutine.class
                    );

                    // execute logic routine
                    logicRoutine.getLogicRoutineDefinition().execute(logicRoutine.getInputs());
                    break;
            }

            // record trigger event for hid behavior binding
            hidBehaviorBinding.recordTriggerEvent();
        }
    }

    private void updateAnimation(GameObject gameObject, KernelState kernelState) throws TransientGameException {
        // precondition check
        if (!gameObject.isFeatureAvailable(AnimationSequenceLibrary.class)) {
            return;
        }

        // setup
        AnimationSequence animationSequence = gameObject.getFeature(AnimationSequenceLibrary.class).getCurrent();

        // step the animation forward as needed
        animationSequence.advanceSequenceByTime();
    }

    private void move(GameObject gameObject, KernelState kernelState, Behavior behavior)
            throws TransientGameException {
        // setup
        BehaviorParameterMagnitude magnitudeParameter;
        Double xVel = 0.0, yVel = 0.0;

        // derive move magnitude
        Double magnitude;
        try {
            magnitudeParameter = (BehaviorParameterMagnitude) behavior.getBehaviorParameter(
                BehaviorParameterMagnitude.class
            );
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
