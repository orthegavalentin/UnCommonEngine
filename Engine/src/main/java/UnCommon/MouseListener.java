package UnCommon;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX, worldX, worldY, lastWorldX, lastWorldY;
    private boolean mouseButtonPressed[] = new boolean[9];

    private boolean isDragging;
    private Vector2f gameViewPortPos = new Vector2f();
    private Vector2f gameViewPortSize = new Vector2f();
    private int mouseButtonsDown = 0;


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
        if (getInstance().mouseButtonsDown>0) {
            getInstance().isDragging = true;


        }
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
        getInstance().lastWorldX = getInstance().worldX;
        getInstance().lastWorldY = getInstance().worldY;
        getInstance().xPos = xpos;
        getInstance().yPos = ypos;

        computeOrthoX();
        computeOrthoY();

        //if the mouse moves and one the buttons is pressed so it means it dragging
        getInstance().isDragging = getInstance().mouseButtonPressed[0] || getInstance().mouseButtonPressed[0] || getInstance().mouseButtonPressed[0];


    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            getInstance().mouseButtonsDown++;
            //incase we might have some fancy mouse with more than 3 buttons we avoid the error
            if (button < getInstance().mouseButtonPressed.length) {
                getInstance().mouseButtonPressed[button] = true;
            }


        } else if (action == GLFW_RELEASE) {
            getInstance().mouseButtonsDown--;
            if (button < getInstance().mouseButtonPressed.length) {
                getInstance().mouseButtonPressed[button] = false;
                getInstance().isDragging = false;

            }


        }


    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance().scrollX = xOffset;
        getInstance().scrollY = yOffset;


    }

    public static void endFrame() {
        getInstance().scrollX = 0;
        getInstance().scrollY = 0;
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
        getInstance().lastWorldX = getInstance().worldX;
        getInstance().lastWorldY = getInstance().worldY;

    }

    public static float getX() {
        return (float) getInstance().xPos;
    }

    public static float getY() {
        return (float) getInstance().yPos;
    }


    public static float getDx() {
        return (float) (getInstance().lastX - getInstance().xPos);
    }

    public static float getDy() {
        return (float) (getInstance().lastY - getInstance().yPos);
    }
    public static float worldDx() {
        return (float) (getInstance().lastWorldX- getInstance().worldX);
    }

    public static float worldDy() {
        return (float) (getInstance().lastWorldY- getInstance().worldY);
    }

    public static float getScrollX() {
        return (float) getInstance().scrollX;

    }

    public static float getScrollY() {
        return (float) getInstance().scrollY;

    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < getInstance().mouseButtonPressed.length) {
            return getInstance().mouseButtonPressed[button];


        } else {
            return false;


        }

    }

    public static float getOrthoX() {


        return (float) getInstance().worldX;
    }

    private static void computeOrthoX() {
        float currentX = getX() - getInstance().gameViewPortPos.x;
        currentX = (currentX / getInstance().gameViewPortSize.x) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMatrix().mul(camera.getInverseProjectionMatrix(), viewProjection);
        tmp.mul(viewProjection);


        getInstance().worldX = tmp.x;

    }

    public static float getOrthoY() {
        return (float) getInstance().worldY;


    }

    public static void computeOrthoY() {
        float currentY = getY() - getInstance().gameViewPortPos.y;
        currentY = -((currentY / getInstance().gameViewPortSize.y) * 2.0f - 1.0f);
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        Camera camera = Window.getScene().camera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMatrix().mul(camera.getInverseProjectionMatrix(), viewProjection);
        tmp.mul(viewProjection);


        getInstance().worldY = tmp.y;
    }


    public static void setGameViewPortPos(Vector2f gameViewPortPos) {
        getInstance().gameViewPortPos.set(gameViewPortPos);
    }

    public static void setGameViewPortSize(Vector2f gameViewPortSize) {
        getInstance().gameViewPortSize.set(gameViewPortSize);
    }

    public static float getScreenX() {
        float currentX = getX() - getInstance().gameViewPortPos.x;
        currentX = (currentX / (float) getInstance().gameViewPortSize.x) * 1920.0f;


        return currentX;
    }

    public static float getScreeny() {
        float currentY = getY() - getInstance().gameViewPortPos.y;
        currentY = 1080.0f - ((currentY / getInstance().gameViewPortSize.y) * 1080.0f);


        return currentY;
    }
}
