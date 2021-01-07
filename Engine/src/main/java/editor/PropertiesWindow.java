package editor;

import UnCommon.GameObject;
import UnCommon.MouseListener;
import components.NonPickable;
import imgui.ImGui;
import physics2D.components.Box2DCollider;
import physics2D.components.Circle2DCollider;
import physics2D.components.RigidBody2D;
import renderer.PickingTexture;
import scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    protected GameObject activegameObject = null;
    private PickingTexture pickingTexture;
    private float debounce = 0.2f;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    public void update(float dt, Scene currentScene) {
        debounce -= dt;
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int) MouseListener.getScreenX();
            int y = (int) MouseListener.getScreeny();
            int gameObjectId = pickingTexture.readPixel(x, y);
            GameObject pickedObj = currentScene.getGameObject(gameObjectId);

            if (pickedObj != null && pickedObj.getComponent(NonPickable.class) == null) {
                System.out.println("picked up");
                activegameObject = pickedObj;
            } else if (pickedObj == null && !MouseListener.isDragging()) {
                activegameObject = null;
            }

            this.debounce = 0.2f;


        }


    }

    public void imgui() {
        if (activegameObject != null) {
            ImGui.begin("Properties");
            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add RigidBody")) {
                    if (activegameObject.getComponent(RigidBody2D.class) == null) {
                        activegameObject.addComponent(new RigidBody2D());
                    }
                }
                if (ImGui.menuItem("Add Box Collider")) {
                    if (activegameObject.getComponent(Box2DCollider.class) == null&&
                            activegameObject.getComponent(Circle2DCollider.class) == null) {
                        activegameObject.addComponent(new Box2DCollider());
                    }
                }

                if (ImGui.menuItem("Add Circle Collider")) {
                    if (activegameObject.getComponent(Circle2DCollider.class) == null&&
                            activegameObject.getComponent(Box2DCollider.class) == null) {
                        activegameObject.addComponent(new Circle2DCollider());
                    }
                }
                ImGui.endPopup();

            }

            activegameObject.imgui();
            ImGui.end();
        }


    }

    public GameObject getActivegameObject() {
        return this.activegameObject;
    }


    public void setActiveGameObject(GameObject o) {
        this.activegameObject=o;
    }
}
