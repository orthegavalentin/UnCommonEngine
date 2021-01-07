package UnCommon;

import components.Component;
import editor.JImGui;
import org.joml.Vector2f;

public class Transform extends Component {
    public Vector2f translate;
    public Vector2f scale;
    public float rotation=0.0f;
    public int zIndex;

    public Transform() {
        init(new Vector2f(), new Vector2f());


    }



    public Transform(Vector2f translate) {
        init(translate, new Vector2f());


    }

    public Transform(Vector2f translate, Vector2f scale) {
        init(translate, scale);


    }

    public void init(Vector2f translate, Vector2f scale) {
        this.translate = translate;
        this.scale = scale;
        this.zIndex=0;


    }

    public Transform copy() {
        Transform t = new Transform(new Vector2f(this.translate), new Vector2f(this.scale));
        return t;
    }

    public void copy(Transform to) {
        to.translate.set(this.translate);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Transform) )
            return false;
        Transform t=(Transform)obj;
        return t.translate.equals(this.translate)&&t.translate.equals(this.scale)&&t.rotation==this.rotation
                &&t.zIndex==this.zIndex;


    }
}
