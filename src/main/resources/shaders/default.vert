#version 330 core

in vec3 position;
in vec3 color;

uniform vec2 offset;

out vec3 vColor;

void main() {
    gl_Position = vec4(position.xy + offset, position.z, 1.0);
    vColor = color;
}