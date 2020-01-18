package gameEngine;

import gameEngine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position, rotation;
    private float MOUSE_SENSITIVITY = 0.05f;
    private float dist;  //distance between camera and object
    public float VAngle, HAngle; //vertical and horizontal angles between camera and target object
    private double prevMouseX = 0, prevMouseY = 0, mouseX = 0, mouseY = 0;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
    }

    public void update() {
        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();

        float distX = (float) (mouseX - prevMouseX);
        float distY = (float) (mouseY - prevMouseY);

        rotation = rotation.add(new Vector3f(-distY * MOUSE_SENSITIVITY,
                -distX * MOUSE_SENSITIVITY, 0));

        prevMouseX = mouseX;
        prevMouseY = mouseY;

        System.out.println("Mouse motion was updated");
    }

    public void update(GameObject object) {

        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();

        float distX = (float) (mouseX - prevMouseX);
        float distY = (float) (mouseY - prevMouseY);

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            VAngle -= distY * MOUSE_SENSITIVITY;
            HAngle += distX * MOUSE_SENSITIVITY;
        }
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            if (dist > 0) {
                dist += distY * MOUSE_SENSITIVITY;
            } else {
                dist = 0.1f;
            }
        }

        float HDist = (float) (dist * Math.cos(Math.toRadians(rotation.getX())));
        float VDist = (float) (dist * Math.cos(Math.toRadians(rotation.getX())));

        float xOffset = (float) (HDist * Math.sin(Math.toRadians(-rotation.getY())));
        float zOffset = (float) (HDist * Math.sin(Math.toRadians(-rotation.getY())));

        position.set(object.getPosition().getX() + xOffset,
                object.getPosition().getY() - VDist, object.getPosition().getZ() + zOffset);

    }

    //TODO magic numbers

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            position.setX(position.getX() + (float) Math.sin(Math.toRadians(rotation.getY())) * -1.0f * offsetZ);
            position.setZ(position.getZ() + (float) Math.cos(Math.toRadians(rotation.getY())) * offsetZ);
        }
        if (offsetX != 0) {
            position.setX(position.getX() + (float) Math.sin(Math.toRadians(rotation.getY() - 90)) * -1.0f * offsetX);
            position.setZ(position.getZ() + (float) Math.cos(Math.toRadians(rotation.getY()) - 90) * offsetX);
        }
        position.setY(position.getY() + offsetY);
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