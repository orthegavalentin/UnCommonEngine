package components;

import UnCommon.Window;
import org.joml.Vector2f;
import util.Settings;

public class GridLines extends Component {


    @Override
    public void update(float dt) {
        Vector2f CameraPos = Window.getScene().camera().position;
        Vector2f projectionSize = Window.getScene().camera().getProjectionSize();

        int firstX = ((int) (CameraPos.x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH);
        int firstY = ((int) (CameraPos.y / Settings.GRID_HEIGHT) * Settings.GRID_HEIGHT);

        int verticalLines = (int) (projectionSize.x / Settings.GRID_WIDTH);
        int horizontalLines = (int) (projectionSize.y / Settings.GRID_HEIGHT);

        int height = (int) projectionSize.y;
        int width = (int) projectionSize.x;

        int maxLines=Math.max(verticalLines,horizontalLines);

        for(int i=0;i<maxLines;i++){
            int x=firstX+(Settings.GRID_WIDTH*i);
            int y=firstY+(Settings.GRID_HEIGHT*i);
            if(i<verticalLines){

            }
        }
    }
}
