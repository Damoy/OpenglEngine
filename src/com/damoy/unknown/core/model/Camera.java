package com.damoy.unknown.core.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.damoy.unknown.core.Core;
import com.damoy.unknown.graphics.Window;
import com.damoy.unknown.utils.Keys;
import com.damoy.unknown.utils.Utils;

public class Camera {

	private final static Vector3f WORLD_UP = new Vector3f(0.0f, 1.0f, 0.0f);
	
	private Core core;
	
	private Vector3f position;
	private Vector3f rotation;

	private Vector3f front;
	private Vector3f right;
	private Vector3f up;
	
	private float speed;

	public Camera(Core core, float pitch, float yaw) {
		this.core = core;
		position = new Vector3f(0.08f, 0.0f, -14.1f);
		speed = 1f;
		
		rotation = new Vector3f(pitch, yaw, 0.0f);
		front = new Vector3f(0.0f, 0.0f, -1.0f);
		right = new Vector3f();
		up = new Vector3f();
	}
	
	public void update() {
		float velocity = speed * 0.1f;
		
		if(Keys.anyKeyPress()) {
			if(Keys.isLeftKeyPressed())
				position.sub(right.mul(velocity));
			if(Keys.isRightKeyPressed())
				position.add(right.mul(velocity));
			if(Keys.isBackwardKeyPressed())
				position.sub(front.mul(velocity));
			if(Keys.isForwardKeyPressed())
				position.add(front.mul(velocity));
			if(Keys.isDownKeyPressed())
				position.sub(up.mul(velocity));
			if(Keys.isUpKeyPressed())
				position.add(up.mul(velocity));
			
			updateDirection();
			
			Window window = core.getWindow();
			StringBuilder sb = new StringBuilder();
			sb.append("[x:");
			sb.append(position.x);
			sb.append(", y:");
			sb.append(position.y);
			sb.append(", z:");
			sb.append(position.z);
			sb.append("]");
			
			window.setTitle(sb.toString());
		}
		// Utils.logn("x:" + position.x + ",y:" + position.y + ",z:" + position.z);
	}
	
	private void updateDirection() {
		front.x = (float) (Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)));
		front.y = (float) Math.sin(Math.toRadians(rotation.x));
		front.z = (float) (Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x)));
		front.normalize();
		
		front.cross(WORLD_UP, right);
		right.normalize();
		
		right.cross(front, up);
		up.normalize();
	}
	
	private float mouseSensitivity = 0.1f;
	public Camera processMovementOf(float xoffset, float yoffset) {
		xoffset *= mouseSensitivity;
		yoffset *= mouseSensitivity;
		
		// yes, y for yaw with xoffset
		rotation.y += xoffset;
		rotation.x += yoffset;
		
		if(rotation.x > 89.0f)
			rotation.x = 89.0f;
		if(rotation.x < -89.0f)
			rotation.x = -89.0f;
		
		if(Utils.nfequals(xoffset, 0.0f) || Utils.nfequals(yoffset, 0.0f)) 
			updateDirection();
		
		return this;
	}
	
	public Camera incX(float dx) {
		position.x += dx;
		return this;
	}
	
	public Camera incY(float dy) {
		position.y += dy;
		return this;
	}
	
	public Camera incZ(float dz) {
		position.z += dz;
		return this;
	}
	
	public Camera incPos(float dx, float dy, float dz) {
		position.add(dx, dy, dz);
		return this;
	}
	
	public Matrix4f getViewMatrix() {
		return new Matrix4f().lookAt(position, new Vector3f(position).add(front), up);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return rotation.x;
	}

	public float getYaw() {
		return rotation.y;
	}

	public float getRoll() {
		return rotation.z;
	}
}
