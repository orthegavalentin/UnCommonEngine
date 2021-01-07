package physics2D.components;

import components.Component;
import org.joml.Vector2f;
import renderer.DebugDraw;

public class Box2DCollider extends Collider {
    private Vector2f halfSize=new Vector2f(1);
    private Vector2f origin=new Vector2f(1);

    public Vector2f getOrigin() {
        return origin;
    }

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public void setHalfSize(Vector2f halfSize) {
        this.halfSize = halfSize;
    }

    @Override
    public  void editorUpdate(float dt){
        Vector2f center=new Vector2f(this.gameObject.transform.translate).add(this.offset);
        DebugDraw.addBox2D(center,this.halfSize,this.gameObject.transform.rotation);

    }
}
