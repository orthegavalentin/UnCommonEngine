package UnCommon;

import Renderer.Shader;
import Renderer.Texture;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;


public class LevelEditorScene extends Scene {



    public LevelEditorScene() {


    }

    @Override
    public void init() {
        loadResources();
        SpriteSheet sprites=AssetPool.getSpriteSheet("Assets/images/spritesheet.png");

       this.camera = new Camera(new Vector2f(-250,0));
       GameObject obj1=new GameObject("object 1",new Transform(new Vector2f(100,100),new Vector2f(32,32)));
       obj1.addComponent((new SpriteRenderer(sprites.getSprite(14))));
       this.addGameObjectToScene(obj1);
        GameObject obj2=new GameObject("object 2",new Transform(new Vector2f(150,100),new Vector2f(64,64)));
        obj2.addComponent((new SpriteRenderer(sprites.getSprite(15))));
        this.addGameObjectToScene(obj2);



    }
    private void loadResources(){
        AssetPool.getShader("Assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("Assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/spritesheet.png"),
                        16,16,26,0));

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

