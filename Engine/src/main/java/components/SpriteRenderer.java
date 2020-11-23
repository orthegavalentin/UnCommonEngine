package components;

import Renderer.Texture;
import UnCommon.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;

    private Sprite sprite;
 public SpriteRenderer(Vector4f color ){

     this.sprite=new Sprite(null);
     this.color=color;

 }
 public SpriteRenderer(Sprite sprite){
     this.sprite=sprite;
     this.color=new Vector4f(1,1,1,1);
 }

 public Texture getTexture(){
     return sprite.getTexture();
 }


 public Vector2f[] getTexCoords(){


     return sprite.getTexCoords();
 }

    @Override
    public void start() {


    }


    @Override
    public void update(float dt) {

    }


    public Vector4f getColor(){

     return this.color;
    }


}
