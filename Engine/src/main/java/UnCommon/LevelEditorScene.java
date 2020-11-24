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
    private GameObject obj1;
    private GameObject obj2;
    SpriteSheet sprites;



    public LevelEditorScene() {


    }

    @Override
    public void init() {
        loadResources();
        sprites=AssetPool.getSpriteSheet("Assets/images/spritesheet.png");

       this.camera = new Camera(new Vector2f(-250,0));
        obj1=new GameObject("object 1",new Transform(new Vector2f(100,100),new Vector2f(100,100)),2);
       obj1.addComponent((new SpriteRenderer(new Sprite(AssetPool.getTexture("Assets/images/blendImage1.png")))));
       this.addGameObjectToScene(obj1);
         obj2=new GameObject("object 2",new Transform(new Vector2f(150,100),new Vector2f(100,100)),1);
        obj2.addComponent((new SpriteRenderer(new Sprite(AssetPool.getTexture("Assets/images/blendImage2.png")))));
        this.addGameObjectToScene(obj2);



    }
    private void loadResources(){
        AssetPool.getShader("Assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("Assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/spritesheet.png"),
                        16,16,28,0));

    }
    //animation test
    private int spriteIndex=15;
    private float spriteFlipTime=0.8f;
    private float spriteFlipTimeleft=0.0f;

    @Override
    public void update(float dt) {


        for (GameObject o : this.gameObjects) {
            o.update(dt);


        }
        this.renderer.render();


    }
}

