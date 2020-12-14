package components;

import UnCommon.Camera;
import UnCommon.KeyListener;
import UnCommon.MouseListener;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class EditorCamera extends Component {
    //for dragging
    private float dragDebounce = 0.1f;
    private boolean reset =false;


    private Camera leveEditorCamera;
    private Vector2f clickOrigin;
    private float dragSensitivity = 30.0f;
    private float scrollSensitivity=0.1f;

    //fixing the camera lerping
    private float lerpTime=0.0f;




    public EditorCamera(Camera levelEditorCamera) {
        this.leveEditorCamera = levelEditorCamera;
        this.clickOrigin = new Vector2f();


    }

    @Override
    public void update(float dt) {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT) && dragDebounce > 0) {
            this.clickOrigin = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());
            dragDebounce -= dt;
            return;
        } else if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            Vector2f mousePos = new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY());
            Vector2f delta = new Vector2f(mousePos).sub(this.clickOrigin);
            leveEditorCamera.position.sub(delta.mul(dt).mul(dragSensitivity));
            this.clickOrigin.lerp(mousePos, dt);


        }
        if (dragDebounce <= 0.0f && !MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            dragDebounce = 0.1f;

        }
        //scroll with exponential functio,
        if (MouseListener.getScrollY() != 0.0f) {

            float addValue = (float) Math.pow(Math.abs(MouseListener.getScrollY()*scrollSensitivity),
                    1 / leveEditorCamera.getZoom());

            addValue *= Math.signum(MouseListener.getScrollY());
            System.out.println("add value ="+addValue);
            leveEditorCamera.addZoom(addValue);

        }


        if(KeyListener.isKeypressed(GLFW_KEY_R)){
            reset=true;

        }

        if(reset){
            leveEditorCamera.position.lerp(new Vector2f(),lerpTime);

           //lniear interporate the zoom
            leveEditorCamera.setZoom(this.leveEditorCamera.getZoom()+
                    ((1.0f-leveEditorCamera.getZoom())*lerpTime));
            this.lerpTime +=0.1*dt;

            if (Math.abs(leveEditorCamera.position.x) <= 5.0f &&
                    Math.abs(leveEditorCamera.position.y) <= 5.0f) {
                leveEditorCamera.position.set(0f,0f);
                this.lerpTime=0.0f;
                this.leveEditorCamera.setZoom(1.0f);
                reset=false;

            }

        }


    }
}
