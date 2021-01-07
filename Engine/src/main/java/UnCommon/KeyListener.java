package UnCommon;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private static KeyListener instance;
    //amount of key binding that glfw might be 350
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {


    }

    public static KeyListener getInstance() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();

        }
        return KeyListener.instance;

    }


    public static void keyCallback(long window, int key, int scancode, int action, int mode) {
        if (action == GLFW_PRESS) {
            getInstance().keyPressed[key] = true;

        } else if (action == GLFW_RELEASE) {
            getInstance().keyPressed[key] = false;
        }

    }

    public static boolean isKeypressed(int keycode) {

        return getInstance().keyPressed[keycode];


    }
}
