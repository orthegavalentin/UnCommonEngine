package physics2D.components;

import UnCommon.KeyListener;
import components.Component;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;


public class RigidBody2D extends Component {
    private Vector2f velocity =new Vector2f();
    private float angularDamping=0.8f;
    private float linearDamping =0.9f;
    private float mass=0;
    private BodyType bodyType=BodyType.DYNAMIC;

    private boolean fixedRotation =false;
    private boolean continuousCollision=true;
    private transient Body rawBody=null;

    @Override
    public void update(float dt) {
        if(rawBody !=null){
           /* if(KeyListener.isKeypressed(GLFW_KEY_LEFT)){
                System.out.println("lll000kk");
                rawBody.applyForce(new Vec2(10,0),rawBody.getWorldCenter());
              //  rawBody.setLinearVelocity(new Vec2(1,0));




            }*/
            this.gameObject.transform.translate.set(rawBody.getPosition().x,
                    rawBody.getPosition().y);
           this.gameObject.transform.rotation=(float) Math.toDegrees(rawBody.getAngle());


        }
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getAngularDamping() {
        return angularDamping;
    }

    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }

    public float getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public boolean isContinuousCollision() {
        return continuousCollision;
    }

    public void setContinuousCollision(boolean continuousCollision) {
        this.continuousCollision = continuousCollision;
    }

    public Body getRawBody() {
        return rawBody;
    }

    public void setRawBody(Body rawBody) {
        this.rawBody = rawBody;
    }
}
