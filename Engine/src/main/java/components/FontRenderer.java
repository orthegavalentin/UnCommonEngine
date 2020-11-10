package components;

import UnCommon.Component;

public class FontRenderer extends Component {
    @Override
    public void start() {
        if(gameObject.getComponent(SpriteRenderer.class)!=null){
         System.out.println("font renderer in the game");
        }

    }


    @Override
    public void update(float dt) {

    }
}
