package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.common.util.MapBuilder;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventBinder;
import info.masterfrog.pixelcat.engine.hid.HIDEventEnum;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockFactory;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockManager;

import java.awt.*;
import java.util.*;

class KernelStateImpl implements KernelState {
    private HashSet<HIDEventEnum> hidTriggeredEvents;
    private HashSet<HIDEventEnum> hidSustainedEvents;
    private HashSet<KernelActionEnum> kernelActions;
    private HashSet<Exception> terminalErrors;
    private HashSet<Exception> transientErrors;
    private HashMap<KernelStatePropertyEnum, Object> properties;
    private Rectangle bounds;
    private GameClockManager masterGameClockManager;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelStateImpl.class);

    KernelStateImpl() {
        this.hidTriggeredEvents = new HashSet<>();
        this.hidSustainedEvents = new HashSet<>();
        this.kernelActions = new HashSet<>();
        this.terminalErrors = new HashSet<>();
        this.transientErrors = new HashSet<>();
        this.properties = new HashMap<>();
        this.masterGameClockManager = GameClockFactory.getInstance().createGameClockManager();
    }

    void init(HashMap<KernelStatePropertyEnum, Object> initProperties) throws TerminalErrorException {
        // create master game clocks
        masterGameClockManager.addAdvancedGameClock(MASTER_GAME_CLOCK);
        masterGameClockManager.addAdvancedGameClock(LOOP_GAME_CLOCK);

        try {
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
        } catch (Exception e) {
            Set exceptionSet = new HashSet<>();
            exceptionSet.add(e);
            throw new TerminalErrorException(exceptionSet);
        }
    }

    private HashMap<KernelStatePropertyEnum, Object> getDefaultProperties() throws TransientGameException {
        // define defaults
        HashMap defaultProperties = new MapBuilder<HashMap, KernelStatePropertyEnum, Object>(HashMap.class).add(
            KernelStatePropertyEnum.SCREEN_BOUNDS, getDefaultScreenBounds()
        ).add(
            KernelStatePropertyEnum.FRAME_RATE, 30
        ).add(
            KernelStatePropertyEnum.FONT_DISPLAY_ENABLED, false
        ).add(
            KernelStatePropertyEnum.LOG_LVL, Printer.getLogLevelWarn()
        ).add(
            KernelStatePropertyEnum.HID_EVENT_BINDER, HIDEventBinder.create()
        ).add(
            KernelStatePropertyEnum.KERNEL_ACTION_BINDER, KernelActionBinder.create()
        ).add(
            KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS, new ArrayList<>()
        ).get();

        temp(new MapBuilder<HashMap, Integer, Kernel>(HashMap.class).add(
            1, new KernelImpl()
        ).get());

        return defaultProperties;
    }

    private void temp(Map<Integer, Kernel> foo) {
        foo.clear();
    }

    private Rectangle getDefaultScreenBounds() {
        // set up screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0, 0, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 400);

        return screenBounds;
    }

    public void addHIDTriggeredEvent(HIDEventEnum hidEvent) {
        hidTriggeredEvents.add(hidEvent);
    }

    public void removeHIDEvent(HIDEventEnum hidEvent) {
        if (hasHIDTriggeredEvent(hidEvent)) {
            removeHIDTriggeredEvent(hidEvent);
        }
        if (hasHIDSustainedEvent(hidEvent)) {
            removeHIDSustainedEvent(hidEvent);
        }
    }

    public void removeHIDTriggeredEvent(HIDEventEnum hidEvent) {
        hidTriggeredEvents.remove(hidEvent);
    }

    public void removeHIDSustainedEvent(HIDEventEnum hidEvent) {
        hidSustainedEvents.remove(hidEvent);
    }

    void sustainHIDEvents() {
        hidSustainedEvents.addAll(hidTriggeredEvents);
        hidTriggeredEvents.clear();
    }

    void resetTransientHIDEvents() {
        // wipe out scroll up and scroll down which don't have a "reset" listener event
        removeHIDEvent(HIDEventEnum.SCROLL_UP);
        removeHIDEvent(HIDEventEnum.SCROLL_DOWN);
    }

    public Boolean hasHIDTriggeredEvent(HIDEventEnum hidEvent) {
        return hidTriggeredEvents.contains(hidEvent);
    }

    public Boolean hasHIDSustainedEvent(HIDEventEnum hidEvent) {
        return hidSustainedEvents.contains(hidEvent);
    }

    public HashSet<HIDEventEnum> getHIDTriggeredEvents() {
        return hidTriggeredEvents;
    }

    public HashSet<HIDEventEnum> getHIDSustainedEvents() {
        return hidSustainedEvents;
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

    public void addTerminalError(Exception exception) {
        terminalErrors.add(exception);
    }

    public Boolean hasTerminalErrors() {
        return terminalErrors.size() > 0;
    }

    public HashSet<Exception> getTerminalErrors() {
        return terminalErrors;
    }

    public void addTransientError(Exception exception) {
        transientErrors.add(exception);
    }

    public void clearTransientErrors() {
        transientErrors.clear();
    }

    public Boolean hasTransientErrors() {
        return transientErrors.size() > 0;
    }

    public HashSet<Exception> getTransientErrors() {
        return transientErrors;
    }

    public void setProperty(KernelStatePropertyEnum name, Object value) throws TransientGameException {
        // property-specific handling
        switch (name) {
            case GAME_LOOP_DURATION_NS:
                // do not allow these cases to be set manually
                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
            case FRAME_RATE:
                // validate
                if ((Integer) value > 1000) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                }

                // set loop time
                properties.put(KernelStatePropertyEnum.GAME_LOOP_DURATION_NS, 1000000000 / (Integer) value);
                break;
            case LOG_LVL:
                // validate input
                if (!(Printer.getLogLevelClass().isInstance(value))) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
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
