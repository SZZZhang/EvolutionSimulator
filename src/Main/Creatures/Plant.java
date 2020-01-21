/*

Abstract class for plant creatures.
Plant creatures have no movement. They generate randomly on
 the map at a specified interval.
 */

package Main.Creatures;

import Main.Game;
import gameEngine.GameObject;
import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public abstract class Plant implements Creature {

    private float nutritionalValue; //also serves as a health bar
    private Mesh mesh;
    private boolean isDead = false;

    public Plant( float nutValue, Mesh m) {
        this.nutritionalValue = nutValue;
        this.mesh = m;
        Game.plantNumber++;
    }

    //generates plant in a random unoccupied position
    public GameObject generatePlant() {

        int randomX = (int) (Math.random() * (Game.TERRAIN_WIDTH - 1));
        int randomZ = (int) (Math.random() * (Game.TERRAIN_LENGTH - 1));

        while (Game.plantMap[randomX][randomZ] != 0) {
            randomX = (int) (Math.random() * (Game.TERRAIN_WIDTH - 1));
            randomZ = (int) (Math.random() * (Game.TERRAIN_LENGTH - 1));

        }

        Vector3f randomPosition = new Vector3f(randomX, Game.CREATURE_Y_POS, randomZ);
        System.out.println(randomPosition.getX() + " " + randomPosition.getY() + " " + randomPosition.getZ());


        GameObject plant = new GameObject(mesh);

        Game.plantMap[randomX][randomZ] = 1;

        plant.setPosition(randomPosition);
        return plant;
    }

    public void update() {
        if (nutritionalValue <= 0) setDeath(true);
    }

    public boolean isDead() {
        return isDead;
    }

    public float getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(float nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    @Override
    public boolean isPlant() {
        return true;
    }

    @Override
    public boolean isPrey() {
        return false;
    }

    @Override
    public void setDeath(boolean deathValue) {
        isDead = deathValue;
    }
}
