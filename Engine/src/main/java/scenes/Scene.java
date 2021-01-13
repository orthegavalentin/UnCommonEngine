package scenes;

import UnCommon.*;
import components.SpriteRenderer;
import components.SpriteSheet;
import org.joml.Vector2f;
import physics2D.Physics2D;
import renderer.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
import components.ComponentDeserialiser;
import imgui.ImGui;
import util.AssetPool;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {

    private Renderer renderer;
    private Camera camera;
    private boolean isRunning;
    private List<GameObject> gameObjects ;

    private boolean levelLoaded;
    private SceneInitializer sceneInitializer;
    private Physics2D physics2D;

    private SpriteSheet sprites;


    public Scene(SceneInitializer sceneInitializer) {

        this.sceneInitializer = sceneInitializer;
        this.physics2D=new Physics2D();
        this.renderer=new Renderer();
        this.gameObjects=new ArrayList<>();
        this.levelLoaded=false;
        this.isRunning=false;



    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public void editorUpdate(float dt){

        this.camera.adjustProjection();
        for (int i=0;i<gameObjects.size();i++) {
            GameObject go=gameObjects.get(i);

            go.editorUpdate(dt);


            if(go.isDead()){

                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;


            }

        }



    }
    private int spriteIndex=15;
    private float spriteFlipTime=0.8f;
    private float spriteFlipTimeleft=0.0f;

    public void update(float dt) {
        this.camera.adjustProjection();
        this.physics2D.update(dt);


        for (int i=0;i<gameObjects.size();i++) {
            GameObject go=gameObjects.get(i);
            if(go.getComponent(SpriteRenderer.class)!=null){


               // go.transform.translate.x+=0.01f;
            }

            go.update(dt);


            if(go.isDead()){

                gameObjects.remove(i);
                this.renderer.destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;


            }

        }

    }

    public void render() {
        this.renderer.render();

    }

    public void init() {
        sprites = AssetPool.getSpriteSheet("Assets/images/spritesheet.png");
        this.camera = new Camera(new Vector2f(0.3f, 0.4f));
        this.sceneInitializer.loadResources(this);
        this.sceneInitializer.init(this);






    }


    public void start() {
        for (int i=0;i<gameObjects.size();i++) {
            GameObject go=gameObjects.get(i);
            go.start();


            this.renderer.add(go);
            this.physics2D.add(go);

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
            this.physics2D.add(go);


        }


    }
    public void destroy(){
        for(GameObject go:gameObjects){
            go.destroy();

        }

    }



    public void imgui() {
        this.sceneInitializer.imgui();


    }

    public GameObject createGameObject(String name) {
        GameObject gameObject = new GameObject(name);
        gameObject.addComponent(new Transform());
        gameObject.transform = gameObject.getComponent(Transform.class);
        return gameObject;


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
                if (objs[i].getUid() > maxGameObjectId) {
                    maxGameObjectId = objs[i].getUid();
                }
            }
            maxCompId++;
            maxGameObjectId++;

            Component.init(maxCompId);
            GameObject.init(maxGameObjectId);
            this.levelLoaded = true;

        }


    }

    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserialiser())
                .registerTypeAdapter(GameObject.class, new GameOjectDeserialiser())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            List<GameObject> objsToSerialize = new ArrayList<>();
            for (GameObject obj : this.gameObjects) {
                if (obj.getDoSerialization()) {
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }


    }


    public GameObject getGameObject(int gameObjectId) {
        Optional<GameObject> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();

        return result.orElse(null);


    }
}
