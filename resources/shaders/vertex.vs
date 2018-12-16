#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoordinates;

out vec3 passPosition;
out vec2 passTextureCoordinates;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main(){
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	passTextureCoordinates = textureCoordinates;
}