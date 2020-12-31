package renderer;

import UnCommon.GameObject;
import components.SpriteRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int Max_BATCH_SIZE = 10000;
    private List<RenderBatch> batches;
    private static Shader currenShader;


    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer sprite) {

        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.getzIndex() == sprite.gameObject.transform.zIndex) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;

                }
            }
        }
        if (!added) {

            RenderBatch newbatch = new RenderBatch(Max_BATCH_SIZE, sprite.gameObject.transform.zIndex);
            newbatch.start();
            batches.add(newbatch);
            newbatch.addSprite(sprite);
            Collections.sort(batches);

        }
    }

    public static void bindShader(Shader shader) {

        currenShader = shader;
    }
    public static Shader getBoundShader() {

        return currenShader;
    }

    public void render() {
        currenShader.use();
        for (RenderBatch batch : batches) {

            batch.render();
        }


    }


}
