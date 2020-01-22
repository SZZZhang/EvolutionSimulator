package Main.Creatures;

import Main.Game;
import Main.Terrain;
import gameEngine.ADT.TraitMap;
import gameEngine.GameEngine;
import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public abstract class Animal implements Creature {

    //distance that the creature must be within to be considered to be approximately in its target position
    private float CLOSE_RADIUS = 4;
    public final static String SPEED_TRAIT_NAME = "Speed";
    public final static String SENSORY_RADIUS_TRAIT_NAME = "Sensory Radius";
    public final static String SIZE_TRAIT_NAME = "Size";

    private TraitMap traits;

    private float speed; //
    private float sensoryRadius;
    private float maxHungerBar;
    private float nutritionalValue;
    private float currentHungerBar;

    private boolean hasTarget; //target is the position it is moving toward
    private boolean hasTargetCreature; //target is the position it is moving toward
    private Vector3f targetPosition;
    private float angleOfTarget;
    private Creature targetCreature;

    private Mesh mesh;
    private boolean isDead = false;

    public Animal(TraitMap traits, Mesh mesh, float nutritionalValue, float maxHungerBar) {
        this.speed = traits.getTrait(SPEED_TRAIT_NAME).getValue();
        this.sensoryRadius = traits.getTrait(SENSORY_RADIUS_TRAIT_NAME).getValue();
        this.maxHungerBar = maxHungerBar;
        this.nutritionalValue = nutritionalValue;
        this.mesh = mesh;
        this.currentHungerBar = maxHungerBar;
    }

    @Override
    public void update() {

        if (targetPosition != null && targetPosition.subtract(this.getGameObject().getPosition()).getMagnitude() <= CLOSE_RADIUS) {
            if (targetCreature != null && targetCreature.getGameObject().getPosition().subtract(this.getGameObject().getPosition()).getMagnitude() <= 1) {
                eat(targetCreature);
            }
            clearTarget();
        }

        move();

        float currentX = this.getGameObject().getPosition().getX();
        float currentZ = this.getGameObject().getPosition().getZ();
        if (!inBound(currentX, currentZ)) {
            clearTarget();
            findTarget();
        }
        //currentHungerBar -= size * speed; //makes creature more hungry
        if (currentHungerBar <= 0) this.setDeath(true);
    }

    public void move() {
        if (!hasTarget) findTarget();

        //TODO first rotate, then move
        //this.getGameObject().setRotation(new Vector3f(0, (float) Math.toDegrees(angleOfTarget), 0));

        //translation
        float deltaX = (float) (Math.sin(angleOfTarget) * speed * GameEngine.gameTimeIncreasePerSec);
        float deltaZ = (float) (Math.cos(angleOfTarget) * speed * GameEngine.gameTimeIncreasePerSec);

        this.getGameObject().setPosition(new Vector3f(
                this.getGameObject().getPosition().add(new Vector3f(deltaX, 0, deltaZ))));
    }

    public void eat(Creature creature) {
        currentHungerBar = Math.max(currentHungerBar + creature.getNutritionalValue(), maxHungerBar);
        creature.setDeath(true);
    }

    private void findTarget() {
        findCreatureTarget();

        if (!hasTarget) {
            findRandomTarget();
        }
    }

    private void clearTarget() {
        hasTarget = false;
        hasTargetCreature = false;
        targetCreature = null;
        targetPosition = null;
        angleOfTarget = 0;
    }

    private void findCreatureTarget() {
        float curTargetDistance = sensoryRadius + 1;
        Vector3f currentPosition = this.getGameObject().getPosition();

        for (Creature creature : Game.creatures) {
            if (creature.equals(this)) continue;
            if (isFood(creature)) {
                float distance = currentPosition.subtract(creature.getGameObject().getPosition()).getMagnitude();
                if (distance <= sensoryRadius && distance < curTargetDistance) {
                    targetPosition = creature.getGameObject().getPosition();
                    targetCreature = creature;
                    hasTarget = true;
                    hasTargetCreature = true;
                    angleOfTarget = (float) Math.atan((currentPosition.getX() - targetPosition.getX()) /
                            (currentPosition.getZ() - targetPosition.getZ()));
                }
            }
        }
    }

    private void findRandomTarget() {
        Vector3f currentPosition = this.getGameObject().getPosition();
        angleOfTarget = (float) (Math.random() * 2f * Math.PI);
        float targetX = (float) Math.sin(angleOfTarget) * sensoryRadius + currentPosition.getX();
        float targetZ = (float) Math.cos(angleOfTarget) * sensoryRadius + currentPosition.getZ();

        //generates new random target position if target position is beyond the bounds of the terrain
        while (!(targetX >= Terrain.xBorderMin && targetX <= Terrain.xBorderMax &&
                targetZ >= Terrain.zBorderMin && targetZ <= Terrain.zBorderMax)) {
            angleOfTarget = (float) (Math.random() * 2f * Math.PI);
            targetX = (float) Math.sin(angleOfTarget) * sensoryRadius + currentPosition.getX();
            targetZ = (float) Math.cos(angleOfTarget) * sensoryRadius + currentPosition.getZ();
        }

        targetPosition = new Vector3f(targetX, Game.CREATURE_Y_POS, targetZ);
        hasTarget = true;
        hasTargetCreature = false;
    }

    //checks if creature is in the bounds of the terrain
    private boolean inBound(float currentX, float currentZ) {
        return currentX >= Terrain.xBorderMin && currentX <= Terrain.xBorderMax &&
                currentZ >= Terrain.zBorderMin && currentZ <= Terrain.zBorderMax;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSensoryRadius() {
        return sensoryRadius;
    }

    public void setSensoryRadius(float sensoryRadius) {
        this.sensoryRadius = sensoryRadius;
    }

    @Override
    public boolean isPlant() {
        return false;
    }

    @Override
    public void setDeath(boolean deathValue) {
        isDead = deathValue;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public float getNutritionalValue() {
        return this.nutritionalValue;
    }

    @Override
    public void setNutritionalValue(float nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public abstract boolean isFood(Creature creature);

}
