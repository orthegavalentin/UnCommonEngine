package UnCommon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.RigidBody;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
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
        this.camera = new Camera(new Vector2f(-250,0));
        if(levelLoaded){
            this.activegameObject=gameObjects.get(0);
            return;
        }
        sprites=AssetPool.getSpriteSheet("Assets/images/spritesheet.png");



        obj1=new GameObject("object 1",new Transform(new Vector2f(100,100),new Vector2f(100,100)),2);
       SpriteRenderer obj1SpriteRenderer=new SpriteRenderer();
       obj1SpriteRenderer.setColor(new Vector4f(1,0,0,1));
        obj1.addComponent(obj1SpriteRenderer);
        obj1.addComponent(new RigidBody());
       this.addGameObjectToScene(obj1);
       this.activegameObject=obj1;
         obj2=new GameObject("object 2",new Transform(new Vector2f(150,100),new Vector2f(100,100)),1);
        SpriteRenderer obj2SpriteRenderer=new SpriteRenderer();
        obj2SpriteRenderer.setSprite(sprites.getSprite(0));
        obj2.addComponent(obj2SpriteRenderer);
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
        //System.out.println("fps= "+ (1.0f/dt));



        for (GameObject o : this.gameObjects) {
            o.update(dt);


        }
        this.renderer.render();


    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("LeGM back at it ");
        ImGui.end();

    }
}

