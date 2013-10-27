package de.mirkosertic.gameengine.physics;

import de.mirkosertic.gameengine.core.*;

import java.awt.event.KeyEvent;

public class PlatformComponent implements GameComponent {

    private GameRuntime gameRuntime;
    private GameObjectInstance objectInstance;
    private boolean jumping;

    PlatformComponent(GameObjectInstance aObjectInstance, GameRuntime aGameRuntime) {
        gameRuntime = aGameRuntime;
        objectInstance = aObjectInstance;
    }

    public void handleKeyPressed(KeyPressedGameEvent aEvent) {
        if (aEvent.getKeyCode() == GameKeyCode.LEFT) {
            gameRuntime.getEventManager().fire(new ApplyForceToGameObjectInstance(objectInstance, -9f, 0));
        }
        if (aEvent.getKeyCode() == GameKeyCode.RIGHT) {
            gameRuntime.getEventManager().fire(new ApplyForceToGameObjectInstance(objectInstance, 9f, 0f));
        }
        if (aEvent.getKeyCode() == GameKeyCode.UP && !jumping) {
            gameRuntime.getEventManager().fire(new ApplyImpulseToGameObjectInstance(objectInstance, 0, 1.4f));
            jumping = true;
        }
    }

    public void handleKeyReleased(KeyReleasedGameEvent aEvent) {
        // Reset Acceleration to zero ?
    }

    public void handleCollision(GameObjectCollisionEvent aEvent) {
        GameObjectInstance theOtherInstance = aEvent.getOtherInstanceOrNullIfNotAffected(objectInstance);
        if (theOtherInstance != null && theOtherInstance.getComponent(StaticComponent.class) != null) {
            double my = theOtherInstance.getPosition().getY() - objectInstance.getPosition().getY();
            if (my > 0) {
                // Object is under the game object
                jumping = false;
            } else {
                double mx = theOtherInstance.getPosition().getX() - objectInstance.getPosition().getX();
                // Object is on the right side
                if (mx > 0) {
                    jumping = false;
                }
                // Object is on the left side
                if (mx < 0) {
                    jumping = false;
                }
            }
        }
    }
}