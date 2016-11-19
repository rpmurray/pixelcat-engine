package info.masterfrog.pixelcat.engine.hid;

import info.masterfrog.pixelcat.engine.common.binding.BindableEvent;

import java.util.HashMap;

public enum HIDEvent implements BindableEvent {
    PRIMARY_UP,
    PRIMARY_DOWN,
    PRIMARY_LEFT,
    PRIMARY_RIGHT,
    PRIMARY_NO_DIRECTION,
    CLICK_LEFT,
    CLICK_MIDDLE,
    CLICK_RIGHT,
    SCROLL_UP,
    SCROLL_DOWN,
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ESC,
    ENTER,
    SPACE,
    DELETE,
    ;

    HashMap<String, Object> properties;

    HIDEvent() {
        properties = new HashMap<>();
    }

    public Object value(String key) {
        return properties.get(key);
    }

    public void value(String key, Object value) {
        properties.put(key, value);
    }
}
