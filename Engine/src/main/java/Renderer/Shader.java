package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {
    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private String filePath;
    String[] splitString;

    public Shader(String filepath) {
        this.filePath = filepath;

        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            splitString = source.split("(#type)( )+([a-zA-Z]+)");
            //find the vertex shader
            int index = source.indexOf("#type") + 6;

            int eol = source.indexOf("\n", index);

            String firstPattern = source.substring(index, eol).trim();

            //find the fragment shader
            index = source.indexOf("#type", eol) + 6;//find the next #type starting from the eol
            eol = source.indexOf("\n", index);
            String secondPattern = source.substring(index, eol).trim();
            System.out.println("eol="+secondPattern);

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];

            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];

            } else {
                throw new IOException("unexpected token " + firstPattern + " ");

            }
            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];

            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];

            } else {
                throw new IOException("unexpected token " + secondPattern + " ");

            }


        } catch (IOException e) {
            e.printStackTrace();
            assert false : "error :could not open for shader" + filepath + "";
        }





    }

    public void compileAndLink() {
        //================================================
        //Compile and link shaders
        //=================================================

        int vertexId = glCreateShader(GL_VERTEX_SHADER);
        //link the shader source
        glShaderSource(vertexId,vertexSource);
        //compile vertex shader
        glCompileShader(vertexId);

        //check for errors in complilation
        int success=glGetShaderi(vertexId,GL_COMPILE_STATUS);

        if(success==GL_FALSE){
            int len=glGetShaderi(vertexId,GL_INFO_LOG_LENGTH);
            System.out.println("Error: "+filePath+"\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexId,len));
            assert false:"";
        }


        int fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        //link the shader source
        glShaderSource(fragmentId,fragmentSource);
        //compile frgmentshader
        glCompileShader(fragmentId);

        //check for errors in complilation
        success=glGetShaderi(fragmentId,GL_COMPILE_STATUS);

        if(success==GL_FALSE){
            int len=glGetShaderi(fragmentId,GL_INFO_LOG_LENGTH);
            System.out.println("Error:" +filePath+"\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentId,len));
            assert false:"";
        }


        //Link shaders to program and check for errors
        shaderProgramID=glCreateProgram();
        glAttachShader(shaderProgramID,vertexId);
        glAttachShader(shaderProgramID,fragmentId);
        glLinkProgram(shaderProgramID);

        success=glGetProgrami(shaderProgramID,GL_LINK_STATUS);

        if(success==GL_FALSE){
            int len=glGetProgrami(shaderProgramID,GL_INFO_LOG_LENGTH);
            System.out.println("Error:" +filePath+"\n\tlinking  shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID,len));
            assert false:"";

        }


    }

    public void use() {
        //Bind our shader program
        glUseProgram(shaderProgramID);


    }

    public void detach() {
        glUseProgram(0);


    }

    public void uploadMat4f(String varName, Matrix4f mat4){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        FloatBuffer matBuffer= BufferUtils.createFloatBuffer(16);//since it is a 4*4 matrix
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation,false,matBuffer);


    }
    public void uploadMat3f(String varName, Matrix3f mat3){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        FloatBuffer matBuffer= BufferUtils.createFloatBuffer(9);//since it is a 4*4 matrix
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation,false,matBuffer);


    }


    public void uploadVec4f(String varName, Vector4f vec){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform4f(varLocation,vec.x,vec.y,vec.z,vec.w);


    }
    public void uploadVec3f(String varName, Vector3f vec){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform3f(varLocation,vec.x,vec.y,vec.z);


    }
    public void uploadVec2f(String varName, Vector2f vec){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform2f(varLocation,vec.x,vec.y);


    }
    public void uploadFloat(String varName, float val){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1f(varLocation,val);


    }

    public void uploadInt(String varName, int val){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1i(varLocation,val);


    }
    public void uploadTexture(String varName, int slot){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1i(varLocation,slot);


    }

    public void uploadIntArray(String varName,int[]array){
        int varLocation =glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1iv(varLocation,array);



    }
}
