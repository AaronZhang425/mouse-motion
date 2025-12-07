package eventclassification.eventcodes;

import eventclassification.EventCategory;
import java.util.HashMap;

public enum Key implements EventCategory {
    RESERVED(0),
    ESC(1),
    ONE(2),
    TWO(3),
    THREE(4),
    FOUR(5),
    FIVE(6),
    SIX(7),
    SEVEN(8),
    EIGHT(9),
    NINE(10),
    ZERO(11),
    MINUS(12),
    EQUAL(13),
    BACKSPACE(14),
    TAB(15),
    Q(16),
    W(17),
    E(18),
    R(19),
    T(20),
    Y(21),
    U(22),
    I(23),
    O(24),
    P(25),
    LEFTBRACE(26),
    RIGHTBRACE(27),
    ENTER(28),
    LEFTCTRL(29),
    A(),
    S(),
    D(),
    F(),
    G(),
    H(),
    J(),
    K(),
    L(),
    SEMICOLON(),
    APOSTROPHE(),
    GRAVE(),
    LEFTSHIFT(),
    BACKSLASH,
    Z(),
    X(),
    C(),
    V(),
    B(),
    N(),
    M(),
    COMMA(),
    DOT(),
    SLASH(),
    RIGHTSHIFT(),
    KPASTERISK(),
    LEFTALT(),
    SPACE(),
    CAPSLOCK(),
    F1(),
    F2(),
    F3(),
    F4(),
    F5(),
    F6(),
    F7(),
    F8(),
    F9(),
    F10(),
    NUMLOCK(),
    SCROLLLOCK(),
    KP7(),
    KP8(),
    KP9(),
    KPMINUS(),
    KP4(),
    KP5(),
    KP6(),
    KPPLUS(),
    KP1(),
    KP2(),
    KP3(),
    KP0(),
    KPDOT(),
    ZENKAKUHANKAKU(),
    ONE_HUNDRED_SECOND(),
    F11(),
    F12(),
    RO(),
    KATAKANA(),
    HIRAGANA(),
    HENKAN(),
    KATAKANAHIRAGANA(),
    MUHENKAN(),
    KPJPKANA();

    private final int value;
    private static final HashMap<Integer, Key> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Key eventCode : Key.values()) {
            VALUE_MAP.put(eventCode.getValue(), eventCode);

        }

    }

    private Key(int value) {
        this.value = value;
    }

    @Override
    public int getValue(){
        return value;

    }           

    public static Key fromValue(int value) {
        return (Key) VALUE_MAP.get(value);
    }


}
