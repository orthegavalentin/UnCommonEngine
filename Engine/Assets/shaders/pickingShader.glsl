#type vertex
#version 330 core

layout(location=0)in vec3 aPos;
layout(location=1)in vec4 aColor;
layout(location=2)in vec2 aTexture;
layout(location=3)in float aTextureID;
layout(location=4)in float aEntityId;

uniform mat4 uProjection;
uniform mat4 uView;


out vec4 fColor;
out vec2 fTexture;
out float fTextureID;
out float fEntityId;

void main(){
    fColor=aColor;
    fTexture=aTexture;
    fTextureID=aTextureID;
    fEntityId=aEntityId;
    gl_Position=uProjection*uView*vec4(aPos, 1.0);

}
    #type fragment
    #version 330
in vec4 fColor;
in vec2 fTexture;
in float fTextureID;
in float fEntityId;
uniform float uTime;
uniform sampler2D uTextures[8];
out vec3 color;

void main(){
    //  float noise=fract(sin(dot(fColor.xy,vec2(12.9898,78.233)))*43758.5453);
     vec4 texColor =vec4(1,1,1,1);

    if (fTextureID>0){
        int id =int(fTextureID);
        texColor=fColor*texture(uTextures[id], fTexture);



    }
    //make sure we are able to click in the middle of a donut
    if(texColor.a<0.5){
        discard;

    }
    color =vec3(fEntityId,fEntityId,fEntityId);


}
