package com.damoy.unknown.core.model;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.damoy.unknown.core.opengl.Vao;
import com.damoy.unknown.utils.Utils;

public class Model {

	private Vao vao;
	private List<Integer> vboIds;
	private float[] vertices;
	private float[] texCoords;
	private int[] indices;
	private ModelTexture texture;

	public Model(Vao vao, float[] vertices, float[] texCoords, int[] indices) {
		this.vao = vao;
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.indices = indices;
		this.vboIds = new ArrayList<>();
		vao.bind();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 2, texCoords);
		vao.unbind();
	}
	
	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vboIds.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Utils.flippedIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
		int vboId = GL15.glGenBuffers();
		vboIds.add(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Utils.flippedFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public Model texturize(String textureFilePath){
		this.texture = ModelTexture.loadTexture(textureFilePath);
		return this;
	}
	
	public Vao getVao() {
		return vao;
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public float[] getTexCoords() {
		return texCoords;
	}

	public int[] getIndices() {
		return indices;
	}
	
	public int getVerticesCount() {
		return indices.length;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}