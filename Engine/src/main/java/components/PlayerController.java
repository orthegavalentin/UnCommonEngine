package components;

import UnCommon.Camera;
import UnCommon.KeyListener;
import UnCommon.Window;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.system.CallbackI;
import physics2D.components.RigidBody2D;
import scenes.Scene;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class PlayerController extends Component {
    private int spriteIndex = 15;
    private float spriteFlipTime = 0.8f;
    private float spriteFlipTimeleft = 0.0f;

    private transient Body rawBody = null;

    public void setRawBody(Body rawBody) {
        this.rawBody = rawBody;
    }


    @Override
    public void update(float dt) {
        System.out.println("fps"+1/dt);
        spriteFlipTimeleft -= (dt)*10;
        if (spriteFlipTimeleft <= 0) {
            spriteFlipTimeleft = spriteFlipTime;
            spriteIndex += 1;
            if (spriteIndex > 17) {
                spriteIndex = 15;
            }


        }
        if (KeyListener.isKeypressed(GLFW_KEY_RIGHT)) {
            this.gameObject.getComponent(RigidBody2D.class).getRawBody().setLinearVelocity(new Vec2(2.0f, 1f));
        }
        if (KeyListener.isKeypressed(GLFW_KEY_LEFT)) {
            this.gameObject.getComponent(RigidBody2D.class).getRawBody().setLinearVelocity(new Vec2(-2.0f, 1f));

        }
      this.gameObject.getComponent(SpriteRenderer.class).setSprite(AssetPool.getSpriteSheet("Assets/images/spritesheet.png").getSprite(spriteIndex));
       Window.getScene().camera().position.x=this.gameObject.transform.translate.x-0.5f;


    }

}
