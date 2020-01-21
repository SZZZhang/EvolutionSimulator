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

    public Terrain(int width, int length) {

        try {

            Texture texture = new Texture(TERRAIN_TEXTURE_PATH);
            Mesh mesh = OBJLoader.loadMesh(TERRAIN_BLOCK_PATH);
            mesh.setMaterial(new Material(texture));

            terrainObject = new GameObject(mesh);

            //terrainObject.setPosition(new Vector3f(0,10,0));

            Game.gameObjects.add(terrainObject);
        } catch (Exception e) {
            System.out.println("failed to initialize terrain" + e);
        }
    }

    public GameObject getTerrainObject() {
        return terrainObject;
    }
}
