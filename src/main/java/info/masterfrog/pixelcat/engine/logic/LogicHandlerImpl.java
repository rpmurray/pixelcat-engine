package info.masterfrog.pixelcat.engine.logic;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.util.MapBuilder;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelAction;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.ResourceLibrary;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.manager.GameObjectManager;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class LogicHandlerImpl implements LogicHandler {
    private static LogicHandlerImpl instance;

    private LogicHandlerImpl() {
        // do nothing
    }

    static LogicHandlerImpl getInstance() {
        if (instance == null) {
            instance = new LogicHandlerImpl();
        }

        return instance;
    }

    public List<GameObject> getGameObjects() throws TransientGameException {
        // setup
        List<GameObject> consolidatedGameObjects = new ArrayList<>();

        // retrieve game object managers
        List<GameObjectManager> gameObjectManagers = KernelState.getInstance().getProperty(KernelStateProperty.ACTIVE_GAME_OBJECT_MANAGERS);

        // loop through each and consolidate
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            consolidatedGameObjects.addAll(gameObjectManager.getGameObjects().values());
        }

        return consolidatedGameObjects;
    }

    public List<List<GameObject>> getLayeredGameObjects() throws TransientGameException {
        // setup
        List<List<GameObject>> consolidatedLayeredGameObjects = new ArrayList<>();

        // retrieve game object managers
        List<GameObjectManager> gameObjectManagers = KernelState.getInstance().getProperty(KernelStateProperty.ACTIVE_GAME_OBJECT_MANAGERS);

        // loop through each and consolidate
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            // setup
            List<List<GameObject>> layeredGameObjects = gameObjectManager.getLayeredGameObjects();

            // iterate through the layers
            Integer i = 0;
            for (List<GameObject> gameObjects: layeredGameObjects) {
                // save game objects in appropriate layer
                if (consolidatedLayeredGameObjects.size() <= i || consolidatedLayeredGameObjects.get(i) == null) {
                    consolidatedLayeredGameObjects.add(i, gameObjects);
                } else {
                    consolidatedLayeredGameObjects.get(i).addAll(gameObjects);
                }

                // increment iterator
                i++;
            }
        }

        return consolidatedLayeredGameObjects;
    }

    public Set<SoundEngine.SoundEventActor> getSoundEvents() throws TransientGameException {
        Set<SoundEngine.SoundEventActor> soundEvents = new HashSet<>();

        // get all game objects
        List<GameObject> gameObjects = getGameObjects();

        // iterate through and find sound event capable objects
        for (GameObject gameObject : gameObjects) {
            if (gameObject.hasFeature(ResourceLibrary.class)) {
                // get sound library
                ResourceLibrary soundLibrary = ResourceLibrary.create();
                ResourceLibrary resourceLibrary = gameObject.getFeature(ResourceLibrary.class);
                for (String resourceId : resourceLibrary.getAll().keySet()) {
                    if (resourceLibrary.get(resourceId) instanceof SoundResource) {
                        soundLibrary.add(resourceLibrary.get(resourceId));
                    }
                }

                // iterate through all sound library elements
                for (String soundResourceId : soundLibrary.getAll().keySet()) {
                    // check each sound event actor for existence of actionable sound event states
                    // and add such sound event actors to our set of sound events
                    SoundEngine.SoundEventActor soundEventActor = (SoundResource) soundLibrary.get(soundResourceId);
                    if (soundEventActor.containsSoundEventStates()) {
                        soundEvents.add(soundEventActor);
                    }
                }
            }
        }

        return soundEvents;
    }

    public void process() throws TransientGameException, TerminalGameException, ExitException {
        // do logic checks
        checkExit();

        // do gameObjectManager handling
        List<GameObjectManager> gameObjectManagers = KernelState.getInstance().getProperty(KernelStateProperty.ACTIVE_GAME_OBJECT_MANAGERS);
        for (GameObjectManager gameObjectManager: gameObjectManagers) {
            gameObjectManager.process();
        }

        // update game state
        updateGameState();
    }

    private void checkExit() throws ExitException {
        if (KernelState.getInstance().hasKernelAction(KernelAction.EXIT)) {
            // trigger an exit
            throw new ExitException();
        }
    }

    private void updateGameState() throws TransientGameException {
        // handle logging
        handleLogging();

        // handle font display
        handleFontDisplay();
    }

    private void handleLogging() throws TransientGameException {
        // logging level
        setKernelStateProperty(
            KernelStateProperty.LOG_LVL,
            new MapBuilder<HashMap, KernelAction, Level>(HashMap.class).add(
                KernelAction.SET_LOG_LVL_FATAL, Printer.getLogLevelFatal()
            ).add(
                KernelAction.SET_LOG_LVL_ERROR, Printer.getLogLevelError()
            ).add(
                KernelAction.SET_LOG_LVL_WARN, Printer.getLogLevelWarn()
            ).add(
                KernelAction.SET_LOG_LVL_INFO, Printer.getLogLevelInfo()
            ).add(
                KernelAction.SET_LOG_LVL_DEBUG, Printer.getLogLevelDebug()
            ).add(
                KernelAction.SET_LOG_LVL_TRACE, Printer.getLogLevelTrace()
            ).get()
        );
    }

    private void handleFontDisplay() throws TransientGameException {
        // font debugger
        toggleKernelStateProperty(KernelAction.FONT_DEBUG_TOGGLE, KernelStateProperty.FONT_DISPLAY_ENABLED);
    }

    private void setKernelStateProperty(KernelStateProperty kernelStateProperty,
                                        Map<KernelAction, Object> kernelActionBindings)
                 throws TransientGameException {
        for (KernelAction kernelAction: kernelActionBindings.keySet()) {
            // setup
            Object kernelStatePropertyValue = kernelActionBindings.get(kernelAction);

            // check hid event and set kernel state property
            if (KernelState.getInstance().hasKernelAction(kernelAction)) {
                // remove kernel action so it isn't processed twice
                KernelState.getInstance().removeKernelAction(kernelAction);

                // set kernel state property
                KernelState.getInstance().setProperty(kernelStateProperty, kernelStatePropertyValue);
            }
        }
    }

    private void toggleKernelStateProperty(KernelAction kernelAction,
                                           KernelStateProperty kernelStateProperty)
                 throws TransientGameException {
        if (KernelState.getInstance().hasKernelAction(kernelAction)) {
            // remove kernel action so it isn't processed twice
            KernelState.getInstance().removeKernelAction(kernelAction);

            // toggle kernel state property
            if (KernelState.getInstance().getPropertyFlag(kernelStateProperty)) {
                KernelState.getInstance().setProperty(kernelStateProperty, false);
            } else {
                KernelState.getInstance().setProperty(kernelStateProperty, true);
            }
        }
    }
}
