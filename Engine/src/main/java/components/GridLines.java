package components;

import UnCommon.Camera;
import renderer.DebugDraw;
import UnCommon.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import util.Settings;

public class GridLines extends Component {
    Camera camera=Window.getScene().camera();


    @Override
    public void editorUpdate(float dt) {
        Vector2f CameraPos = camera.position;
        Vector2f projectionSize = camera.getProjectionSize();

        int firstX = ((int) (CameraPos.x / Settings.GRID_WIDTH)) * Settings.GRID_WIDTH;
        int firstY = ((int) (CameraPos.y / Settings.GRID_HEIGHT))* Settings.GRID_HEIGHT;

        int verticalLines = (int) (projectionSize.x*camera.getZoom() / Settings.GRID_WIDTH);
        int horizontalLines = (int) (projectionSize.y*camera.getZoom() / Settings.GRID_HEIGHT);

        int height = (int) (projectionSize.y*camera.getZoom())+Settings.GRID_WIDTH;
        int width = (int)( projectionSize.x*camera.getZoom())+Settings.GRID_WIDTH;

        int maxLines=Math.max(verticalLines,horizontalLines);
        Vector3f color=new Vector3f(0.2f,0.2f,0.2f);
        for(int i=0;i<maxLines;i++){
            int x=firstX+(Settings.GRID_WIDTH*i);
            int y=firstY+(Settings.GRID_HEIGHT*i);
            if(i<verticalLines){
                DebugDraw.addLine2D(new Vector2f(x,firstY),new Vector2f(x,firstY+height),color);


            }
            if(i<horizontalLines){
                DebugDraw.addLine2D(new Vector2f(firstX,y),new Vector2f(firstX+width,y),color);


            }
        }
    }
}
