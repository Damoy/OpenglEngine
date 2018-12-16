package com.damoy.unknown.core.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

	private Model model;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	
	public Entity(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	public Entity(Model model, Vector3f position, Vector3f rotation) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	public Matrix4f getTransformationMatrix() {
		return new Matrix4f().translationRotateScale(position.x, position.y, position.z,
				rotation.x, rotation.y, rotation.z, 1.0f, scale.x, scale.y, scale.z);
	}
	
	public void terminate() {
		model.getVao().delete();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
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

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
}