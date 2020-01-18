package gameEngine;

import gameEngine.graphics.Lighting;
import gameEngine.graphics.Mesh;
import gameEngine.graphics.Renderer;
import gameEngine.graphics.Texture;
import gameEngine.math.Vector3f;
import org.lwjgl.Version;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable {

    // The window handle
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 1000;

    public int FPS = 1;
    public Window window;
    public Lighting lighting;
    public static Camera camera;
    public Input input;
    public Renderer renderer;
    ArrayList<GameObject> objects;

            float[] positions = new float[]{
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,
            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,
            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,};
        float[] textCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,
            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,
            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,};
        int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,};
    //GameObject house = new GameObject(new Vector3f(0, 0, -50), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

    public void run() {

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        //game = new Thread(this, "game");
        //game.start();

        try {
            init();
            update();
        } finally {
            cleanup();
        }

    }

    private void init() {

        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "DNA");
        window.create();

        renderer = new Renderer();
        renderer.init(window);
        camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
        input = new Input();

        objects = new ArrayList<>();
        Texture texture;
        try {
            texture = new Texture("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/grassblock.png"); //TODO add better error handling for loading in textures
            //Texture t2 = new Texture("")

            Mesh mesh = OBJLoader.loadMesh("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/cottage_obj.obj");
            //mesh.setTexture(texture);System.out.println("success");
            GameObject house = new GameObject(mesh);
            //house.setPosition(new Vector3f(0,0,-2));
            //house.setScale(new Vector3f(0.01f, 0.01f, 0.01f));
            objects.add(house);
            //
            // GameObject square = new GameObject(new Mesh(positions, textCoords, indices, texture));
        //square.setPosition(new Vector3f(0, 0, 0f));//TODO change
       // square.setScale(new Vector3f(.2f, .2f, .2f));
        //objects.add(square);
        } catch (Exception e) {
            System.out.println("failed to load texture: " + e);
        }


    }

    private void update() {
        //TODO move this

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.currentTimeMillis();

            camera.update();
            renderer.render(window, objects);


            window.update();

            sleep(loopStartTime); //makes the thread sleep until next update
        }
    }

    private void sleep(double loopStartTime) {
        float loopSlot = 1f / FPS;
        int sleepInterval = 2; // time that the thread sleeps between checks (milliseconds)

        double endTime = loopStartTime + loopSlot;
        while (System.nanoTime() < endTime) {
            try {
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                System.out.println("Uh oh, sync method's sleep was interrupted!");
            }
        }
    }

    private void gameUpdate() {
        for (GameObject object : objects) {
            // Update position
            Vector3f itemPos = object.getPosition();
            /*float posx = itemPos.getX() + displxInc * 0.01f;
            float posy = itemPos.getY() + displyInc * 0.01f;
            float posz = itemPos.getZ() + displzInc * 0.01f;*/
            object.setPosition(new Vector3f(itemPos.getX(), itemPos.getY(), itemPos.getZ()));
            /*
            // Update scale
            float scale = object.getScale();
            scale += scaleInc * 0.05f;
            if ( scale < 0 ) {
                scale = 0;
            }
            object.setScale();

            // Update rotation angle
            float rotation = object.getRotation().getZ() + 1.5f;
            if ( rotation > 360 ) {
                rotation = 0;
            }
            object.setRotation(0, 0, rotation);  */
        }
    }

    private void cleanup() {
        renderer.cleanup(objects);
        window.destroy();
    }
}
