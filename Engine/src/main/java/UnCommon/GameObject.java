package UnCommon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
import components.ComponentDeserialiser;
import components.SpriteRenderer;
import imgui.ImGui;
import util.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private static int ID_COUNTERR = 0;
    private int uid = -1;
    private String name;
    private List<Component> components;
    public transient Transform transform;
    //to implement layers

    private boolean doSerialisation = true;
    private boolean isDead = false;


   /* public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
        this.transform = new Transform();
        zIndex = 0;
        //potential cause problems in the future


    }*/

    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();


        //potential cause problems in the future
        this.uid = ID_COUNTERR++;

    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error :Casting component";
                }

            }

        }


        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {

                components.remove(i);

                return;


            }


        }


    }

    public void addComponent(Component c) {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;

    }

    public void update(float dt) {

        for (int i = 0; i < components.size(); i++) {

            components.get(i).update(dt);

        }
    }

    public void start() {

        for (int i = 0; i < components.size(); i++) {

            components.get(i).start();

        }
    }


    public void imgui() {
        for (Component c : components) {
            if (ImGui.collapsingHeader(c.getClass().getSimpleName()))
                c.imgui();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public static void init(int maxId) {
        ID_COUNTERR = maxId;

    }

    public int getUid() {
        return this.uid;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void setNoSerialize() {
        this.doSerialisation = false;
    }

    public boolean getDoSerialization() {
        return this.doSerialisation;
    }

    public void destroy() {
        this.isDead = true;
        for (int i = 0; i < components.size(); i++) {

            components.get(i).destroy();


        }


    }

    public void editorUpdate(float dt) {

        for (int i = 0; i < components.size(); i++) {

            components.get(i).editorUpdate(dt);

        }


    }

    public GameObject copy() {
        //TODO do it a good way

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserialiser())
                .registerTypeAdapter(GameObject.class, new GameOjectDeserialiser())
                .create();

        String jsonObj = gson.toJson(this);
        GameObject obj = gson.fromJson(jsonObj, GameObject.class);
        obj.generateUid();
        for (Component c : obj.getAllComponents()) {
            c.generateId();
        }
        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null && sprite.getTexture() != null) {

            sprite.setTextur(AssetPool.getTexture(sprite.getTexture().getFilepath()));
        }

        return obj;
    }

    private void generateUid() {
        this.uid = ID_COUNTERR++;

    }
}
