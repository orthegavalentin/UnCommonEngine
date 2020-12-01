package scenes;

import Renderer.Renderer;
import UnCommon.Camera;
import UnCommon.GameObject;
import UnCommon.GameOjectDeserialiser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
import components.ComponentDeserialiser;
import imgui.ImGui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = gameObjects = new ArrayList<>();
    protected GameObject activegameObject = null;
    protected boolean levelLoaded = false;

    public Scene() {


    }

    public abstract void update(float dt);

    public void init() {
    }


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
        if (activegameObject != null) {
            ImGui.begin("inspector");
            activegameObject.imgui();
            ImGui.end();
        }
        imgui();


    }

    public void imgui() {


    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserialiser())
                .registerTypeAdapter(GameObject.class, new GameOjectDeserialiser())
                .create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));


        } catch (IOException e) {

            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            int maxGameObjectId = -1;
            int maxCompId = -1;

            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);
                for (Component c : objs[i].getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }

                }
                if(objs[i].getUid()>maxGameObjectId){
                    maxGameObjectId=objs[i].getUid();
                }
            }
            maxCompId++;
            maxGameObjectId++;

            Component.init(maxCompId);
            GameObject.init(maxGameObjectId);
            this.levelLoaded = true;

        }


    }

    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserialiser())
                .registerTypeAdapter(GameObject.class, new GameOjectDeserialiser())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }


    }


}