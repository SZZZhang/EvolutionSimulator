package gameEngine;

import gameEngine.graphics.MeshObject;
import gameEngine.math.Matrix3D;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameEngine implements Runnable {

    // The window handle
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 800;

    public int FPS = 30;
    public Window window;
    public Thread game;


    public void run() {

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        //game = new Thread(this, "game");
        //game.start();

        init();
        loop();

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

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        double secsPerFrame = 1d / FPS;
        long prevTime = System.nanoTime();
        long steps = 0;

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.nanoTime();
            long deltaT = loopStartTime - prevTime;
            prevTime = loopStartTime;
            steps += deltaT;

            window.update();

            while(steps >= secsPerFrame) {
                //update game state
                steps -= secsPerFrame;
            }

            //render
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

    // sets a perspective projection
    public static void setPerspective(float fovy, float aspect, float near, float far) {
        float bottom = -near * (float) Math.tan(fovy / 2);
        float top = -bottom;
        float left = aspect * bottom;
        float right = -left;
        glFrustum(left, right, bottom, top, near, far);
    }
}
