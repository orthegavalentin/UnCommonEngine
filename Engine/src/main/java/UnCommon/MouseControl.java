package UnCommon;

import components.Component;
import util.Settings;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControl extends Component {
    GameObject holdenObject = null;

    public void pickUpObject(GameObject gameObject,int indice) {

        System.out.println("picked up objject "+indice);
        this.holdenObject = gameObject;
        Window.getScene().addGameObjectToScene(holdenObject);
    }

    public void place() {
        this.holdenObject = null;

    }

    @Override
    public void update(float dt) {
        if(holdenObject!=null){

            holdenObject.transform.translate.x=MouseListener.getOrthoX();
            holdenObject.transform.translate.y=MouseListener.getOrthoY();
            holdenObject.transform.translate.x=(int)(holdenObject.transform.translate.x/ Settings.GRID_WIDTH)*Settings.GRID_WIDTH;
            holdenObject.transform.translate.y=(int)(holdenObject.transform.translate.y/ Settings.GRID_HEIGHT)*Settings.GRID_HEIGHT;



            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
                place();
            }




        }
    }
}
