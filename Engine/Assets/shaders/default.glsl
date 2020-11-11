#type vertex
#version 330 core

layout(location=0)in vec3 aPos;
layout(location=1)in vec4 aColor;
layout(location=2)in vec2 aTexture;
layout(location=3)in float aTextureID;

uniform mat4 uProjection;
uniform mat4 uView;


out vec4 fColor;
out vec2 fTexture;
out float fTextureID;

void main(){
    fColor=aColor;
    fTexture=aTexture;
    fTextureID=aTextureID;
    gl_Position=uProjection*vec4(aPos, 1.0);

}
    #type fragment
    #version 330
in vec4 fColor;
in vec2 fTexture;
in float fTextureID;
uniform float uTime;
uniform sampler2D uTextures[8];
out vec4 color;

void main(){
    //  float noise=fract(sin(dot(fColor.xy,vec2(12.9898,78.233)))*43758.5453);

    int id =int(fTextureID);
    if (id>0){
        color=fColor*texture(uTextures[id], fTexture);



    }
    else {
        color=fColor;

    }

}
