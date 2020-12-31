package components;

import UnCommon.GameObject;
import UnCommon.MouseListener;
import UnCommon.Prefabs;
import UnCommon.Window;
import editor.PropertiesWindow;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Gizmo extends Component {

    private Vector4f xAxisColor = new Vector4f(0.4f, 0.4f, 0, 1);
    private Vector4f xAxisHover = new Vector4f(0.9f, 0.4f, 0, 0.6f);
    private Vector4f yAxisColor = new Vector4f(0, 0.4f, 0.4f, 1);
    private Vector4f yAxisHover = new Vector4f(0, 0.4f, 0.9f, 0.6f);
    private Vector4f InActiveColor = new Vector4f(0, 0, 0, 0);

    private GameObject xAxisObject;
    private GameObject yAxisObject;
    private SpriteRenderer xAxisSprite;
    private SpriteRenderer yAxisSprite;
    protected GameObject activeGameObject = null;
    private PropertiesWindow propertiesWindow;
    private Vector2f xAxisOffSet = new Vector2f(65f, 2f);
    private Vector2f yAxisOffSet = new Vector2f(26f, 60f);
    private int gizmoWidth = 16;
    private int gizmoHeight = 48;

    protected boolean xAxisActive = false;
    protected boolean yAxisActive = false;
    private boolean using = false;


    public Gizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
        this.xAxisObject = Prefabs.generateSpriteObject(arrowSprite,
                16, 48);
        this.yAxisObject = Prefabs.generateSpriteObject(arrowSprite,
                16, 48);
        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);
        this.propertiesWindow = propertiesWindow;
        this.xAxisObject.addComponent(new NonPickable());
        this.yAxisObject.addComponent(new NonPickable());
        Window.getScene().addGameObjectToScene(this.xAxisObject);
        Window.getScene().addGameObjectToScene(this.yAxisObject);


    }

    @Override
    public void start() {
        this.xAxisObject.transform.rotation = 90;
        this.yAxisObject.transform.rotation = 180;
        this.xAxisObject.transform.zIndex=50;
        this.yAxisObject.transform.zIndex=50;
        this.xAxisObject.setNoSerialize();
        this.yAxisObject.setNoSerialize();


    }

    @Override
    public void update(float dt) {
        if (!using) return;


        if (MouseListener.isDragging()) {
            System.out.println("dragged me man");

        }
        this.activeGameObject = this.propertiesWindow.getActivegameObject();
        if (this.activeGameObject != null) {
            this.setActive();
        } else {
            this.setInActive();
            return;
        }
        boolean xAxisHot = checkXHoverState();
        boolean yAxisHot = checkYHoverState();
        if ((xAxisHot || xAxisActive) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            xAxisActive = true;
            yAxisActive = false;
        } else if ((yAxisHot || yAxisActive) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            xAxisActive = false;
            yAxisActive = true;


        } else {
            yAxisActive = false;
            xAxisActive = false;


        }

        if (this.activeGameObject != null) {
            this.xAxisObject.transform.translate.set(this.activeGameObject.transform.translate);
            this.yAxisObject.transform.translate.set(this.activeGameObject.transform.translate);
            this.xAxisObject.transform.translate.add(this.xAxisOffSet);
            this.yAxisObject.transform.translate.add(this.yAxisOffSet);


        }


    }

    private boolean checkXHoverState() {
        Vector2f mousePos = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());

        if (mousePos.x <= xAxisObject.transform.translate.x &&
                mousePos.x >= xAxisObject.transform.translate.x - gizmoHeight &&
                mousePos.y >= xAxisObject.transform.translate.y &&
                mousePos.y <= xAxisObject.transform.translate.y + gizmoWidth) {

            xAxisSprite.setColor(xAxisHover);
            return true;


        }
        xAxisSprite.setColor(xAxisColor);
        return false;


    }

    private boolean checkYHoverState() {
        Vector2f mousePos = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());

        if (mousePos.x <= yAxisObject.transform.translate.x &&
                mousePos.x >= yAxisObject.transform.translate.x - gizmoWidth &&
                mousePos.y <= yAxisObject.transform.translate.y &&
                mousePos.y >= yAxisObject.transform.translate.y - gizmoHeight) {

            yAxisSprite.setColor(yAxisHover);
            return true;


        }
        yAxisSprite.setColor(yAxisColor);
        return false;

    }

    public void setActive() {

        this.xAxisSprite.setColor(xAxisColor);
        this.yAxisSprite.setColor(yAxisColor);


    }

    public void setInActive() {
        this.activeGameObject = null;
        this.xAxisSprite.setColor(InActiveColor);
        this.yAxisSprite.setColor(InActiveColor);


    }

    public void setUsing() {

        this.using = true;


    }

    public void setNotUsing() {
        this.using = false;
        this.setInActive();

    }


}
