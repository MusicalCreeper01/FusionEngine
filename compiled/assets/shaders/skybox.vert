#version 110

/*layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;*/

//out vec2 outTexCoord;

/*uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;*/

void main()
{
    gl_Position = ftransform(); //projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    gl_TexCoord[0] = gl_MultiTexCoord0;
}