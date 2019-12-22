package GameEngine;

import math.Vector3D;

public class Camera {
	private Vector3D position, rotation;
	private float MOVE_SPEED = 0.05f;
	private double prevMouseX = 0, prevMouseY = 0, mouseX = 0, mouseY = 0;

	public Camera(Vector3D position, Vector3D rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public void update() {
		mouseX = Input.getMouseX();
		mouseY = Input.getMouseY();

		float distanceX = (float) (mouseX - prevMouseX);
		float distanceY = (float) (mouseY - prevMouseY);
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