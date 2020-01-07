package gameEngine;

import gameEngine.graphics.Lighting;
import gameEngine.graphics.MeshObject;
import org.lwjgl.Version;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable {

    // The window handle
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 800;

    public int FPS = 1;
    public Window window;
    public Thread game;
    public Lighting lighting;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

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
        lighting = new Lighting();
        lighting.init();
    }

    private void update() {

        double millisecsPerFrame = 1E3 / FPS;
        long prevTime = System.currentTimeMillis();
        long steps = 0;

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.currentTimeMillis();
            long deltaT = loopStartTime - prevTime;
            prevTime = loopStartTime;
            steps += deltaT;

            while(steps >= millisecsPerFrame) {
                window.update();
                steps -= millisecsPerFrame;
            }

            sync(loopStartTime); //makes the thread sleep until next frame
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
