package com.damoy.unknown.graphics.shaders;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import com.damoy.unknown.utils.Utils;

public class ShaderProgram {

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	private Map<String, Integer> uniforms;
	
	public ShaderProgram() {
		programId = GL20.glCreateProgram();
		
		if(programId == 0) {
			throw new IllegalStateException("Could not generate shader program");
		}
		
		uniforms = new HashMap<>();
	}
	
	public ShaderProgram vertex(String url) {
		try {
			createVertexShader(Utils.loadResource(url));
			return this;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	public ShaderProgram fragment(String url) {
		try {
			createFragmentShader(Utils.loadResource(url));
			return this;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}
	
	public ShaderProgram generate() {
		createUniform("projectionMatrix");
		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		return this;
	}
	
	public ShaderProgram binding() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoordinates");
		return this;
	}
	
	public ShaderProgram bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programId, attribute, variableName);
		return this;
	}
	
	public void createUniform(String uniformName) {
		int uniformLocation = GL30.glGetUniformLocation(programId, uniformName);
		
		if(uniformLocation < 0) {
			throw new RuntimeException("Could not find uniform: " + uniformName);
		}
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	public void setProjectionMatrixUniform(Matrix4f value) {
		setUniform("projectionMatrix", value);
	}
	
	public void setTransformationMatrixUniform(Matrix4f value) {
		setUniform("transformationMatrix", value);
	}
	
	public void setViewMatrixUniform(Matrix4f value) {
		setUniform("viewMatrix", value);
	}
	
	private MemoryStack stack = MemoryStack.stackPush();
	private FloatBuffer fb = stack.mallocFloat(16);
	public void setUniform(String uniformName, Matrix4f value) {
		value.get(fb);
		GL30.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
	}
	
	private int createShader(String shaderCode, int shaderType) {
		int shaderId = GL20.glCreateShader(shaderType);
		
		if(shaderId == 0) {
			throw new IllegalStateException("Could not create [" + shaderCode + "," +  shaderType + "] shader.");
		}
		
		GL20.glShaderSource(shaderId, shaderCode);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
			throw new IllegalStateException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024));
		}
		
		GL20.glAttachShader(programId, shaderId);
		return shaderId;
	}
	
	public void createVertexShader(String shaderCode) {
		vertexShaderId = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String shaderCode) {
		fragmentShaderId = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
	}
	
	public ShaderProgram link() {
		GL20.glLinkProgram(programId);
		
		if(GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
			throw new IllegalStateException("Error linking Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
		}
		
		if(vertexShaderId != 0) {
			GL20.glDetachShader(programId, vertexShaderId);
		}
		
		if(fragmentShaderId != 0) {
			GL20.glDetachShader(programId, fragmentShaderId);
		}
		
		GL20.glValidateProgram(programId);
		
		if(GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
		}
		
		return this;
	}
	
	public void bind() {
		GL20.glUseProgram(programId);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void terminate() {
		unbind();
		if(programId != 0)
			GL20.glDeleteProgram(programId);
	}

	public int getProgramId() {
		return programId;
	}

	public int getVertexShaderId() {
		return vertexShaderId;
	}

	public int getFragmentShaderId() {
		return fragmentShaderId;
	}
	
}