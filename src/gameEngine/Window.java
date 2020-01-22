package gameEngine;

import gameEngine.graphics.MeshObject;
import gameEngine.math.Matrix4f;
import gameEngine.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long windowHandle;
    private int frames;
    private static long time;
    private Input input;
    private Vector3f bgColor = new Vector3f(1f, 1f, 1f);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int windowPosX, windowPosY;
    private Matrix4f projection = new Matrix4f();
    private MeshObject cottage;

    private final WindowOptions opts;

    public Window(int width, int height, String title, WindowOptions opts) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.opts = opts;
        //projection = projection.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
    }

    public void create() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure
        //Sets OpenGL Context to version 3.2
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the windowHandle will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the windowHandle will be resizable

        windowHandle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        input = new Input();

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        createCallbacks();
        // Center the windowHandle
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the windowHandle visible
        glfwShowWindow(windowHandle);

        //clears screen
        GL11.glClearColor(bgColor.getX(), bgColor.getY(), bgColor.getZ(), 1.0f);

        if (opts.antialiasing) {
            glfwWindowHint(GLFW_SAMPLES, 4);
        }
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetCursorPosCallback(windowHandle, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(windowHandle, input.getMouseScrollCallback());
        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyboardCallback());
        GLFW.glfwSetWindowSizeCallback(windowHandle, sizeCallback);
    }

    public void update() {
        glfwSwapBuffers(this.getWindowHandle());
        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void destroy() {
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(windowHandle);
        GLFW.glfwDestroyWindow(windowHandle);
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

    public boolean isResized() {
        return isResized;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindowHandle() {
        return windowHandle;
    }


    public static class WindowOptions {

        public boolean cullFace;

        public boolean showTriangles;

        public boolean showFps;

        public boolean compatibleProfile;

        public boolean antialiasing;

        public WindowOptions(boolean cullFace, boolean showTriangles, boolean showFps, boolean compatibleProfile,
                             boolean antialiasing) {
            this.cullFace = cullFace;
            this.showTriangles = showTriangles;
            this.compatibleProfile = compatibleProfile;
            this.showFps = showFps;
            this.antialiasing = antialiasing;
        }
        public WindowOptions() {

        }
    }

    public WindowOptions getOptions() {
        return opts;
    }

    public void restoreState() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        if (opts.cullFace) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }
    }


}
