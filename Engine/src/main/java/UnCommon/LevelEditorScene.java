package UnCommon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.RigidBody;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;


public class LevelEditorScene extends Scene {
    private GameObject obj1;
    private GameObject obj2;
    SpriteSheet sprites,sprites2;


    public LevelEditorScene() {


    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));
        sprites = AssetPool.getSpriteSheet("Assets/images/spritesheet.png");
        sprites2 = AssetPool.getSpriteSheet("Assets/images/decorationsAndBlocks.png");


        if (levelLoaded) {
            this.activegameObject = gameObjects.get(0);
            return;
        }

        obj1 = new GameObject("object 1", new Transform(new Vector2f(100, 100), new Vector2f(100, 100)), 2);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        obj1SpriteRenderer.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1SpriteRenderer);
        obj1.addComponent(new RigidBody());
        this.addGameObjectToScene(obj1);
        this.activegameObject = obj1;
        obj2 = new GameObject("object 2", new Transform(new Vector2f(150, 100), new Vector2f(100, 100)), 1);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        obj2SpriteRenderer.setSprite(sprites.getSprite(0));
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);


    }

    private void loadResources() {
        AssetPool.getShader("Assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("Assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/spritesheet.png"),
                        16, 16, 28, 0));
        AssetPool.addSpriteSheet("Assets/images/decorationsAndBlocks.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/decorationsAndBlocks.png"),
                        16, 16, 81, 0));

    }

    //animation test
    private int spriteIndex = 15;
    private float spriteFlipTime = 0.8f;
    private float spriteFlipTimeleft = 0.0f;

    @Override
    public void update(float dt) {

        MouseListener.getOrthoY();
        //System.out.println("fps= "+ (1.0f/dt));


        for (GameObject o : this.gameObjects) {
            o.update(dt);


        }
        this.renderer.render();


    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites2.size(); i++) {

            Sprite sprite = sprites2.getSprite(i);
            float spriteWidth = sprite.getWidth() *2;
            float spriteHeight = sprite.getHeight() *2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();
            ImGui.pushID(i);

           if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {

                System.out.println("button" + i + "clicked");

            }
            ImGui.popID();
            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites2.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();

            }


        }


        ImGui.end();

    }
}

