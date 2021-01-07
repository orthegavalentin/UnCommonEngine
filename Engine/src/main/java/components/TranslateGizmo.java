package components;


import UnCommon.MouseListener;
import editor.PropertiesWindow;


public class TranslateGizmo extends Gizmo {






    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
     super(arrowSprite,propertiesWindow);

    }


    @Override
    public void editorUpdate(float dt) {


        if(activeGameObject!=null){

            if(xAxisActive&&!yAxisActive){
                activeGameObject.transform.translate.x-= MouseListener.worldDx();
            } else if(yAxisActive){
                activeGameObject.transform.translate.y-= MouseListener.worldDy();
            }
        }

        super.editorUpdate(dt);



    }


}
