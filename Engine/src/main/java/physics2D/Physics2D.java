package physics2D;

import UnCommon.GameObject;
import UnCommon.Transform;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import physics2D.components.Box2DCollider;
import physics2D.components.Circle2DCollider;
import physics2D.components.RigidBody2D;

public class Physics2D {

    private Vec2 gravity = new Vec2(0, -10.0f);
    private World world = new World(gravity);

    private float physicsTime = 0.0f;
    private float physicsTimeStep = 1.0f / 60.0f;
    private int velocityIterations = 8;
    private int positionIterations = 3;

    public void add(GameObject gameObject) {
        RigidBody2D rb = gameObject.getComponent(RigidBody2D.class);
        if (rb != null && rb.getRawBody() == null) {
            Transform transform = gameObject.transform;
            BodyDef bodyDef = new BodyDef();
            bodyDef.angle = (float) Math.toRadians(transform.rotation);
            bodyDef.position.set(transform.translate.x, transform.translate.y);
            bodyDef.angularDamping = rb.getAngularDamping();
            bodyDef.linearDamping = rb.getLinearDamping();
            bodyDef.fixedRotation = rb.isFixedRotation();
            bodyDef.bullet = rb.isContinuousCollision();

            switch (rb.getBodyType()) {
                case KINEMATIC:
                    bodyDef.type = BodyType.KINEMATIC;
                    break;
                case STATIC:
                    bodyDef.type = BodyType.STATIC;
                    break;
                case DYNAMIC:
                    bodyDef.type = BodyType.DYNAMIC;
                    break;


            }

            PolygonShape shape = new PolygonShape();
            Circle2DCollider circle2DCollider;
            Box2DCollider box2DCollider;

            if ((circle2DCollider = gameObject.getComponent(Circle2DCollider.class)) != null) {
                shape.setRadius(circle2DCollider.getRadius());

            } else if ((box2DCollider = gameObject.getComponent(Box2DCollider.class)) != null) {
                Vector2f halfSize = new Vector2f(box2DCollider.getHalfSize().mul(0.5f));
                Vector2f offset = box2DCollider.getOffset();
                Vector2f origin = new Vector2f(box2DCollider.getOrigin());
                shape.setAsBox(halfSize.x, halfSize.y, new Vec2(origin.x, origin.y), 0);

                Vec2 pos = bodyDef.position;

                float xPos = pos.x + offset.x;
                float ypos = pos.y + offset.y;
                bodyDef.position.set(xPos, ypos);


            }

            Body body = this.world.createBody(bodyDef);
            rb.setRawBody(body);
            body.createFixture(shape, rb.getMass());


        }

    }

    public void update(float dt) {
        physicsTime += dt;
        if (physicsTime >= 0.0f) {
            physicsTime -= physicsTimeStep;
            world.step(physicsTimeStep, velocityIterations, positionIterations);

        }


    }

    public void destroyGameObject(GameObject go) {
        RigidBody2D rb = go.getComponent(RigidBody2D.class);
        if (rb != null) {

            if (rb.getRawBody() != null) {

                world.destroyBody(rb.getRawBody());
                rb.setRawBody(null);
            }
        }

    }
}
