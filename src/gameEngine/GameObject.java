package gameEngine;

import gameEngine.graphics.MeshObject;
import gameEngine.math.Vector3D;

public class GameObject {
    private Vector3D position, rotation, scale;
    private MeshObject mesh;

    public GameObject(Vector3D position, Vector3D rotation, Vector3D scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getScale() {
        return scale;
    }

    public void setScale(Vector3D scale) {
        this.scale = scale;
    }

    public Vector3D getRotation() {
        return rotation;
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }

    public MeshObject getObject() {
        return mesh;
    }

    public void setObject(MeshObject object) {
        this.mesh = object;
    }
}
