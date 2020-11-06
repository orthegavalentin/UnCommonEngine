package UnCommon;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3];

    private boolean isDragging;


    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;


    }

    public static MouseListener getInstance() {
        if (instance == null) {
            MouseListener.instance = new MouseListener();

        }
        return MouseListener.instance;
    }

    //glfw.org/docs/3.3/input_guide.html
    public static void mousePosCallback(long window, double xpos, double ypos) {
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
        getInstance().xPos = xpos;
        getInstance().yPos = ypos;
        //if the mouse moves and one the buttons is pressed so it means it dragging
        getInstance().isDragging=getInstance().mouseButtonPressed[0]||getInstance().mouseButtonPressed[0]||getInstance().mouseButtonPressed[0];




    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            //incase we might have some fancy mouse with more than 3 buttons we avoid the error
            if (button < getInstance().mouseButtonPressed.length) {
                getInstance().mouseButtonPressed[button] = true;
            }


        } else if (action == GLFW_RELEASE) {
            if (button < getInstance().mouseButtonPressed.length){
                getInstance().mouseButtonPressed[button] = false;
                getInstance().isDragging = false;

            }



        }


    }

    public static void mouseScrollCallback(long window,double xOffset,double yOffset){
        getInstance().scrollX=xOffset;
        getInstance().scrollY=yOffset;


    }

    public static void endFrame(){
        getInstance().scrollX=0;
        getInstance().scrollY=0;
        getInstance().lastX=getInstance().xPos;
        getInstance().lastY=getInstance().yPos;
    }

    public static float getX(){
        return (float)getInstance().xPos;
    }
    public static float getY(){
        return (float)getInstance().yPos;
    }
    public static float getDx(){
        return (float)(getInstance().lastX-getInstance().xPos);
    }
    public static float getDy(){
        return (float)(getInstance().lastY-getInstance().yPos);
    }

    public static float getScrollX(){
        return (float)getInstance().scrollX;

    }
    public static float getScrollY(){
        return (float)getInstance().scrollY;

    }

    public static boolean isDragging(){
        return getInstance().isDragging;
    }
    public static boolean mouseButtonDown(int button){
        if(button<getInstance().mouseButtonPressed.length){
            return getInstance().mouseButtonPressed[button];


        }else{
            return false;


        }

    }




}
