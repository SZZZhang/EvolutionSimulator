package gameEngine;

import gameEngine.graphics.Mesh;
import gameEngine.math.Vector3f;

public class GameObject {
    private Vector3f position, rotation, scale;
    private Mesh mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public GameObject(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f();
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh object) {
        this.mesh = object;
    }
}
