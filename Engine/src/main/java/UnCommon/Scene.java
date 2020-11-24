package UnCommon;

import Renderer.Renderer;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = gameObjects = new ArrayList<>();
    protected GameObject activegameObject=null;

    public Scene() {


    }

    public abstract void update(float dt);

    public void init() {
    }

    ;


    public void start() {
        for (GameObject go : gameObjects) {
            go.start();

            this.renderer.add(go);

        }
        isRunning = true;


    }

    public Camera camera() {
        return this.camera;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {

            gameObjects.add(go);
            go.start();
            this.renderer.add(go);


        }


    }

    public void sceneImgui() {
        if(activegameObject!=null){
            ImGui.begin("inspector");
            activegameObject.imgui();
            ImGui.end();
        }
        imgui();


    }

    public void imgui() {


    }


}
