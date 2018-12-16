package com.damoy.unknown.core;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import com.damoy.unknown.core.model.Block;
import com.damoy.unknown.core.model.Camera;
import com.damoy.unknown.core.model.Entity;
import com.damoy.unknown.core.model.EntityBatch;
import com.damoy.unknown.graphics.CoreRenderer;
import com.damoy.unknown.graphics.Window;
import com.damoy.unknown.utils.Config;
import com.damoy.unknown.utils.Time;

public class Core {

	private Config config;
	private Window window;
	private CoreRenderer renderer;
	private Camera camera;
	private EntityBatch batch;

	public Core(Config config) {
		this.config = config;
		this.camera = new Camera(this, 0.0f, 90.0f);
		this.window = new Window(config, camera).init();
		
		CoreRenderer.init();
		this.renderer = new CoreRenderer(config, window);
	}

	public void start() {
		Time.init();
		Vector3f basePos = new Vector3f(0.0f, 0.0f, -10.0f);
		
		List<Entity> entities = new ArrayList<>();

		entities.add(new Block(new Vector3f(basePos).add(0.0f, 0.0f, 0.0f), "./resources/textures/bb16x16.png"));
		
//		for(int i = 0; i < 2; ++i) {
//			entities.add(new Block(new Vector3f(basePos).add(i * 1.0f, 0.0f, 0.0f), "./resources/textures/bb16x16.png"));
//		}
		
		batch = new EntityBatch(entities.get(0).getModel(), entities);
		run();
	}

	public void run() {
		if(window.isvSyncActivated()) {
			coreRunGlSync();
		} else {
			coreRunCustomSync();
		}
		terminate();
	}
	
	private void coreRunGlSync() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / config.getUpsCap();
		
		while (!window.windowShouldClose()) {
			elapsedTime = Time.getElapsedTime();
			accumulator += elapsedTime;

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}
			
			render();
		}
	}
	
	private void coreRunCustomSync() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / config.getUpsCap();
		
		while (!window.windowShouldClose()) {
			elapsedTime = Time.getElapsedTime();
			accumulator += elapsedTime;

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}
			
			render();
			sync();
		}
	}
	
	private void update(float interval) {
		camera.update();
	}
	
	private void render() {
		renderer.render(camera, batch);
		window.render();
	}

	private void sync() {
		float loopSlot = 1f / config.getFpsCap();
		double endTime = Time.getLastLoopTime() + loopSlot;
		while (Time.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
			}
		}
	}
	
	private void terminate() {
		window.terminate();
		batch.terminate();
	}

	public Config getConfig() {
		return config;
	}

	public Window getWindow() {
		return window;
	}

	public CoreRenderer getRenderer() {
		return renderer;
	}

	public Camera getCamera() {
		return camera;
	}

	public EntityBatch getBatch() {
		return batch;
	}
	
}
