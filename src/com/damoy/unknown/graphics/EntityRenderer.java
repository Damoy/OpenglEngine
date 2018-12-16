package com.damoy.unknown.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.damoy.unknown.core.model.Camera;
import com.damoy.unknown.core.model.Entity;
import com.damoy.unknown.core.model.EntityBatch;
import com.damoy.unknown.graphics.shaders.ShaderProgram;
import com.damoy.unknown.utils.Config;

@SuppressWarnings("unused")
public class EntityRenderer {

	private Config config;
	private Window window;
	
	public EntityRenderer(Config config, Window window) {
		this.config = config;
		this.window = window;
	}
	
	public void render(Camera camera, EntityBatch batch, ShaderProgram shader) {
		batch.getModel().getVao().bind();
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		batch.getModel().getTexture().bind();
		
		for(Entity entity : batch.getEntities()) {
			shader.setTransformationMatrixUniform(entity.getTransformationMatrix());
			GL11.glDrawElements(GL11.GL_TRIANGLES, batch.getModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		
		batch.getModel().getTexture().unbind();
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		batch.getModel().getVao().unbind();
	}
	
}