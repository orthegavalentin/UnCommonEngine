package components;

import Renderer.Texture;
import org.joml.Vector2f;

public class Sprite {
    private float height,width;

    private Texture texture=null;
    private Vector2f[] texCoords={

            new Vector2f(1,0),
            new Vector2f(1,1),
            new Vector2f(0,1),
            new Vector2f(0,0)

    };



    public Vector2f[] getTexCoords(){


        return this.texCoords;
    }
    public Texture getTexture(){
        return this.texture;

    }


    public void setTexture(Texture texture) {
        this.texture=texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords=texCoords;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    public int getTexID(){
        return texture==null?-1:texture.getId();
    }
}
