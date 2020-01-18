package gameEngine;

import gameEngine.graphics.Lighting;
import gameEngine.graphics.Mesh;
import gameEngine.graphics.Renderer;
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
            // VO
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
    };

    float[] colours = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
    };
    int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            4, 0, 3, 5, 4, 3,
            // Right face
            3, 2, 7, 5, 3, 7,
            // Left face
            6, 1, 0, 6, 0, 4,
            // Bottom face
            2, 1, 6, 2, 6, 7,
            // Back face
            7, 6, 4, 7, 4, 5,
    };

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
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
        input = new Input();

        objects = new ArrayList<>();
        GameObject square = new GameObject(new Mesh(positions, colours, indices));
        square.setMesh(new Mesh(positions, colours, indices));
        square.setPosition(new
                Vector3f(0, 0, -2f));//TODO change
        //square.setScale(new Vector3f(.5f,.5f,.5f));
        objects.add(square);
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
        renderer.cleanup();
        window.destroy();
    }
}
