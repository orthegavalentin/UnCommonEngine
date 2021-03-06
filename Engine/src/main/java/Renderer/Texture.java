package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {
    private String filepath;
    private transient int textID;
    private int height, width;

    public Texture(int width, int height) {
        this.filepath = "generated";
        textID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, 0);


    }

    public Texture() {
        textID = -1;
        width = -1;
        height = -1;
    }

    /* public Texture(String filepath) {


     }*/
    public void init(String filepath) {
        this.filepath = filepath;

        //genrate texture on GPU

        textID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textID);

        // set Texture parameters
        //Repeat texture in both directions

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        //when stretxhing pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //when shrinking pixelate also
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
// Image loading library
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            //uploading texture to gpu
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);


            } else {
                assert false : "Error (Txture) unknown number of texture channel " + channels.get(0) + " ";

            }

        } else {

            assert false : "Error (Txture) could not load image" + filepath + " ";

        }
        stbi_image_free(image);


    }


    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textID);
    }

    public void unBind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getId() {
        return this.textID;
    }

    public String getFilepath() {
        return this.filepath;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Texture)) return false;
        Texture otex = (Texture) obj;
        return otex.getWidth() == this.getWidth() && otex.getHeight() == this.getHeight()
                && otex.getId() == this.textID && otex.getFilepath().equals(this.filepath);
    }
}
