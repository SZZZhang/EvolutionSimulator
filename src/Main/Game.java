package Main;

import Main.Creatures.Creature;
import Main.Creatures.Cube;
import Main.Creatures.Pyramid;
import gameEngine.GameEngine;
import gameEngine.GameObject;
import gameEngine.OBJLoader;
import gameEngine.graphics.Material;
import gameEngine.graphics.Mesh;
import gameEngine.graphics.Texture;
import gameEngine.math.Vector3f;

import java.util.ArrayList;

public class Game {

    public static float CREATURE_Y_POS = 2;
    public static int TERRAIN_WIDTH = 100, TERRAIN_LENGTH = 100;
    public static float MAP_TO_TERRAIN_WIDTH_SHIFT = TERRAIN_WIDTH / 2f;
    public static float MAP_TO_TERRAIN_LENGTH_SHIFT = TERRAIN_LENGTH / 2f;
    public static int MAX_PLANT_NUMBER = 20;
    public static float DEATH_SHRINK_FACTOR = 0.1f;
    private static int PYRAMID_GENERATE_INTERVAL = 20;

    //TODO change file paths
    public static String PRISM_TEXTURE_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/pyramid.png";
    public static String PRISM_OBJ_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/pyramid.obj";

    public static String CUBE_TEXTURE_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/Cube.png";
    public static String CUBE_OBJ_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/CubeReal2.obj";

    public static Mesh pyramidMesh;
    public static Texture prismTexture;
    public static Mesh cubeMesh;
    public static Texture cubeTexture;

    public static int plantMap[][];
    private static Terrain terrain;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<Creature> creatures = new ArrayList<>();

    private static double prevPlantUpdateTime = 0;
    public static int plantNumber = 0;


    public Game() {

        plantMap = new int[TERRAIN_WIDTH][TERRAIN_LENGTH];
        terrain = new Terrain(TERRAIN_WIDTH, TERRAIN_LENGTH);
        //gameObjects.add(terrain.getTerrainObject());

        //load textures and meshes
        pyramidMesh = OBJLoader.loadMesh(PRISM_OBJ_PATH);
        prismTexture = new Texture(PRISM_TEXTURE_PATH);
        pyramidMesh.setMaterial(new Material(prismTexture));

        cubeMesh = OBJLoader.loadMesh(CUBE_OBJ_PATH);
        cubeTexture = new Texture(CUBE_TEXTURE_PATH);
        cubeMesh.setMaterial(new Material(cubeTexture));

        Pyramid pyramid = new Pyramid();
        Cube cube = new Cube(0.1f, 4, 1, 30f);
        //pyramid.getGameObject().setPosition(new Vector3f(0,2,0));
    }

    public static void update() {

        /*System.out.println(terrain.getTerrainObject().getPosition().getX() + " " +
        terrain.getTerrainObject().getPosition().getY() + " " +
     terrain.getTerrainObject().getPosition().getZ());*/

        /*System.out.println(gameObjects.get(1).getPosition().getX() + " " + gameObjects.get(1).getPosition().getY() + " " +
                gameObjects.get(1).getPosition().getZ());*/

        //System.out.println(GameEngine.gameTime);

        updatePlants();
        for (Creature creature : creatures) {
            creature.update();
            if (creature.isDead()) {
                death(creature);
            }
        }
    }

    public ArrayList<GameObject> getObjects() {
        return gameObjects;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    private static void updatePlants() {
        if (GameEngine.gameTime >= prevPlantUpdateTime + PYRAMID_GENERATE_INTERVAL) {
            Pyramid p = new Pyramid();
            prevPlantUpdateTime = GameEngine.gameTime;
        }
    }

    //Shrinks creature for death animation and deletes creature from array list
    private static void death(Creature creature) {
        Vector3f creatureObjScale = creature.getGameObject().getScale();
        if (creatureObjScale.getX() - DEATH_SHRINK_FACTOR > 0) {
            creature.getGameObject().setScale(
                    creatureObjScale.add(new Vector3f(DEATH_SHRINK_FACTOR,
                            DEATH_SHRINK_FACTOR, DEATH_SHRINK_FACTOR))
            );
        } else {
            gameObjects.remove(creature.getGameObject());
            creatures.remove(creature);
        }
    }
}
