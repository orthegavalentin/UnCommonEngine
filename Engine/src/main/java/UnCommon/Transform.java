package UnCommon;

import org.joml.Vector2f;

public class Transform {
    public Vector2f translate;
    public Vector2f scale;

    public Transform(){
        init(new Vector2f(),new Vector2f());


    }
    public Transform(Vector2f translate){
        init(translate,new Vector2f());


    }
    public Transform(Vector2f translate,Vector2f scale){
        init(translate,scale);


    }

    public void init(Vector2f translate,Vector2f scale){
        this.translate=translate;
        this.scale=scale;


    }

}
