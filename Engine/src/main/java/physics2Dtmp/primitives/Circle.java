package physics2Dtmp.primitives;

import org.joml.Vector2f;
import physics2Dtmp.rigidbody.RigidBody2D;

public class Circle {
    private float radius = 1.0f;
    private RigidBody2D body = null;

    public float getRadius() {
        return this.radius;
    }

    public Vector2f getCenter() {
        return this.body.getPosition();
    }
}
