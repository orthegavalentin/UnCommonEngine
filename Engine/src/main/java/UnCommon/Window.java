package UnCommon;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int height, width;
    private String title;
    private long glfwWindow;


    private Window() {
        this.height = 1080;
        this.width = 1920;
        this.title = "UnCommon";

    }

    /**
     * techinque du holder pour utiliser le pattern singleton
     * description ici
     * http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java
     */
    private static class WindowHolder {
        /**
         * Instance unique non préinitialisée
         */
        private final static Window window = new Window();
    }

    public static Window getWindow() {

        return WindowHolder.window;

    }

    /**
     * creation et initialization de la fenetre code disponible
     * sur
     * https://www.lwjgl.org/guide
     */
    public void run() {
        System.out.println("hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();


        //free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();


    }

    public void init() {
        //gestion d'erreur
        GLFWErrorCallback.createPrint(System.err).set();

        //initialization de glfw
        if (!glfwInit()) {
            throw new IllegalStateException("unable to initialize GLFW");
        }

        //configuration
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //create Window it returns a memory adress
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("failed to create GLFW window");
        }
        //after creating a window
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

    }

    public void loop() {
        // Set the clear color
        glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(glfwWindow)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(glfwWindow); // swap the color buffers
            if (KeyListener.isKeypressed(GLFW_KEY_SPACE)) {
                   System.out.println("you taped space bro");
            }
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }


    }
}
