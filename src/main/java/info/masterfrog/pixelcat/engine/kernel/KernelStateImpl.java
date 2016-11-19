package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.common.util.MapBuilder;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventBinder;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockFactory;
import info.masterfrog.pixelcat.engine.logic.clock.GameClockManager;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KernelStateImpl implements KernelState, KernelStateCore {
    private static KernelStateImpl instance;

    private HashSet<HIDEvent> hidTriggeredEvents;
    private HashSet<HIDEvent> hidSustainedEvents;
    private HashSet<KernelAction> kernelActions;
    private HashSet<Exception> terminalErrors;
    private HashSet<Exception> transientErrors;
    private HashMap<KernelStateProperty, Object> properties;
    private GameClockManager masterGameClockManager;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelStateImpl.class);

    private KernelStateImpl() {
        this.hidTriggeredEvents = new HashSet<>();
        this.hidSustainedEvents = new HashSet<>();
        this.kernelActions = new HashSet<>();
        this.terminalErrors = new HashSet<>();
        this.transientErrors = new HashSet<>();
        this.properties = new HashMap<>();
        this.masterGameClockManager = GameClockFactory.getInstance().createGameClockManager();
    }

    static KernelStateImpl getInstance() {
        if (instance == null) {
            instance = new KernelStateImpl();
        }

        return instance;
    }

    public void init(HashMap<KernelStateProperty, Object> initProperties) throws TerminalErrorException {
        // create master game clocks
        masterGameClockManager.addAdvancedGameClock(KernelGameClock.MASTER_GAME_CLOCK);
        masterGameClockManager.addAdvancedGameClock(KernelGameClock.LOOP_GAME_CLOCK);

        try {
            // set properties
            for (KernelStateProperty propertyKey : KernelStateProperty.values()) {
                // setup
                Object propertyValue;

                // get defaults
                HashMap<KernelStateProperty, Object> defaultProperties = getDefaultProperties();


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

            // other property initialization
            this.<CanvasManager>getProperty(KernelStateProperty.CANVAS_MANAGER).add(
                Canvas.createFromKernel()
            );
        } catch (Exception e) {
            Set exceptionSet = new HashSet<>();
            exceptionSet.add(e);
            throw new TerminalErrorException(exceptionSet);
        }
    }

    private HashMap<KernelStateProperty, Object> getDefaultProperties() throws TransientGameException {
        // define defaults
        HashMap defaultProperties = new MapBuilder<HashMap, KernelStateProperty, Object>(HashMap.class).add(
            KernelStateProperty.SCREEN_BOUNDS, getDefaultScreenBounds()
        ).add(
            KernelStateProperty.BACKGROUND_COLOR, Color.WHITE
        ).add(
            KernelStateProperty.FRAME_RATE, 30
        ).add(
            KernelStateProperty.FONT_DISPLAY_ENABLED, false
        ).add(
            KernelStateProperty.LOG_LVL, Printer.getLogLevelWarn()
        ).add(
            KernelStateProperty.HID_EVENT_BINDER, HIDEventBinder.create()
        ).add(
            KernelStateProperty.KERNEL_ACTION_BINDER, KernelActionBinder.create()
        ).add(
            KernelStateProperty.ACTIVE_GAME_OBJECT_MANAGERS, new ArrayList<>()
        ).add(
            KernelStateProperty.CANVAS_MANAGER, CanvasManagerFactory.create()
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
        // set up bounds
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0, 0, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 400);

        return screenBounds;
    }

    public void addHIDTriggeredEvent(HIDEvent hidEvent) {
        hidTriggeredEvents.add(hidEvent);
    }

    public void removeHIDEvent(HIDEvent hidEvent) {
        if (hasHIDTriggeredEvent(hidEvent)) {
            removeHIDTriggeredEvent(hidEvent);
        }
        if (hasHIDSustainedEvent(hidEvent)) {
            removeHIDSustainedEvent(hidEvent);
        }
    }

    public void removeHIDTriggeredEvent(HIDEvent hidEvent) {
        hidTriggeredEvents.remove(hidEvent);
    }

    public void removeHIDSustainedEvent(HIDEvent hidEvent) {
        hidSustainedEvents.remove(hidEvent);
    }

    public void sustainHIDEvents() {
        hidSustainedEvents.addAll(hidTriggeredEvents);
        hidTriggeredEvents.clear();
    }

    public void resetTransientHIDEvents() {
        // wipe out scroll up and scroll down which don't have a "reset" listener event
        removeHIDEvent(HIDEvent.SCROLL_UP);
        removeHIDEvent(HIDEvent.SCROLL_DOWN);
    }

    public Boolean hasHIDTriggeredEvent(HIDEvent hidEvent) {
        return hidTriggeredEvents.contains(hidEvent);
    }

    public Boolean hasHIDSustainedEvent(HIDEvent hidEvent) {
        return hidSustainedEvents.contains(hidEvent);
    }

    public HashSet<HIDEvent> getHIDTriggeredEvents() {
        return hidTriggeredEvents;
    }

    public HashSet<HIDEvent> getHIDSustainedEvents() {
        return hidSustainedEvents;
    }

    public void addKernelAction(KernelAction kernelAction) {
        kernelActions.add(kernelAction);
    }

    public void removeKernelAction(KernelAction kernelAction) {
        kernelActions.remove(kernelAction);
    }

    public void clearKernelActions() {
        kernelActions.clear();
    }

    public void resetTransientKernelActions() {
        // none to reset
    }

    public Boolean hasKernelAction(KernelAction kernelAction) {
        return kernelActions.contains(kernelAction);
    }

    public HashSet<KernelAction> getKernelActions() {
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

    public void setProperty(KernelStateProperty name, Object value) throws TransientGameException {
        // property-specific handling
        switch (name) {
            case GAME_LOOP_DURATION_NS:
                // do not allow these cases to be set manually
                throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
            case CANVAS_MANAGER:
                // do not allow these cases to be set manually after they have been set once
                if (getProperty(name) != null) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                }
                break;
            case BACKGROUND_COLOR:
                // validate
                if (!(value instanceof Color)) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                }
                break;
            case FRAME_RATE:
                // validate
                if ((Integer) value > 1000) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                }

                // set loop time
                properties.put(KernelStateProperty.GAME_LOOP_DURATION_NS, 1000000000 / (Integer) value);
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

    public <T> T getProperty(KernelStateProperty name) {
        T value = null;
        if (properties.containsKey(name)) {
            value = (T) properties.get(name);
        }

        return value;
    }

    public Boolean getPropertyFlag(KernelStateProperty name) {
        Boolean value = false;
        if (properties.containsKey(name)) {
            value = (Boolean) properties.get(name);
        }

        return value;
    }

    public GameClockManager getMasterGameClockManager() {
        return masterGameClockManager;
    }
}
