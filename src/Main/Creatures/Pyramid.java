package Main.Creatures;

import Main.Game;
import gameEngine.GameObject;

public class Pyramid extends Plant {

    private static float NUTRITIONAL_VALUE = 20f;
    private static int GENERATE_INTERVAL = 10;

    GameObject pyramidObject;

    public Pyramid() {
        super(NUTRITIONAL_VALUE, Game.pyramidMesh);
        pyramidObject = super.generatePlant();
        Game.gameObjects.add(pyramidObject);
        Game.creatures.add(this);
    }

    @Override
    public GameObject getGameObject() {
        return pyramidObject;
    }
}
