package UnCommon;

import Renderer.Shader;
import Renderer.Texture;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;


public class LevelEditorScene extends Scene {



    public LevelEditorScene() {


    }

    @Override
    public void init() {
       this.camera = new Camera(new Vector2f(-250,0));
       GameObject obj1=new GameObject("object 1",new Transform(new Vector2f(100,100),new Vector2f(32,32)));
       obj1.addComponent((new SpriteRenderer((AssetPool.getTexture("Assets/images/mario.png")))));
       this.addGameObjectToScene(obj1);
        loadResources();


    }
    private void loadResources(){
        AssetPool.getShader("Assets/shaders/default.glsl");

    }

    @Override
    public void update(float dt) {
       System.out.println("fps= "+ (1.0f/dt));

        for (GameObject o : this.gameObjects) {
            o.update(dt);


        }
        this.renderer.render();


    }
}

