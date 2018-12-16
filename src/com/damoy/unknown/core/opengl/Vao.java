package com.damoy.unknown.core.opengl;

import org.lwjgl.opengl.GL30;

public class Vao {

	private int id;
	
	public Vao() {
		
	}
	
	public Vao generate() {
		id = GL30.glGenVertexArrays();
		return this;
	}
	
	public Vao bind() {
		GL30.glBindVertexArray(id);
		return this;
	}
	
	public Vao unbind() {
		GL30.glBindVertexArray(0);
		return this;
	}
	
	public Vao delete() {
		GL30.glDeleteVertexArrays(id);
		return this;
	}
	
	public int getId() {
		return id;
	}
}
