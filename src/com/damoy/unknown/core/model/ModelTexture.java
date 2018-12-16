package com.damoy.unknown.core.model;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

// TODO refactor - https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/graphic/Texture.java
public class ModelTexture {

	private String filePath;
	private int id;
	private int width;
	private int height;
	private int channels;
	
	public ModelTexture() {}

	public ModelTexture(String filePath, int id, int width, int height, int channels) {
		this.filePath = filePath;
		this.id = id;
		this.width = width;
		this.height = height;
		this.channels = channels;
	}

	public void bind() {
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
	}

	/**
	 * Sets a parameter of the texture.
	 *
	 * @param name  Name of the parameter
	 * @param value Value to set
	 */
	public void setParameter(int name, int value) {
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, name, value);
	}

	/**
	 * Uploads image data with specified width and height.
	 *
	 * @param width  Width of the image
	 * @param height Height of the image
	 * @param data   Pixel data of the image
	 */
	public void uploadData(int width, int height, ByteBuffer data) {
		uploadData(GL20.GL_RGBA8, width, height, GL20.GL_RGBA, data);
	}

	/**
	 * Uploads image data with specified internal format, width, height and image
	 * format.
	 *
	 * @param internalFormat Internal format of the image data
	 * @param width          Width of the image
	 * @param height         Height of the image
	 * @param format         Format of the image data
	 * @param data           Pixel data of the image
	 */
	public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
		GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL20.GL_UNSIGNED_BYTE, data);
	}

	/**
	 * Delete the texture.
	 */
	public void delete() {
		GL20.glDeleteTextures(id);
	}

	/**
	 * Load texture from file.
	 *
	 * @param path File path of the texture
	 *
	 * @return Texture from specified file
	 */
	public static ModelTexture loadTexture(String path) {
		ByteBuffer image;
		int width, height;
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			/* Load image */
			STBImage.stbi_set_flip_vertically_on_load(true);
			image = STBImage.stbi_load(path, w, h, comp, 4);
			
			if (image == null) {
				throw new RuntimeException(
						"Failed to load a texture file!" + System.lineSeparator() + STBImage.stbi_failure_reason());
			}

			width = w.get();
			height = h.get();
		}

		return createTexture(width, height, image);
	}
	
	/**
	 * Creates a texture with specified width, height and data.
	 *
	 * @param width  Width of the texture
	 * @param height Height of the texture
	 * @param data   Picture Data in RGBA format
	 *
	 * @return Texture from the specified data
	 */
	private static ModelTexture createTexture(int width, int height, ByteBuffer data) {
		ModelTexture texture = new ModelTexture();
		texture.setWidth(width);
		texture.setHeight(height);
		texture.bind();
		texture.setParameter(GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_BORDER);
		texture.setParameter(GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_BORDER);
		texture.setParameter(GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
		texture.setParameter(GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
		texture.uploadData(GL20.GL_RGBA8, width, height, GL20.GL_RGBA, data);
		return texture;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

}