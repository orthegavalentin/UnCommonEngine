package Renderer;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

    private Texture texture;
    private int fboID=0;

    public int getTextureId() {
        return fboID;
    }


    public int getFboID() {
        return fboID;
    }



    public FrameBuffer(int width , int height){
        //generate frame buffer
        fboID=glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER,fboID);

        //create the texture to render the data to ,and attach it to our framebuffer
        this.texture=new Texture(width,height);
        glFramebufferTexture2D(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0,GL_TEXTURE_2D,this.texture.getId(),0);

    //create renderbuffer and sotre depth info
        int rboID=glGenRenderbuffers();
        glBindBuffer(GL_RENDERBUFFER,rboID);
        glRenderbufferStorage(GL_RENDERBUFFER,GL_DEPTH_COMPONENT32,width,height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,GL_RENDERBUFFER,rboID);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) !=GL_FRAMEBUFFER_COMPLETE){
            assert false :"error :Framebuffer is not complete";


        }
        glBindFramebuffer(GL_FRAMEBUFFER,0);


    }
    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER,fboID);
    }
    public void unBind(){
        glBindFramebuffer(GL_FRAMEBUFFER,0);
    }


}