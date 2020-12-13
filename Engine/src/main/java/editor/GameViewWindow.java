package editor;

import UnCommon.MouseListener;
import UnCommon.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;

public class GameViewWindow {
    private static float leftX,rightX,topY,bottomY;

    public static void imgui(){
        ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar|ImGuiWindowFlags.NoScrollWithMouse);
        ImVec2 windowSize=getLargestSizeForViewPort();
        ImVec2 windowPos=getCenteredPositionForViewport(windowSize);



        ImGui.setCursorPos(windowPos.x,windowPos.y);

        ImVec2 topLef=new ImVec2();
        ImGui.getCursorScreenPos(topLef);
        topLef.x-=ImGui.getScrollX();
        topLef.y-=ImGui.getScrollY();

        leftX=topLef.x;
        bottomY=topLef.y;
        rightX=topLef.x+windowSize.x;
        topY=topLef.y+windowSize.y;


        int textureId= Window.getFrameBuffer().getTextureId();
        ImGui.image(textureId,windowSize.x,windowSize.y,0,1,1,0);
        MouseListener.setGameViewPortPos(new Vector2f(topLef.x,topLef.y));
        MouseListener.setGameViewPortSize(new Vector2f(windowSize.x,windowSize.y));

        ImGui.end();



    }

    private static ImVec2 getLargestSizeForViewPort(){
        ImVec2 windowSize= new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x-=ImGui.getScrollX();
        windowSize.y-=ImGui.getScrollY();
        float aspectWidth=windowSize.x;
        float aspectHeight=aspectWidth/Window.getTargetAspectRatio();

        if(aspectHeight > windowSize.y){
            aspectHeight= windowSize.y;
            aspectWidth=aspectHeight*Window.getTargetAspectRatio();


        }

        return new ImVec2(aspectWidth,aspectHeight);













    }

    private static ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize){
        ImVec2 windowSize=new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x-=ImGui.getScrollX();
        windowSize.y-=ImGui.getScrollY();

        float  viewPortX=(windowSize.x/2.0f)-(aspectSize.x/2.0f);
        float  viewPortY=(windowSize.y/2.0f)-(aspectSize.y/2.0f);
        return new ImVec2(viewPortX+ImGui.getCursorPosX(),viewPortY+ImGui.getCursorPosY());







    }


    public static boolean getWantCaptureMouse() {

      return MouseListener.getX()>=leftX&&MouseListener.getX()<=rightX&&
        MouseListener.getY()>=bottomY &&MouseListener.getY()<=topY;
    }
}
