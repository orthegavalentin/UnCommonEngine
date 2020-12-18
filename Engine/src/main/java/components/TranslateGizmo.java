package components;

import UnCommon.GameObject;
import UnCommon.Prefabs;
import UnCommon.Window;
import editor.PropertiesWindow;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class TranslateGizmo extends Component {

    private Vector4f xAxisColor = new Vector4f(0.8f, 0, 0, 1);
    private Vector4f xAxisHover = new Vector4f();
    private Vector4f yAxisColor = new Vector4f(0, 0.8f, 0, 1);
    private Vector4f yAxisHover = new Vector4f();
    private Vector4f InActiveColor = new Vector4f(0, 0, 0, 0);

    private GameObject xAxisObject;
    private GameObject yAxisObject;
    private SpriteRenderer xAxisSprite;
    private SpriteRenderer yAxisSprite;
    private GameObject activeGameObject = null;
    private PropertiesWindow propertiesWindow;
    private Vector2f xAxisOffSet = new Vector2f(80f,2f);
    private Vector2f yAxisOffSet = new Vector2f(26f,80f);


    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
        this.xAxisObject = Prefabs.generateSpriteObject(arrowSprite,
                24, 48);
        this.yAxisObject = Prefabs.generateSpriteObject(arrowSprite,
                24, 48);
        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);

        Window.getScene().addGameObjectToScene(this.xAxisObject);
        Window.getScene().addGameObjectToScene(this.yAxisObject);
        this.propertiesWindow = propertiesWindow;


    }

    @Override
    public void start() {
        this.xAxisObject.transform.rotation = 90;
        this.yAxisObject.transform.rotation = 180;
        this.xAxisObject.setNoSerialize();
        this.yAxisObject.setNoSerialize();


    }

    @Override
    public void update(float dt) {
        if (this.activeGameObject != null) {
            this.xAxisObject.transform.translate.set(this.activeGameObject.transform.translate);
            this.yAxisObject.transform.translate.set(this.activeGameObject.transform.translate);
            this.xAxisObject.transform.translate.add(this.xAxisOffSet);
            this.yAxisObject.transform.translate.add(this.yAxisOffSet);


        }
        this.activeGameObject = this.propertiesWindow.getActivegameObject();
        if (this.activeGameObject != null) {
            this.setActive();
        } else {
            this.setInActive();
        }


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
}
