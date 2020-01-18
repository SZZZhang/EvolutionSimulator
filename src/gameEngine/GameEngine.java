package gameEngine;

import gameEngine.graphics.*;
import gameEngine.math.Vector3f;
import org.lwjgl.Version;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable {

    // constants
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 1000;
    private Vector3f LIGHT_COLOR_WHITE = new Vector3f(1, 1, 1);
    private float LIGHT_INTENSITY = 0.1f;
    private Vector3f LIGHT_POSITION = new Vector3f(-1, 0, 0);

    public int FPS = 1;
    public Window window;
    public Lighting lighting;
    public static Camera camera;
    public Input input;
    public Renderer renderer;

    private DirectionalLight directionalLight;
    private SceneLight sceneLight;
    private float lightAngle = -90;

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

        //set up direction lighting
        sceneLight = new SceneLight();
        sceneLight.setDirectionalLight(new DirectionalLight(LIGHT_COLOR_WHITE, LIGHT_POSITION, LIGHT_INTENSITY));

        objects = new ArrayList<>();
        GameObject square = new GameObject(new Mesh(positions, colours, indices));
        square.setMesh(new Mesh(positions, colours, indices));
        //square.setPosition(new Vector3f(0, 0, 0f));//TODO change
        square.setScale(new Vector3f(.01f, .01f, .01f));
        objects.add(square);
    }

    private void update() {
        //TODO move this

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.currentTimeMillis();

            camera.update();
            renderer.render(window, objects, sceneLight);


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

        // Update directional light direction, intensity and colour

        DirectionalLight directionalLight = sceneLight.getDirectionalLight();
        lightAngle += 1.1f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90; //TODO add scene light class
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);

            //TODO fix magic numbers
            directionalLight.setColor(new Vector3f(directionalLight.getColor().getX(),
                    Math.max(factor, 0.9f), Math.max(factor, 0.5f)));


        } else {
            directionalLight.setIntensity(1);
            directionalLight.setColor(LIGHT_COLOR_WHITE);
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.setDirection(new Vector3f((float) Math.sin(angRad), (float) Math.cos(angRad),
                directionalLight.getDirection().getZ()));

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
