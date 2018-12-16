package com.damoy.unknown.core.model;

import com.damoy.unknown.core.opengl.Vao;

public final class BlockModel {

	private BlockModel() {}
	
	public final static float[] vertices = {			
			-0.5f,0.5f,0,	
			-0.5f,-0.5f,0,	
			0.5f,-0.5f,0,	
			0.5f,0.5f,0,		
			
			-0.5f,0.5f,1,	
			-0.5f,-0.5f,1,	
			0.5f,-0.5f,1,	
			0.5f,0.5f,1,
			
			0.5f,0.5f,0,	
			0.5f,-0.5f,0,	
			0.5f,-0.5f,1,	
			0.5f,0.5f,1,
			
			-0.5f,0.5f,0,	
			-0.5f,-0.5f,0,	
			-0.5f,-0.5f,1,	
			-0.5f,0.5f,1,
			
			-0.5f,0.5f,1,
			-0.5f,0.5f,0,
			0.5f,0.5f,0,
			0.5f,0.5f,1,
			
			-0.5f,-0.5f,1,
			-0.5f,-0.5f,0,
			0.5f,-0.5f,0,
			0.5f,-0.5f,1
		};
		
	public final static float[] colors = new float[] {
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
			
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
			
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
			
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
			
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
			
			0.5f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.5f,
			0.0f, 0.5f, 0.5f,
		};
		
	public final static int[] indices = {
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,5,6,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22

		};
	
	public final static float[] textureCoords = {
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0
	};
	
	public final static Model INSTANCE = new Model(new Vao().generate(), vertices, textureCoords, indices);
}
