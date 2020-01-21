package gameEngine;

import Main.Game;
import Main.HUD;
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
    private HUD hud;

    //Time
    public static double gameTime = 0;
    private static long prevNanoTime = 0;
    public static double gameTimeIncreasePerSec = 1;

    ArrayList<GameObject> objects;
    private Vector3f ambientLight;
    private DirectionalLight directionalLight;
    private float lightAngle = -90;


    private PointLight pointLight;

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

        Window.WindowOptions windowOptions = new Window.WindowOptions(true, true, true, true, true);

        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "DNA", windowOptions);
        window.create();

        hud = new HUD();
        hud.init(window);
        renderer = new Renderer();
        renderer.init(window);
        camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
        input = new Input();

        //objects = new ArrayList<>();

        //set up lighting
        ambientLight = new Vector3f(.5f, .5f, .5f);
        Vector3f lightColour = new Vector3f(1, 0, 0);
        Vector3f lightPosition = new Vector3f(0, 0, -20);
        float lightIntensity = 4.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(-1, 0, 0);
        directionalLight = new DirectionalLight(LIGHT_COLOR_WHITE, lightPosition, lightIntensity);

        //TODO make parameters CONSTANTS
        Game game = new Game();
        objects = game.getObjects();

        //objects = new ArrayList<>();
        //objects.add(game.getTerrain().getTerrainObject());
        //objects = game.getObjects();

        prevNanoTime = System.nanoTime();

    }

    //tracks time
    public static void timeUpdate() {
        long currentTime = System.nanoTime();
        if (currentTime >= prevNanoTime + 1E9) {
            gameTime += gameTimeIncreasePerSec;
            prevNanoTime  = currentTime;
        }
    }

    private void update() {
        //TODO move thispackage Main;
        while (!glfwWindowShouldClose(window.getWindowHandle())) {
            long loopStartTime = System.currentTimeMillis();

            gameUpdate();
            timeUpdate();
            camera.update();
            renderer.render(window, objects, ambientLight, pointLight, directionalLight);
            hud.render(window);

            window.update();

            Game.update();
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

    }

    private void cleanup() {
        renderer.cleanup(objects);
        window.destroy();
        hud.cleanup();

    }
}
