package com.damoy.unknown.utils;

import org.lwjgl.glfw.GLFW;

import com.damoy.unknown.core.model.Camera;

public final class Mouse {
	
	public static final int NUM_KEYS = 3;

	public static boolean mouseState[] = new boolean[NUM_KEYS];
	public static boolean mousePrevState[] = new boolean[NUM_KEYS];

	public static final int LEFT_BUTTON = 0;
	public static final int MID_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;
	
//	float fxpos = static_cast<float>(xpos);
//	float fypos = static_cast<float>(ypos);
//
//	if (firstMouseHandling){
//		mouseX = fxpos;
//		mouseY = fypos;
//		firstMouseHandling = false;
//	}
//
//	float xOffset = fxpos - mouseX;
//	// reversed since y-coords go from bottom to top
//	float yOffset = mouseY - fypos;
//
//	mouseX = fxpos;
//	mouseY = fypos;
//
//	if(isRightMouseButtonPressed())
//		playerCamera->processMouseMovement(xOffset, yOffset);
	
	private static boolean firstMouseProcess = true;
	private static float mouseX;
	private static float mouseY;
	
	public static void processMouseMovement(Camera camera, double xpos, double ypos) {
		float fxpos = (float) xpos;
		float fypos = (float) ypos;
		
		if(firstMouseProcess) {
			mouseX = fxpos;
			mouseY = fypos;
			firstMouseProcess = false;
		}
		
		float xoffset = fxpos - mouseX;
		float yoffset = mouseY - fypos;
		
		mouseX = fxpos;
		mouseY = fypos;
		
		if(isPressed(RIGHT_BUTTON)) {
			camera.processMovementOf(xoffset, yoffset);
		}
	}

	public static void buttonSet(int i, boolean b) {
		if (i == GLFW.GLFW_MOUSE_BUTTON_LEFT)
			set(LEFT_BUTTON, b);
		else if (i == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			set(RIGHT_BUTTON, b);
		else if (i == GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
			set(MID_BUTTON, b);
	}
	
	private static void set(int button, boolean b) {
		mouseState[button] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			mousePrevState[i] = mouseState[i];
		}
	}

	public static boolean isPressed(int i) {
		return mouseState[i] && !mousePrevState[i];
	}

}
