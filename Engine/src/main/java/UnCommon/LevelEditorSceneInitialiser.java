package UnCommon;

import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;
import scenes.Scene;
import scenes.SceneInitializer;
import util.AssetPool;


public class LevelEditorSceneInitialiser extends SceneInitializer {
    private GameObject obj1;
    private GameObject obj2;
    SpriteRenderer obj2SpriteRenderer;
    private SpriteSheet sprites, sprites2, gizmos;


    private GameObject leveEditorStuff;

    public LevelEditorSceneInitialiser() {


    }

    @Override
    public void init(Scene scene) {

        sprites = AssetPool.getSpriteSheet("Assets/images/spritesheet.png");
        sprites2 = AssetPool.getSpriteSheet("Assets/images/decorationsAndBlocks.png");

        gizmos = AssetPool.getSpriteSheet("Assets/images/gizmos.png");

        leveEditorStuff = scene.createGameObject("levelEditor");
        leveEditorStuff.setNoSerialize();

        leveEditorStuff.addComponent(new MouseControl());
       // leveEditorStuff.addComponent(new GridLines());
        leveEditorStuff.addComponent(new EditorCamera(scene.camera()));
        leveEditorStuff.addComponent(new GizmoSystem(gizmos));

        scene.addGameObjectToScene(leveEditorStuff);




    }

    @Override
    public void loadResources(Scene scene) {
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

        for (GameObject obj : scene.getGameObjects()) {
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
    public void imgui() {
       /* ImGui.begin("Level Editor Stuff");
        leveEditorStuff.imgui();
        ImGui.end();*/


        ImGui.begin("Test window");
        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {

            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();
            ImGui.pushID(i);

            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                leveEditorStuff.getComponent(MouseControl.class).pickUpObject(object, i);
                System.out.println("button" + i + "clicked");

            }
            ImGui.popID();
            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();

            }



        }
        for (int i = 0; i < sprites2.size(); i++) {

            Sprite sprite = sprites2.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();
            ImGui.pushID(i);

            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
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

