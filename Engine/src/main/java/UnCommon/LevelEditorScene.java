package UnCommon;

import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class LevelEditorScene extends Scene {
    private String vertexShaderSrc="#version 330 core\n" +
            "\n" +
            "layout(location=0)in vec3 aPos;\n" +
            "layout(location=1)in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor=aColor;\n" +
            "    gl_Position=vec4(aPos, 1.0);\n" +
            "\n" +
            "}";
    private String fragmentShaderSrc=" #version 330\n" +
            " in vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "    color=fColor;\n" +
            "}";



    private int vertexId,fragmentId,shaderProgram;

    private float [] vertexArray={
            //position                  // color
             0.5f, -0.5f,  0.0f,        1.0f,0.0f,0.0f,1.0f,//BottomRight 0
            -0.5f,  0.5f,  0.0f,        0.0f,1.0f,0.0f,1.0f,//top left    1
             0.5f,  0.5f,  0.0f,        0.0f,0.0f,1.0f,1.0f,//Top right   2
            -0.5f, -0.5f,  0.0f,        1.0f,1.0f,0.0f,1.0f,//Bottomleft  3

    };
    private int[]indicesArray={

            2,1,0,//top right triangle
            0,1,3//bottom left triangle



    };
    private int vaoID,vboID,eboID;
    public LevelEditorScene() {


    }
    @Override
    public void init(){
        //================================================
        //Compile and link shaders
        //=================================================

        //first load and compile the vertex shader

        vertexId=glCreateShader(GL_VERTEX_SHADER);
        //link the shader source
        glShaderSource(vertexId,vertexShaderSrc);
        //compile vertex shader
        glCompileShader(vertexId);

        //check for errors in complilation
        int success=glGetShaderi(vertexId,GL_COMPILE_STATUS);

        if(success==GL_FALSE){
            int len=glGetShaderi(vertexId,GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexId,len));
            assert false:"";
        }


        fragmentId=glCreateShader(GL_FRAGMENT_SHADER);
        //link the shader source
        glShaderSource(fragmentId,fragmentShaderSrc);
        //compile frgmentshader
        glCompileShader(fragmentId);

        //check for errors in complilation
         success=glGetShaderi(fragmentId,GL_COMPILE_STATUS);

        if(success==GL_FALSE){
            int len=glGetShaderi(fragmentId,GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentId,len));
            assert false:"";
        }


        //Link shaders to program and check for errors

        shaderProgram=glCreateProgram();
        glAttachShader(shaderProgram,vertexId);
        glAttachShader(shaderProgram,fragmentId);
        glLinkProgram(shaderProgram);

        success=glGetProgrami(shaderProgram,GL_LINK_STATUS);

        if(success==GL_FALSE){
            int len=glGetProgrami(shaderProgram,GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tlinking  shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgram,len));
            assert false:"";

        }

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
        int floatSizeBytes=4; //size of a float =4 bytes
        int vertexSizeBytes=(positionSize+colorSize)*floatSizeBytes;

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
                positionSize*floatSizeBytes);//offset
        glEnableVertexAttribArray(1);












    }

    @Override
    public void update(float dt) {

        //Bind our shader program
        glUseProgram(shaderProgram);
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
        glUseProgram(0);




    }
}

