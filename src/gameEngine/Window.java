package gameEngine;

import gameEngine.graphics.MeshObject;
import gameEngine.math.Matrix3D;
import gameEngine.math.Vector3D;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long window;
    private int frames;
    private static long time;
    private Input input;
    private Vector3D bgColor = new Vector3D(0, 0, 0);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int windowPosX, windowPosY;
    private Matrix3D projection = new Matrix3D();
    private MeshObject cottage;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        projection = projection.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
    }

    public void create() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        input = new Input();

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        createCallbacks();
        // Center the window
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
    cottage = new MeshObject("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/cottage_obj.obj");

        // Make the window visible
        glfwShowWindow(window);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        setPerspective((float)(Math.toRadians(40)), width/height, 0.01f, 100f);

        glEnable(GL_TEXTURE_2D);	// enable texture mapping
		glEnable(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);

        //clears screen
        GL11.glClearColor(bgColor.getX(), bgColor.getY(), bgColor.getZ(), 1.0f);

        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void update() {
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();



        // draw your scene here...
        //glColor3f(1, 0, 0);
        glPushMatrix();
        glTranslatef(0,0,-50);
        //glRotatef(1, 1,0,0);
        cottage.draw();
        glPopMatrix();

        glfwSwapBuffers(this.getWindow());

        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b) {
        bgColor.set(r, g, b);
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
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
