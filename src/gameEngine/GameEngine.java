package gameEngine;

import gameEngine.graphics.Lighting;
import gameEngine.graphics.MeshObject;
import gameEngine.graphics.Renderer;
import gameEngine.math.Vector3D;
import org.lwjgl.Version;

import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class GameEngine implements Runnable {

    // The window handle
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 800;

    public int FPS = 1;
    public Window window;
    public Lighting lighting;
    public Camera camera;
    public Renderer renderer;

    float[] vertices = new float[]{
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
    };

    float[] colours = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
    };

    GameObject house = new GameObject(new Vector3D(0, 0, -50), new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));

    public void run() {

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        //game = new Thread(this, "game");
        //game.start();

        init();
        update();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window.getWindow());
        glfwDestroyWindow(window.getWindow());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void init() {

        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "DNA");
        window.create();
        renderer = new Renderer();
        renderer.init();
        camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
    }

    private void update() {

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.currentTimeMillis();

            ArrayList<MeshObject> objects = new ArrayList<>();
            objects.add(new MeshObject(vertices, colours));
            renderer.render(window, objects);

            window.update();

            sync(loopStartTime); //makes the thread sleep until next update
        }
    }

    private void sync(double loopStartTime) {
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
}
