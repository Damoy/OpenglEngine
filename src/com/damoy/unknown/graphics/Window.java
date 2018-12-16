package com.damoy.unknown.graphics;

import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.damoy.unknown.core.model.Camera;
import com.damoy.unknown.utils.Config;
import com.damoy.unknown.utils.Keys;
import com.damoy.unknown.utils.Mouse;

public class Window {

	private Config config;
	private Camera camera;
	
	private long id;

	private String title;
	private int width;
	private int height;
	private boolean resized;
	private boolean vSync;

	public Window(Config config, Camera camera) {
		this.config = config;
		this.title = config.getWindowTitle();
		this.width = config.getWindowWidth();
		this.height = config.getWindowHeight();
		this.vSync = config.getWindowVSync();
		this.camera = camera;
	}
	
	public Window init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// configure GLFW
		GLFW.glfwDefaultWindowHints();
		// the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		// the window will be resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

		// create the window
		id = GLFW.glfwCreateWindow(width, height, title, 0, 0);

		if (id == 0L) {
			throw new RuntimeException("Failed to create window !");
		}

		GLFW.glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
			if(action == GLFW.GLFW_PRESS) {
				Keys.keySet(key, true);
			} else if(action == GLFW.GLFW_RELEASE) {
				Keys.keySet(key, false);
			}
			
			if(Keys.isPressed(Keys.KEY_ESCAPE)) {
				close();
			}
		});
		
		GLFW.glfwSetMouseButtonCallback(id, (long window, int button, int action, int mods) -> {
			if(action == GLFW.GLFW_PRESS) {
				Mouse.buttonSet(button, true);
			} else if(action == GLFW.GLFW_RELEASE) {
				Mouse.buttonSet(button, false);
			}
		});
		
		// long window, double xpos, double ypos
		GLFW.glfwSetCursorPosCallback(id, (long window, double xpos, double ypos) -> {
			Mouse.processMouseMovement(camera, xpos, ypos);
		});
		
		
		// Setup resize callback
		GLFW.glfwSetFramebufferSizeCallback(id, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.resized = true;
		});

		// get the thread stack and push a new frame
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pwidth = stack.mallocInt(1); // int*
			IntBuffer pheight = stack.mallocInt(1);

			// get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(id, pwidth, pheight);

			// get the resolution of the primary monitor
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			// center window
			int midX = (vidMode.width() - pwidth.get(0)) >> 1;
			int midY = (vidMode.height() - pheight.get(0)) >> 1;

			GLFW.glfwSetWindowPos(id, midX, midY);
		}

		// make the opengl context current
		GLFW.glfwMakeContextCurrent(id);
		
		// enable v-sync
		if (vSync)
			GLFW.glfwSwapInterval(1);
		
		// make the window visible
		GLFW.glfwShowWindow(id);
		
		return this;
	}

	public void render() {
		if(resized) {
			GL11.glViewport(0, 0, width, height);
			resized = true;
		}
		
		GLFW.glfwSwapBuffers(id);
		GLFW.glfwPollEvents();
	}
	
	public void close() {
		GLFW.glfwSetWindowShouldClose(id, true);
	}

	public void terminate() {
		Callbacks.glfwFreeCallbacks(id);
		GLFW.glfwDestroyWindow(id);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	public boolean isKeyPressed(int keyCode) {
		return GLFW.glfwGetKey(id, keyCode) == GLFW.GLFW_PRESS;
	}

	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(id);
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		GLFW.glfwSetWindowTitle(id, this.title);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public boolean isvSyncActivated() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

}
