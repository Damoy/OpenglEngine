package com.damoy.unknown.core.model;

import org.joml.Vector3f;

public class Block extends Entity {
	
//	public Block(Vector3f position, Vector3f rotation) {
//		super(BlockModel.INSTANCE, position, rotation);
//	}
	
	public Block(Vector3f position, String textureFilePath) {
		super(BlockModel.INSTANCE.texturize(textureFilePath), position, new Vector3f(0.0f, 0.0f, 0.0f));
	}
	
}