package Main;

import gameEngine.ADT.LinkedList;
import gameEngine.GameObject;
import gameEngine.OBJLoader;
import gameEngine.graphics.Material;
import gameEngine.graphics.Mesh;
import gameEngine.graphics.Texture;
import gameEngine.math.Vector3f;

public class Terrain {

    private GameObject terrainObject;
    private String TERRAIN_BLOCK_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/Terrain.obj";
    private String TERRAIN_TEXTURE_PATH = "/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/Terrain.png";

    public static float xBorderMin, xBorderMax, zBorderMin, zBorderMax;

    public Terrain(int width, int length) {

        try {

            Texture texture = new Texture(TERRAIN_TEXTURE_PATH);
            Mesh mesh = OBJLoader.loadMesh(TERRAIN_BLOCK_PATH);
            mesh.setMaterial(new Material(texture));

            terrainObject = new GameObject(mesh);

            Game.gameObjects.add(terrainObject);

            xBorderMin = -Game.TERRAIN_WIDTH/2f;
            xBorderMax = Game.TERRAIN_WIDTH/2f;
            zBorderMin = -Game.TERRAIN_LENGTH/2f;
            zBorderMax = Game.TERRAIN_LENGTH/2f;

        } catch (Exception e) {
            System.out.println("failed to initialize terrain" + e);
        }
    }

    public GameObject getTerrainObject() {
        return terrainObject;
    }
}
