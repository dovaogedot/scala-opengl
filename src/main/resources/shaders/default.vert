#version 330 core

in vec3 position;
in vec3 color;
in vec3 normal;
in vec3 tangent;
in vec2 uv;

// uniform vec2 offset;

out vec3 vPosition;
out vec3 vColor;
out vec3 vNormal;
out vec3 vTangent;
out vec2 vUv;

void main() {
    gl_Position = vec4(position, 1.0);
    // gl_Position = vec4(position.xy + offset, position.z, 1.0);
    vPosition = position;
    vColor = color;
    vNormal = normal;
    vTangent = tangent;
    vUv = uv;
}