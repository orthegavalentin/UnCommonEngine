package Renderer;

import UnCommon.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import util.AssetPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static int MAX_LINES = 500;
    private static List<Line2D> lines = new ArrayList<>();

    //6 floats per vertex ,2 vertices per line
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static void start() {
        //generete the vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //create the vbo and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Enable the vertex array Attributes
        //position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //color
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        // TODO: SET LINE WIDTH
        glLineWidth(2.0f);


    }

    //to know if we have to remove a line
    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }


        //Remove dead lines
        for (int i = 0; i < lines.size(); i++) {
//check if it has ended its lifetime
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;


            }
        }

    }

    public static void draw() {
        if (lines.size() <= 0)
            return;
        int index = 0;
        for (Line2D line : lines) {

            for (int i = 0; i < 2; i++) {
                Vector2f position = i == 0 ? line.getFrom() : line.getTo();
                Vector3f color = line.getColor();


                //load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                //load color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;

                index += 6;


            }
        }
// bind the vbo
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        // for us to not upload the whole array which canup to 500 vertex we use subDATA
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));


        //use our shader

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        //bind the Vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //draw the batch

        glDrawArrays(GL_LINES, 0, lines.size() * 6 * 2);


        //disable location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);


        //unbind shader
        shader.detach();

    }


    //==========================================
    //Add line2D methods
    //===========================================
    public static void addLine2D(Vector2f from, Vector2f to) {
        // TODO: add constants for common colors
        addLine2D(from, to, new Vector3f(0, 1, 0), 2);
    }

    public static void addLine2D(Vector2f from, Vector2f to, Vector3f color) {

        addLine2D(from, to, color, 1);
    }
    public static void addLine2D(Vector2f from, Vector2f to, int lifetime) {

        addLine2D(from, to, new Vector3f(0, 1, 0), lifetime);
    }



    public static void addLine2D(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
        if (lines.size() >= MAX_LINES) return;
        DebugDraw.lines.add(new Line2D(from, to, color, lifetime));

    }


}
