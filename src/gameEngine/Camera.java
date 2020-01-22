/*
Description: This class updates the functions and stuff
 */

package gameEngine;

import Main.Game;
import gameEngine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position, rotation;
    private float MOUSE_SENSITIVITY = 0.1f;
    private float MOUSE_MOVE_FACTOR = 1f;
    private double prevMouseX = 0, prevMouseY = 0, mouseX = 0, mouseY = 0;
    private boolean isHoldingButtonDown[] = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
    }

    public void update() {
        if(Input.isKeyDown(GLFW.GLFW_KEY_UP)) {
            position = position.add(new Vector3f(0, 0,-MOUSE_MOVE_FACTOR));
            System.out.println("MOVED");
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            position = position.add(new Vector3f(0, 0,MOUSE_MOVE_FACTOR));
            System.out.println(position.getX() + " " + position.getY() + " " + position.getZ());
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            position = position.add(new Vector3f(-MOUSE_MOVE_FACTOR, 0,0));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            position = position.add(new Vector3f(MOUSE_MOVE_FACTOR, 0,0));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            position = position.add(new Vector3f(0, MOUSE_MOVE_FACTOR, 0));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            if(position.getY() > Game.CREATURE_Y_POS + MOUSE_MOVE_FACTOR){
                position = position.add(new Vector3f(0 , -MOUSE_MOVE_FACTOR, 0));
            }
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}