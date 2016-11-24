#version 110

/*in vec2 outTexCoord;
in vec3 mvPos;
out vec4 fragColor;*/

uniform sampler2D texture_sampler;
uniform vec3 ambientLight;

void main()
{
    gl_FragColor = vec4(ambientLight, 1) * texture2D(texture_sampler, gl_TexCoord[0].st);
}