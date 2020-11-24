package components;

import Renderer.Texture;
import UnCommon.Component;
import UnCommon.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;

    private Sprite sprite;

    //get notified when sprite changes position;
    private Transform lastTrasform;
    private boolean isDirty=false;

    public SpriteRenderer(Vector4f color) {

        this.sprite = new Sprite(null);
        this.color = color;
        isDirty=true;

    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
      isDirty=true;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }


    public Vector2f[] getTexCoords() {


        return sprite.getTexCoords();
    }

    @Override
    public void start() {
        this.lastTrasform=gameObject.transform.copy();


    }


    @Override
    public void update(float dt) {
        if(!(this.lastTrasform.equals(this.gameObject.transform))){
            this.gameObject.transform.copy(this.lastTrasform);
            this.isDirty=true;




        }

    }


    public Vector4f getColor() {

        return this.color;
    }

    public void setSprite(Sprite sprite) {
        this.isDirty=true;

        this.sprite = sprite;


    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty=true;
            this.color.set(color);


        }
    }

    public boolean isDirty(){
        return  this.isDirty;

    }
    public void setClean(){
        this.isDirty=false;
    }

}
