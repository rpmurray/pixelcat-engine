package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventBinder;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.clock.GameClockFactory;
import com.rpm.pixelcat.engine.logic.clock.GameClockManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class KernelStateImpl implements KernelState {
    private HashSet<HIDEventEnum> hidEvents;
    private HashSet<KernelActionEnum> kernelActions;
    private HashSet<Exception> errors;
    private HashMap<KernelStatePropertyEnum, Object> properties;
    private Rectangle bounds;
    private GameClockManager masterGameClockManager;

    KernelStateImpl() {
        this.hidEvents = new HashSet<>();
        this.kernelActions = new HashSet<>();
        this.errors = new HashSet<>();
        this.properties = new HashMap<>();
        this.masterGameClockManager = GameClockFactory.getInstance().createGameClockManager();
    }

    void init(HashMap<KernelStatePropertyEnum, Object> initProperties) throws GameException {
        // create master game clocks
        masterGameClockManager.addGameClock(MASTER_GAME_CLOCK);
        masterGameClockManager.addGameClock(LOOP_GAME_CLOCK);

        // set properties
        for (KernelStatePropertyEnum propertyKey : KernelStatePropertyEnum.values()) {
            // setup
            Object propertyValue;

            // get defaults
            HashMap<KernelStatePropertyEnum, Object> defaultProperties = getDefaultProperties();

            // determine property value
            if (initProperties.containsKey(propertyKey)) {
                propertyValue = initProperties.get(propertyKey);
            } else if (defaultProperties.containsKey(propertyKey)) {
                propertyValue = defaultProperties.get(propertyKey);
            } else {
                // skip cases that don't have a defined init or default value
                continue;
            }

            // set property
            setProperty(propertyKey, propertyValue);
        }
    }

    HashMap<KernelStatePropertyEnum, Object> getDefaultProperties() {
        // init
        HashMap<KernelStatePropertyEnum, Object> defaultProperties = new HashMap<>();

        // define defaults
        defaultProperties.put(KernelStatePropertyEnum.SCREEN_BOUNDS, getDefaultScreenBounds());
        defaultProperties.put(KernelStatePropertyEnum.FRAME_RATE, 60);
        defaultProperties.put(KernelStatePropertyEnum.FONT_DISPLAY_ENABLED, false);
        defaultProperties.put(KernelStatePropertyEnum.LOG_LVL, Printer.getLogLevelWarn());
        defaultProperties.put(KernelStatePropertyEnum.HID_EVENT_BINDER, HIDEventBinder.create());
        defaultProperties.put(KernelStatePropertyEnum.KERNEL_ACTION_BINDER, KernelActionBinder.create());
        defaultProperties.put(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS, new ArrayList<>());

        return defaultProperties;
    }

    private Rectangle getDefaultScreenBounds() {
        // set up screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0, 0, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 400);

        return screenBounds;
    }

    public void addHIDEvent(HIDEventEnum hidEvent) {
        hidEvents.add(hidEvent);
    }

    public void removeHIDEvent(HIDEventEnum hidEvent) {
        hidEvents.remove(hidEvent);
    }

    public void clearHIDEvents() {
        hidEvents.clear();
    }

    public void resetTransientHIDEvents() {
        // wipe out scroll up and scroll down which don't have a "reset" listener event
        hidEvents.remove(HIDEventEnum.SCROLL_UP);
        hidEvents.remove(HIDEventEnum.SCROLL_DOWN);
    }

    public Boolean hasHIDEvent(HIDEventEnum hidEvent) {
        return hidEvents.contains(hidEvent);
    }

    public HashSet<HIDEventEnum> getHIDEvents() {
        return hidEvents;
    }

    public void addKernelAction(KernelActionEnum kernelAction) {
        kernelActions.add(kernelAction);
    }

    public void removeKernelAction(KernelActionEnum kernelAction) {
        kernelActions.remove(kernelAction);
    }

    public void clearKernelActions() {
        kernelActions.clear();
    }

    public void resetTransientKernelActions() {
        // none to reset
    }

    public Boolean hasKernelAction(KernelActionEnum kernelAction) {
        return kernelActions.contains(kernelAction);
    }

    public HashSet<KernelActionEnum> getKernelActions() {
        return kernelActions;
    }

    public void addError(Exception exception) {
        errors.add(exception);
    }

    public void clearErrors() {
        errors.clear();
    }

    public HashSet<Exception> getErrors() {
        return errors;
    }

    public void setProperty(KernelStatePropertyEnum name, Object value) throws GameException {
        // property-specific handling
        switch (name) {
            case GAME_LOOP_DURATION_NS:
                // do not allow these cases to be set manually
                throw new GameException(GameErrorCode.LOGIC_ERROR);
            case FRAME_RATE:
                // validate
                if ((Integer) value > 1000) {
                    throw new GameException(GameErrorCode.LOGIC_ERROR);
                }

                // set loop time
                properties.put(KernelStatePropertyEnum.GAME_LOOP_DURATION_NS, 1000000000 / (Integer) value);
                break;
            case LOG_LVL:
                // validate input
                if (!(Printer.getLogLevelClass().isInstance(value))) {
                    throw new GameException(GameErrorCode.LOGIC_ERROR);
                }

                // update logging level in printer
                Printer.setLevel(Printer.getLogLevelClass().cast(value));
                break;
            default:
                // do nothing special
                break;
        }

        // save property value
        properties.put(name, value);
    }

    public Object getProperty(KernelStatePropertyEnum name) {
        Object value = null;
        if (properties.containsKey(name)) {
            value = properties.get(name);
        }

        return value;
    }

    public Boolean getPropertyFlag(KernelStatePropertyEnum name) {
        Boolean value = false;
        if (properties.containsKey(name)) {
            value = (Boolean) properties.get(name);
        }

        return value;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public GameClockManager getMasterGameClockManager() {
        return masterGameClockManager;
    }
}
