package Renderer;

import UnCommon.GameObject;
import components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int Max_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;


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
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;


            }
        }
        if (!added) {

            RenderBatch newbatch = new RenderBatch(Max_BATCH_SIZE);
            newbatch.start();
            batches.add(newbatch);
            newbatch.addSprite(sprite);

        }
    }


    public void render(){
for(RenderBatch batch:batches){

    batch.render();
}


    }


}