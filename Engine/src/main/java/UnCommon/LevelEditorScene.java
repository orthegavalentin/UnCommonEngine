package UnCommon;

import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;
import scenes.Scene;
import util.AssetPool;


public class LevelEditorScene extends Scene {
    private GameObject obj1;
    private GameObject obj2;
    SpriteRenderer obj2SpriteRenderer;
    SpriteSheet sprites, sprites2,gizmos;


    GameObject leveEditorStuff = this.createGameObject("levelEditor");

    public LevelEditorScene() {


    }

    @Override
    public void init() {
        loadResources();
        sprites = AssetPool.getSpriteSheet("Assets/images/spritesheet.png");
        sprites2 = AssetPool.getSpriteSheet("Assets/images/decorationsAndBlocks.png");

       gizmos = AssetPool.getSpriteSheet("Assets/images/gizmos.png");


        this.camera = new Camera(new Vector2f(0.3f, 0.4f));
        leveEditorStuff.addComponent(new MouseControl());
        leveEditorStuff.addComponent(new GridLines());
        leveEditorStuff.addComponent(new EditorCamera(this.camera));
        leveEditorStuff.addComponent(new GizmoSystem(gizmos));
        leveEditorStuff.start();




        if (levelLoaded) {

            return;
        }

      /*  obj1 = new GameObject("object 1", new Transform(new Vector2f(100, 100), new Vector2f(32, 32)), 0);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        obj1SpriteRenderer.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1SpriteRenderer);

        this.addGameObjectToScene(obj1);

        obj2 = new GameObject("object 2", new Transform(new Vector2f(150, 100), new Vector2f(300, 300)), 0);

        obj2SpriteRenderer.setSprite(sprites.getSprite(15));
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);*/


    }

    private void loadResources() {
        AssetPool.getShader("Assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("Assets/images/spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/spritesheet.png"),
                        16, 16, 28, 0));
        AssetPool.addSpriteSheet("Assets/images/decorationsAndBlocks.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/decorationsAndBlocks.png"),
                        16, 16, 81, 0));
        AssetPool.addSpriteSheet("Assets/images/gizmos.png",
                new SpriteSheet(AssetPool.getTexture("Assets/images/gizmos.png"),
                        24, 48, 3, 0));

        for (GameObject obj : gameObjects) {
            if (obj.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = obj.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTextur(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }

            }

        }
    }

    //animation test
    private int spriteIndex = 15;
    private float spriteFlipTime = 0.1f;
    private float spriteFlipTimeleft = 0.0f;
    float t = 0.0f;
    float angle = 0.0f;

    @Override
    public void update(float dt) {
        leveEditorStuff.update(dt);
        this.camera.adjustProjection();
        /*spriteFlipTimeleft -= dt;
        gameObjects.get(1).transform.translate.x+=dt*20;*/


      /*  if (spriteFlipTimeleft <= 0) {
            spriteFlipTimeleft = spriteFlipTime;
            spriteIndex++;

            if (spriteIndex > 17) {
                spriteIndex = 15;

            }
            gameObjects.get(1).getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }*/


        //  DebugDraw.addCircle(new Vector2f(300, 300), 64, new Vector3f(1, 1, 0), 1);


        // gameObjects.get(1).transform.translate.x += 50 * dt;


        MouseListener.getOrthoY();
        //   System.out.println("fps= "+ (1.0f/dt));


        for (GameObject o : this.gameObjects) {
            o.update(dt);


        }


    }

    @Override
    public void render() {
        this.renderer.render();

    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Stuff");
        leveEditorStuff.imgui();
        ImGui.end();



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
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();
            ImGui.pushID(i);

            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                leveEditorStuff.getComponent(MouseControl.class).pickUpObject(object, i);
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

