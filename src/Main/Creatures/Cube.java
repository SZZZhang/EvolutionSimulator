package Main.Creatures;

import Main.Game;
import gameEngine.GameObject;
import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public class Cube extends Animal {

    private static float MAX_HUNGER_BAR = 50f;
    private static float NUTRITIONAL_VALUE = 30f;

    private float speed; //
    private float sensoryRadius;
    private float maxHungerBar;
    private float size;
    private float eatingSpeed;
    private float nutritionalValue;
    private float currentHungerBar;

    GameObject cubeObject;

    public Cube(float speed, float sensoryRadius, float size,
                float nutritionalValue) {

        super(speed, sensoryRadius, MAX_HUNGER_BAR, size,
                Game.cubeMesh, NUTRITIONAL_VALUE); //TODO rearrange params if time
        cubeObject = new GameObject(Game.cubeMesh);
        cubeObject.setPosition(new Vector3f(0, Game.CREATURE_Y_POS, 0));
        Game.gameObjects.add(cubeObject);
        Game.creatures.add(this);
    }

    @Override
    public boolean isPrey() {
        return true;
    }

    @Override
    public boolean isFood(Creature creature) {
        return creature.isPlant();
    }

    @Override
    public GameObject getGameObject() {
        return cubeObject;
    }

    @Override
    public float getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public void setNutritionalValue(float nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

}
