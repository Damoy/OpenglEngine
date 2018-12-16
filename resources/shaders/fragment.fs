#version 330

in vec2 passTextureCoordinates;
out vec4 color;

uniform sampler2D modelTexture;

void main(){
	color = texture(modelTexture, passTextureCoordinates);
}