package UnCommon;

import org.joml.Vector4f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import java.awt.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int height, width;
    private String title;
    private long glfwWindow;
    private static Window window;
    private Vector4f color;
   private static Scene currentScene;
   private ImGuiLayer imGuiLayer;


    private Window() {
        this.height = 1080;
        this.width = 1920;
        this.title = "UnCommon";
        this.color=new Vector4f(1,1,1,1);

    }
    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene=new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene=new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false :"unknown scene "+newScene+"**";
                break;



        }
    }

    public static Window getWindow(){
        if(Window.window==null){
            Window.window=new Window();
        }
        return Window.window;
    }

    public static Scene getScene(){
        return getWindow().currentScene;

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
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });
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
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiLayer= new ImGuiLayer(glfwWindow);
      this.imGuiLayer.initImGui();
        Window.changeScene(0);

    }

    public void loop() {
        float beginTime= (float) glfwGetTime();
        float endTime= (float) glfwGetTime();
        float dt=-1.0f;



        // Set the clear color
       // glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
        glClearColor(this.color.x,this.color.y,this.color.z,this.color.w);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(glfwWindow)) {

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT ); // clear the framebuffer
            if (MouseListener.isDragging()) {
                System.out.println("you are dragging bro");
            }
            if(dt>=0){
                currentScene.update(dt);
            }
          this.imGuiLayer.update(dt);
            glfwSwapBuffers(glfwWindow); // swap the color buffers

            endTime= (float) glfwGetTime();
            dt=endTime-beginTime;
            beginTime=endTime;

        }


    }

    public static int getWidth(){
        return getWindow().width;

    }
    public static int getHeight(){
        return getWindow().height;

    }
    public static void  setHeight(int height){
        getWindow().height=height;

    }

    public static void  setWidth(int width){
        getWindow().height=width;

    }

}
