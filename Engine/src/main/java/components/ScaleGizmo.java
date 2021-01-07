package components;

import UnCommon.MouseListener;
import editor.PropertiesWindow;

public class ScaleGizmo extends Gizmo  {

    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void editorUpdate(float dt) {


        if(activeGameObject!=null){

            if(xAxisActive&&!yAxisActive){
                activeGameObject.transform.scale.x-= MouseListener.worldDx();
            } else if(yAxisActive){
                activeGameObject.transform.scale.y-= MouseListener.worldDy();
            }
        }

        super.editorUpdate(dt);



    }
}
