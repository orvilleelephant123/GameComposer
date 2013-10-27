package de.mirkosertic.gameengine.physics;

import de.mirkosertic.gameengine.core.GameComponentFactory;
import de.mirkosertic.gameengine.core.GameObjectInstance;
import de.mirkosertic.gameengine.core.GameRuntime;

public class StaticComponentFactory implements GameComponentFactory<StaticComponent> {

    public StaticComponent create(GameObjectInstance aInstance, GameRuntime aGameRuntime) {
        return new StaticComponent(aInstance, aGameRuntime);
    }
}