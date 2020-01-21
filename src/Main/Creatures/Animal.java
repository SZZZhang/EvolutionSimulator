package Main.Creatures;

import Main.Game;
import gameEngine.GameEngine;
import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public abstract class Animal implements Creature {

    private float EATING_RADIUS = 1;


    private float speed; //
    private float sensoryRadius;
    private float maxHungerBar;
    private float size;
    private float nutritionalValue;
    private float currentHungerBar;

    private boolean hasTarget; //target is the position it is moving toward
    private boolean hasTargetCreature; //target is the position it is moving toward
    private Vector3f targetPosition;
    private float angleOfTarget;
    private Creature targetCreature;
    private Mesh mesh;
    private boolean isDead = false;

    public Animal(float speed, float sensoryRadius, float maxHungerBar, float size, Mesh mesh,
                   float nutritionalValue) {
        this.speed = speed;
        this.size = size;
        this.sensoryRadius = sensoryRadius;
        this.maxHungerBar = maxHungerBar;
        this.nutritionalValue = nutritionalValue;
        this.mesh = mesh;
        this.currentHungerBar = maxHungerBar;
    }

    @Override
    public void update() {

//you don't always have a target creature;
        if(targetCreature != null && targetCreature.getGameObject().getPosition().subtract(this.getGameObject().getPosition()).getMagnitude() <= 1) {
            eat(targetCreature);
            findTarget();
        }

        move();
        //currentHungerBar -= size * speed; //makes creature more hungry
        if(currentHungerBar <= 0) this.setDeath(true);
    }

    public void move() {
        if (!hasTarget) findTarget();
        if (targetPosition.equals(this.getGameObject().getPosition())) hasTarget = false;

        //TODO first rotate, then move

        //translation
        float deltaX = (float) (Math.cos(angleOfTarget) * speed * GameEngine.gameTimeIncreasePerSec);
        float deltaZ = (float) (Math.cos(angleOfTarget) * speed * GameEngine.gameTimeIncreasePerSec);

        this.getGameObject().setPosition(new Vector3f(
                this.getGameObject().getPosition().add(new Vector3f(deltaX, 0, deltaZ))));
    }

    public void eat(Creature creature) {
        currentHungerBar = Math.max(currentHungerBar + creature.getNutritionalValue(), maxHungerBar);
        creature.setDeath(true);
    }

    private void findTarget() {
        float targetDistance = sensoryRadius + 1;
        Vector3f currentPosition = this.getGameObject().getPosition();
        targetPosition = new Vector3f();

        for (Creature creature : Game.creatures) {
            if (creature.equals(this)) continue;
            if (isFood(creature)) {

                Vector3f subtracted = creature.getGameObject().getPosition();
                Vector3f vec = currentPosition
                        .subtract(subtracted);
                float distance = vec.getMagnitude();

                if (distance <= sensoryRadius && distance < targetDistance) {
                    targetPosition = creature.getGameObject().getPosition();
                    hasTarget = true;
                    targetCreature = creature;
                }
            }
        }

        if (!hasTarget) {
            float randomX = (float) Math.random() * sensoryRadius;
            float randomZ = (float) Math.sqrt(Math.pow(sensoryRadius, 2) - Math.pow(randomX, 2));
            targetPosition = new Vector3f(randomX, Game.CREATURE_Y_POS,
                    randomZ);
        }

        angleOfTarget = (float) Math.acos(currentPosition.dotProduct(targetPosition) /(
                currentPosition.getMagnitude() * targetPosition.getMagnitude()));

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

    public abstract boolean isFood(Creature creature);

}
