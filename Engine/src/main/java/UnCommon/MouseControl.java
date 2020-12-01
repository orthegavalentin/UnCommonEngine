package UnCommon;

import components.Component;

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

            holdenObject.transform.translate.x=MouseListener.getOrthoX()-16;
            holdenObject.transform.translate.y=MouseListener.getOrthoY()-16;


            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
                place();
            }




        }
    }
}
