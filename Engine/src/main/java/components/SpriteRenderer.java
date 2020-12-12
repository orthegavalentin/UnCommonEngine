package components;

import Renderer.Texture;
import UnCommon.Transform;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color=new Vector4f(1,1,1,1);

    private Sprite sprite=new Sprite();

    //get notified when sprite changes position;
    // transient tells Json values that we dont want to serialize
    private transient Transform lastTrasform;
    private transient boolean isDirty=true;

   /* public SpriteRenderer(Vector4f color) {

        this.sprite = new Sprite(null);
        this.color = color;
        isDirty=true;

    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
      isDirty=true;
    }*/

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

    @Override
    public void imgui() {
        float[] imcolor={ color.x,color.y,color.z,color.w};
        if(ImGui.colorPicker4("Color picker",imcolor)){
            this.color.set(imcolor[0],imcolor[1],imcolor[2],imcolor[3]);
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

    public void setTextur(Texture texture){
        this.sprite.setTexture(texture);
    }

}
