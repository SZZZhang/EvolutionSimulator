package gameEngine;

import gameEngine.math.Vector3D;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3D position, rotation;
    private float MOUSE_SENSITIVITY = 0.05f;
    private float dist;  //distance between camera and object
    public float VAngle, HAngle; //vertical and horizontal angles between camera and target object
    private double prevMouseX = 0, prevMouseY = 0, mouseX = 0, mouseY = 0;

    public Camera(Vector3D position, Vector3D rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update(Boolean bleh) {
        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();

        float distX = (float) (mouseX - prevMouseX);
        float distY = (float) (mouseY - prevMouseY);

        rotation = rotation.add(new Vector3D(-distY * MOUSE_SENSITIVITY,
                -distX * MOUSE_SENSITIVITY, 0));

        prevMouseX = mouseX;
        prevMouseY = mouseY;
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

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getRotation() {
        return rotation;
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }
}