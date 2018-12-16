package com.damoy.unknown.core.opengl;

import org.lwjgl.opengl.GL30;

public class Vbo {
	
	private int id;
	private int target;
	
	public final static int GL_ARRAY_BUFFER = GL30.GL_ARRAY_BUFFER;
	public final static int GL_ELEMENT_ARRAY_BUFFER = GL30.GL_ELEMENT_ARRAY_BUFFER;
	
	public Vbo(int target) {
		this.target = target;
	}
	
	public Vbo generate() {
		id = GL30.glGenBuffers();
		return this;
	}
	
	public Vbo bind() {
		 GL30.glBindBuffer(target, id);
		return this;
	}
	
	public Vbo unbind() {
		GL30.glBindBuffer(target, 0);
		return this;
	}
	
	public Vbo delete() {
		unbind();
		GL30.glDeleteBuffers(id);
		return this;
	}
	
	public int getId() {
		return id;
	}
}
