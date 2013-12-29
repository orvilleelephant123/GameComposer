package de.mirkosertic.gameengine.input;

import de.mirkosertic.gameengine.types.GameKeyCode;
import de.mirkosertic.gameengine.event.GameEvent;
import de.mirkosertic.gameengine.event.Property;
import de.mirkosertic.gameengine.event.ReadOnlyProperty;

public class KeyPressed extends GameEvent {

    private final Property<GameKeyCode> keyCode;

    KeyPressed(GameKeyCode aKeyCode) {
        super("KeyPressed");
        keyCode = registerProperty(new Property<GameKeyCode>(this, "keyCode", aKeyCode));
    }

    public ReadOnlyProperty<GameKeyCode> keyCodeProperty() {
        return keyCode;
    }
}