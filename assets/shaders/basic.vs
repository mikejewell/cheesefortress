#version 150 core

in vec2 in_pos;
in vec3 in_color;
in vec2 in_texCoord;

out vec3 pass_color;
out vec2 pass_texCoord;

void main(void) {
	gl_Position = vec4(in_pos, 0, 0);
	
	pass_color = in_color;
	pass_texCoord = in_texCoord;
}