package UnCommon;

import java.awt.event.KeyEvent;

import static org.lwjgl.opengl.GL20.*;

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
            System.out.println(glGetProgramInfoLog(fragmentId,len));
            assert false:"";

        }




    }

    @Override
    public void update(float dt) {


    }
}

