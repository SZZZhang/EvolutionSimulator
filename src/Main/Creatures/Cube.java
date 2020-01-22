package Main.Creatures;

import Main.Game;
import gameEngine.ADT.Trait;
import gameEngine.ADT.TraitMap;
import gameEngine.GameObject;
import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public class Cube extends Animal {

    //Cube species constants
    public static final float SPEED_MIN = 0.05f;
    public static final float SPEED_MAX = 0.15f;
    public static final float SPEED_DEFAULT = 0.1f;
    public static final float SIZE_MIN = .75f;
    public static final float SIZE_MAX = 1.25f;
    public static final float SIZE_DEFAULT = 1f;
    public static final float SENSORY_RADIUS_MIN = 10f;
    public static final float SENSORY_RADIUS_MAX = 20f;
    public static final float SENSORY_RADIUS_DEFAULT = 15f;
    private static final float MAX_HUNGER_BAR = 50f;
    private static final float NUTRITIONAL_VALUE = 30f;

    //information regarding the cube population
    public static int cubePopulation = 0;
    public static float sumOfCubeSensoryRadius = 0;
    public static float sumOfCubeSpeed = 0;

    GameObject cubeObject;

    public Cube(TraitMap traits) {

        super(traits, Game.cubeMesh, NUTRITIONAL_VALUE, MAX_HUNGER_BAR);
        cubeObject = new GameObject(Game.cubeMesh);
        cubeObject.setPosition(new Vector3f(0, Game.CREATURE_Y_POS, 0));
        Game.gameObjects.add(cubeObject);
        Game.creatures.add(this);

        //update population information
        cubePopulation++;
        sumOfCubeSensoryRadius += traits.getTrait(Animal.SENSORY_RADIUS_TRAIT_NAME).getValue();
        sumOfCubeSpeed += traits.getTrait(Animal.SPEED_TRAIT_NAME).getValue();
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

    //Used for creating new cubes
    public static TraitMap getDefaultTraits() {
        Trait[] traits = {
                new Trait(Animal.SPEED_TRAIT_NAME, SPEED_MIN, SPEED_MAX, SPEED_DEFAULT),
                new Trait(Animal.SENSORY_RADIUS_TRAIT_NAME, SENSORY_RADIUS_MIN, SENSORY_RADIUS_MAX,
                        SENSORY_RADIUS_DEFAULT),
                new Trait(Animal.SIZE_TRAIT_NAME, SIZE_MIN, SIZE_MAX, SIZE_DEFAULT)
        };
        return new TraitMap(traits);
    }


}
