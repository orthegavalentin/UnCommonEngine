package UnCommon;

import Renderer.Shader;
import Renderer.Texture;
import components.FontRenderer;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import util.Time;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class LevelEditorScene extends Scene {


    private int vertexId,fragmentId,shaderProgram;
    private Texture texture;
    GameObject testObj;

    private float [] vertexArray={
            //position                      // color
             100f,    0f,  0.0f,        1.0f,0.0f,0.0f,1.0f,          1,1, //BottomRight 0
             0.0f,  100f,  0.0f,        0.0f,1.0f,0.0f,1.0f,          0,0, //top left    1
             100f,  100f,  0.0f,        1.0f,0.0f,1.0f,1.0f,          1,0, //Top right   2
               0f,    0f,  0.0f,        1.0f,1.0f,0.0f,1.0f,          0,1//Bottomleft  3

    };
    private int[]indicesArray={

            2,1,0,//top right triangle
            0,1,3//bottom left triangle



    };
    private int vaoID,vboID,eboID;
    private Shader shader;
    private boolean firstTime=true;

    public LevelEditorScene() {




    }
    @Override
    public void init(){
        System.out.println("creating test Object");

        this.testObj=new GameObject("test Object");
        this.testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);
        this.camera=new Camera(new Vector2f());
        shader=new Shader("Assets/shaders/default.glsl");
        shader.compileAndLink();
        this.texture=new Texture("Assets/images/lena.png");

      //======================================
      // Generate VAO,VBO and EBO bufferobjects , and send them to GPU
      //======================================

        vaoID=glGenVertexArrays();
        glBindVertexArray(vaoID);
        // create a float buffer of vertices
        FloatBuffer vertexBuffer= BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();//orienting the right way

        //create VBO upload the vertex buffer
        vboID= glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer,GL_STATIC_DRAW);

        //create the indices and upload
        IntBuffer elementBuffer= BufferUtils.createIntBuffer(indicesArray.length);
        elementBuffer.put(indicesArray).flip();//orienting the right way


        eboID= glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,elementBuffer,GL_STATIC_DRAW);

        //add attrib pointers to tell the GPU how to implment the data in buffers

        int positionSize =3;//x y z
        int colorSize=4;// r g b a
        int uvSize=2;

        int vertexSizeBytes=(positionSize+colorSize+uvSize)*Float.BYTES;

        glVertexAttribPointer(0,
                positionSize,
                GL_FLOAT,
                false,
                vertexSizeBytes,
                0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1,
                colorSize,//size
                GL_FLOAT,//type
                false,
                vertexSizeBytes,//stride how long until the next vertex
                positionSize*Float.BYTES);//offset
        glEnableVertexAttribArray(1);
       glVertexAttribPointer(2,
                uvSize,//size
                GL_FLOAT,//type
                false,
                vertexSizeBytes,//stride how long until the next vertex
                (positionSize+colorSize)*Float.BYTES);//offset
        glEnableVertexAttribArray(2);












    }

    @Override
    public void update(float dt) {
        camera.position.x -= dt*80.0f;
       // System.out.println("running at"+(1.0f/dt)+"fps");

         shader.use();

         shader.uploadTexture("uTexture",0);
       glActiveTexture(GL_TEXTURE0);
        texture.bind();

         shader.uploadMat4f("uProjection",camera.getProjectionMatrix());
        shader.uploadMat4f("uView",camera.getViewMatrix());
        shader.uploadFloat("uTime",(float) glfwGetTime());
        //bind the VAO that we're using
        glBindVertexArray(vaoID);

        //Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES,indicesArray.length,GL_UNSIGNED_INT,0);


        //unbind everything after drawing
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detach();

        if(firstTime) {
            System.out.println("Creting object at runtime");
            GameObject go = new GameObject("Game Object");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime=false;
        }

            for(GameObject o:gameObjects){
                o.update(dt);


            }


    }
}

