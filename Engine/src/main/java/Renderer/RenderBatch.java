package Renderer;

import UnCommon.Window;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;


import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch  implements Comparable<RenderBatch>{
    //Vertex
    //=======
    //Pos                    color                         tex coords      tex id
    //float,float,           float,float,float,float       float,float     float
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_COORDS_SIZE = 2;
    private final int TEX_ID_SIZE = 1;


    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private List<Texture> textures;
    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;
    private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};
    private int zIndex;

    public RenderBatch(int maxBatchSize,int zIndex) {
        //avoiding creation of a bunch of shader for nothing
        shader = AssetPool.getShader("assets/shaders/default.glsl");
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;
        this.textures = new ArrayList<>();
        this.zIndex=zIndex;

        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
        this.numSprites = 0;
        this.hasRoom = true;


    }


    public void start() {
// generate and bind Vertex Array object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);
        // create and upload indices buffer

        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


        //Enable the buffer attribute pointer
        glVertexAttribPointer(
                0,
                POS_SIZE,
                GL_FLOAT,
                false,
                VERTEX_SIZE_BYTES,
                POS_OFFSET
        );
        glEnableVertexAttribArray(0);

        //Enable the buffer attribute pointer
        glVertexAttribPointer(
                1,
                COLOR_SIZE,
                GL_FLOAT,
                false,
                VERTEX_SIZE_BYTES,
                COLOR_OFFSET
        );
        glEnableVertexAttribArray(1);

        //Enable the buffer attribute pointer
        glVertexAttribPointer(
                2,
                TEX_COORDS_SIZE,
                GL_FLOAT,
                false,
                VERTEX_SIZE_BYTES,
                TEX_COORDS_OFFSET
        );
        glEnableVertexAttribArray(2);


        //Enable the buffer attribute pointer
        glVertexAttribPointer(
                3,
                TEX_ID_SIZE,
                GL_FLOAT,
                false,
                VERTEX_SIZE_BYTES,
                TEX_ID_OFFSET
        );
        glEnableVertexAttribArray(3);


    }

    private int[] generateIndices() {
        //6 indices per quad(3 per triangle)
        int[] elements = new int[6 * maxBatchSize];

        for (int i = 0; i < maxBatchSize; i++) {
            loadELementIndices(elements, i);
        }

        return elements;
    }


    public void addSprite(SpriteRenderer spr) {
        //get index and add renderObject
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;
        if (spr.getTexture() != null) {
            if (!textures.contains(spr.getTexture())) {
                textures.add(spr.getTexture());
            }
        }
        //add properties to local vertices array
        loadVertexProperties(index);
        if (numSprites >= this.maxBatchSize) {

            this.hasRoom = false;
        }

    }


    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites[index];
        // find offset within array (4 vertices per sprite)
        int offset = 4 * index * VERTEX_SIZE;


        Vector4f color = sprite.getColor();
        Vector2f[] texCords = sprite.getTexCoords();
        int texId = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == sprite.getTexture()) {
                    texId = i + 1;
                    break;


                }


            }
        }

        //add vertice with the appropriate properties
        float xAdd = 1.0f;
        float yAdd = 1.0f;

        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;

            } else if (i == 3) {
                yAdd = 1.0f;

            }
            //load position
            vertices[offset] = sprite.gameObject.transform.translate.x
                    + (xAdd * sprite.gameObject.transform.scale.x);
            vertices[offset + 1] = sprite.gameObject.transform.translate.y
                    + (yAdd * sprite.gameObject.transform.scale.y);
            //load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            //load Texture coordiantes
            vertices[offset + 6] = texCords[i].x;
            vertices[offset + 7] = texCords[i].y;


            //load texture id
            vertices[offset + 8] = texId;


            offset += VERTEX_SIZE;


        }


    }


    public void render() {
        boolean rebufferData = false;
        for (int i = 0; i < numSprites; i++) {
            SpriteRenderer spriteRenderer = sprites[i];
            if (spriteRenderer.isDirty()) {
                loadVertexProperties(i);
                spriteRenderer.setClean();
                rebufferData = true;

            }

        }

        if (rebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }


        //use our shader

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();


        }
        shader.uploadIntArray("uTextures", texSlots);
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        for (int i = 0; i < textures.size(); i++) {

            textures.get(i).unBind();


        }
        shader.detach();


    }


    private void loadELementIndices(int[] elements, int index) {
        int offsetArrayindex = 6 * index;
        int offset = 4 * index;
        //3 2 0 0 2 1       7 6 4 4 6 5
        //triangle 1
        elements[offsetArrayindex] = offset + 3;
        elements[offsetArrayindex + 1] = offset + 2;
        elements[offsetArrayindex + 2] = offset + 0;
        //triangle 2
        elements[offsetArrayindex + 3] = offset + 0;
        elements[offsetArrayindex + 4] = offset + 2;
        elements[offsetArrayindex + 5] = offset + 1;


    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    public boolean hasTextureRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasTexture(Texture tex) {
        return this.textures.contains(tex);
    }

    public int getzIndex(){
        return this.zIndex;
    }


    @Override
    public int compareTo(RenderBatch o) {

        return Integer.compare(this.zIndex,o.getzIndex());
    }
}
