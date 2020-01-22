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
    private int WINDOW_HEIGHT = 800, WINDOW_WIDTH = 1200;

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
    private float lightAngle = 0;

    private PointLight pointLight;

    public void run() {

        try {
            init();
            update();
        } catch (Exception e) {
           System.out.println("Error: " + e);
        }  finally{
            cleanup();
        }
    }

    private void init() {

        Window.WindowOptions windowOptions = new Window.WindowOptions(true, true, true, true, true);

        window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "Evolution simulator", windowOptions);
        window.create();

        hud = new HUD();
        hud.init(window);
        renderer = new Renderer();
        renderer.init(window);
        camera = new Camera(new Vector3f(0, Game.CREATURE_Y_POS + 1, 1), new Vector3f(0, 0, 0));
        input = new Input();

        //objects = new ArrayList<>();

        //set up lighting
        ambientLight = new Vector3f(.5f, .5f, .5f);
        Vector3f lightColour = new Vector3f(1, 0, 0);
        Vector3f lightPosition = new Vector3f(0, 0, 0);
        float lightIntensity = .5f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(0, 1, 0);
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


    private void cleanup() {
        renderer.cleanup(objects);
        window.destroy();
        hud.cleanup();

    }
}
