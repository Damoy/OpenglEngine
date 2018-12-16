package com.damoy.unknown.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.damoy.unknown.core.model.Camera;
import com.damoy.unknown.core.model.EntityBatch;
import com.damoy.unknown.graphics.shaders.ShaderProgram;
import com.damoy.unknown.utils.Config;
import com.damoy.unknown.utils.Utils;

public class CoreRenderer {

	private final static float FOV = (float) Math.toRadians(45.0f);
	private final static float Z_NEAR = 0.01f;
	private final static float Z_FAR = 1000.0f;
	
	private EntityRenderer entityRenderer;
	private ShaderProgram shader;
	
	private Config config;
	private Window window;
	private Matrix4f projectionMatrix;
	private Vector3f backgroundColor;
	
	public static void init() {
		GL.createCapabilities();
	}
	
	public CoreRenderer(Config config, Window window) {
		// TODO
//		GL11.glEnable(GL11.GL_CULL_FACE);
//		GL11.glCullFace(GL11.GL_FRONT);
		
		this.config = config;
		this.window = window;
		this.backgroundColor = Utils.normalizeColor(config.getBackgroundColor());
		
		this.shader = new ShaderProgram().vertex("./resources/shaders/vertex.vs")
				.fragment("./resources/shaders/fragment.fs")
				.binding().link().generate();
		
		this.entityRenderer = new EntityRenderer(config, window);
		createProjectionMatrix();
		
		shader.bind();
		shader.setProjectionMatrixUniform(projectionMatrix);
		shader.unbind();
	}
	
	public void render(Camera camera, EntityBatch batch) {
		// prepare
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
		resize(window);
		
		// load view matrix
		shader.bind();
		shader.setViewMatrixUniform(camera.getViewMatrix());
		
		// actual render
		entityRenderer.render(camera, batch, shader);
		
		// terminate
		shader.unbind();
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) config.getWindowWidth() / config.getWindowHeight();
		projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
	}
	
	private void resize(Window window) {
		if(window.isResized()) {
			GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
	}
	
	public void terminate() {
		if(shader != null)
			shader.terminate();
	}
	
}
