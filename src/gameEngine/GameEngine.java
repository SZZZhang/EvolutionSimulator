package gameEngine;

import gameEngine.graphics.*;
import gameEngine.math.Vector3f;
import org.lwjgl.Version;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine implements Runnable {

    // The window handle
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 800;

    private Vector3f LIGHT_COLOR_WHITE = new Vector3f(1, 1, 1);
    private float LIGHT_INTENSITY = 0.1f;

    public int FPS = 1;
    public Window window;
    public Lighting lighting;
    public static Camera camera;
    public Input input;
    public Renderer renderer;
    ArrayList<GameObject> objects;
    private Vector3f ambientLight;
    private DirectionalLight directionalLight;
    private float lightAngle = -90;


    private PointLight pointLight;

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

        //set up lighting
        ambientLight = new Vector3f(.3f, .3f, .3f);
        Vector3f lightColour = new Vector3f(1, 0, 0);
        Vector3f lightPosition = new Vector3f(0, 0, -20);
        float lightIntensity = 4.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(-1, 0, 0);
        directionalLight = new DirectionalLight(LIGHT_COLOR_WHITE, lightPosition, lightIntensity);
//TODO make parameters CONSTANTS
        Texture texture;
        try {

            texture = new Texture("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/texturecube.png"); //TODO add better error handling for loading in textures
            //Texture t2 = new Texture("")

            Mesh mesh = OBJLoader.loadMesh("/Users/shirleyzhang/Desktop/ics4u/3DGame/src/assets/CuteCube.obj");
            mesh.setMaterial(new Material(texture));
            //GameObject house = new GameObject(mesh);
            //house.setPosition(new Vector3f(0,0,-2));
            //house.setScale(new Vector3f(0.01f, 0.01f, 0.01f));

            //
            GameObject square = new GameObject(mesh);
            objects.add(square);

            //square.setPosition(new Vector3f(0, 0, 0f));//TODO change
             //square.setScale(new Vector3f(.2f, .2f, .2f));
            //objects.add(square);
        } catch (Exception e) {
            System.out.println("failed to load texture: " + e);
        }


    }

    private void update() {
        //TODO move this

        while (!glfwWindowShouldClose(window.getWindow())) {
            long loopStartTime = System.currentTimeMillis();

            gameUpdate();
            camera.update();
            renderer.render(window, objects, ambientLight, pointLight, directionalLight);


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
