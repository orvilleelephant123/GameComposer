package de.mirkosertic.gameengine.physics;

import java.util.HashMap;
import java.util.Map;

import de.mirkosertic.gameengine.core.GameComponentTemplate;
import de.mirkosertic.gameengine.core.GameObject;
import de.mirkosertic.gameengine.core.GameObjectInstance;
import de.mirkosertic.gameengine.core.GameRuntime;
import de.mirkosertic.gameengine.event.GameEventManager;
import de.mirkosertic.gameengine.event.Property;

public class PhysicsComponentTemplate extends GameComponentTemplate<PhysicsComponent> implements Physics {

    private final GameObject owner;

    private final Property<Boolean> active;
    private final Property<Boolean> fixedRotation;
    private final Property<Float> density;
    private final Property<Float> friction;
    private final Property<Float> restitution;
    private final Property<Float> gravityScale;

    public PhysicsComponentTemplate(GameEventManager aEventManager, GameObject aOwner) {
        owner = aOwner;

        active = registerProperty(new Property<Boolean>(this, ACTIVE_PROPERTY, Boolean.TRUE, aEventManager));
        fixedRotation = registerProperty(new Property<Boolean>(this, FIXED_ROTATION_PROPERTY, Boolean.FALSE, aEventManager));
        density = registerProperty(new Property<Float>(this, DENSITY_PROPERTY, 1f, aEventManager));
        friction = registerProperty(new Property<Float>(this, FRICTION_PROPERTY, 1.8f, aEventManager));
        restitution = registerProperty(new Property<Float>(this, RESTITUTION_PROPERTY, 0f, aEventManager));
        gravityScale = registerProperty(new Property<Float>(this, GRAVITY_SCALE_PROPERTY, 1f, aEventManager));
    }

    @Override
    public GameObject getOwner() {
        return owner;
    }

    public Property<Boolean> activeProperty() {
        return active;
    }

    public Property<Boolean> fixedRotationProperty() {
        return fixedRotation;
    }

    public Property<Float> densityProperty() {
        return density;
    }

    public Property<Float> frictionProperty() {
        return friction;
    }

    public Property<Float> restitutionProperty() {
        return restitution;
    }

    public Property<Float> gravityScaleProperty() {
        return gravityScale;
    }

    public PhysicsComponent create(GameObjectInstance aInstance, GameRuntime aGameRuntime) {
        return new PhysicsComponent(aInstance, this);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> theResult = new HashMap<String, Object>();
        theResult.put(PhysicsComponent.TYPE_ATTRIBUTE, PhysicsComponent.TYPE);
        theResult.put(ACTIVE_PROPERTY, Boolean.toString(active.get()));
        theResult.put("fixedrotation", Boolean.toString(fixedRotation.get()));
        theResult.put(DENSITY_PROPERTY, Float.toString(density.get()));
        theResult.put(FRICTION_PROPERTY, Float.toString(friction.get()));
        theResult.put(RESTITUTION_PROPERTY, Float.toString(restitution.get()));
        theResult.put(GRAVITY_SCALE_PROPERTY, Float.toString(gravityScale.get()));
        return theResult;
    }

    public static PhysicsComponentTemplate deserialize(GameEventManager aEventManager, GameObject aOwner, Map<String, Object> aSerializedData) {
        PhysicsComponentTemplate theTemplate = new PhysicsComponentTemplate(aEventManager, aOwner);
        String theActive = (String) aSerializedData.get(ACTIVE_PROPERTY);
        if (theActive != null) {
            theTemplate.active.setQuietly(Boolean.parseBoolean(theActive));
        }
        String theFixedRotation = (String) aSerializedData.get("fixedrotation");
        if (theFixedRotation != null) {
            theTemplate.fixedRotation.setQuietly(Boolean.parseBoolean(theFixedRotation));
        }
        String theDensity = (String) aSerializedData.get(DENSITY_PROPERTY);
        if (theDensity != null) {
            theTemplate.density.setQuietly(Float.parseFloat(theDensity));
        }
        String theFriction = (String) aSerializedData.get(FRICTION_PROPERTY);
        if (theFriction != null) {
            theTemplate.friction.setQuietly(Float.parseFloat(theFriction));
        }
        String theRestitution = (String) aSerializedData.get(RESTITUTION_PROPERTY);
        if (theRestitution != null) {
            theTemplate.restitution.setQuietly(Float.parseFloat(theRestitution));
        }
        String theGravityScale = (String) aSerializedData.get(GRAVITY_SCALE_PROPERTY);
        if (theGravityScale != null) {
            theTemplate.gravityScale.setQuietly(Float.parseFloat(theGravityScale));
        }

        return theTemplate;
    }

    @Override
    public String getTypeKey() {
        return PhysicsComponent.TYPE;
    }
}