package physics2D.rigidbody;

import Renderer.Line2D;
import org.joml.Vector2f;
import org.lwjgl.system.CallbackI;
import physics2D.primitives.*;
import util.JMath;

import java.util.Vector;

public class IntersectorDetector2D {

    //==============================================
    //point intersection function
    //============================================

    public static boolean pointOnLine(Vector2f point, Line2D line) {

        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if (dx == 0f) {
            return JMath.compare(point.x, line.getStart().x);
        }
        float slope = dy / dx;
        float b = line.getEnd().y - (slope * line.getEnd().x);

        return point.y == slope * point.x + b;


    }

    public static boolean pointIncircle(Vector2f point, Circle circle) {
        Vector2f center = circle.getCenter();
        //wrap around a new object for us to not modify point
        Vector2f centerToPoint = new Vector2f(point).sub(center);
//avoid square rooot by squaring values because square root take a long processing time
        return centerToPoint.lengthSquared() < circle.getRadius() * circle.getRadius();

    }

    public static boolean pointInAABB(Vector2f point, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();
        return point.x >= min.x && point.x <= max.x && point.y <= max.y && point.y >= min.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box2D box) {
        //translate the point into local space the apply same formula as AABB
        Vector2f pointRotated = new Vector2f(point);
        JMath.rotate(pointRotated, box.getRigidBody().getRotation()
                , box.getRigidBody().getPosition());


        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return pointRotated.x >= min.x && pointRotated.x <= max.x &&
                pointRotated.y <= max.y && pointRotated.y >= min.y;
    }

    //==============================================
    //line intersection function
    //line vs circle
    //line vs AABB
    //line vs OBB
    //============================================
    public static boolean LineCircle(Line2D l, Circle c) {
        if (pointIncircle(l.getStart(), c) || pointIncircle(l.getEnd(), c)) {
            return true;
        }


        Vector2f ab = new Vector2f(l.getEnd()).sub(new Vector2f(l.getStart()));
        Vector2f center = c.getCenter();
        float t = (center.sub(l.getStart())).dot(ab) / ab.dot(ab);
        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        Vector2f closestPoint = new Vector2f(l.getStart()).add(ab.mul(t));
        Vector2f circleToClosest = closestPoint.sub(center);
        return pointIncircle(closestPoint, c);
    }

    public static boolean lineAABB(Line2D line, AABB box) {
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) {
            return true;
        }

        Vector2f unitVector = new Vector2f(line.getEnd()).sub(line.getStart());
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;
        Vector2f min = box.getMin();
        min.sub(line.getStart()).mul(unitVector);
        Vector2f max = box.getMax();
        max.sub(line.getStart()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));

        if (tmax < 0 || tmin > tmax) {
            return false;
        }
        float t = (tmin < 0f) ? tmax : tmin;

        return t > 0f && t * t < line.lengthSquared();


    }

    public static boolean lineAndBox2D(Line2D line, Box2D box) {
        float angle = -box.getRigidBody().getRotation();
        Vector2f center = box.getRigidBody().getPosition();
        Vector2f localStart = new Vector2f(line.getStart());
        Vector2f localEnd = new Vector2f(line.getEnd());

        JMath.rotate(localStart, angle, center);
        JMath.rotate(localEnd, angle, center);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAABB(localLine, aabb);
    }
//RayCasting

    public static boolean rayCast(Circle circle, Ray2D ray, RayCastResult result) {
        RayCastResult.reset(result);

        Vector2f originToCircle = new Vector2f(circle.getCenter()).sub(ray.getOrigin());
        float radiusSquared = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSquared = originToCircle.lengthSquared();
        float a = originToCircle.dot(ray.getDirection());
        float bsqr = originToCircleLengthSquared - (a * a);

        if (radiusSquared - bsqr < 0.0f) {
            return false;
        }
        //this is what makes the raycast are slow because of the square root
        //intel i7 processor the length of  a quare root takes 25cpu circles while a multiplication
        //take 5cpu circles
        float f = (float) Math.sqrt(radiusSquared - bsqr);
        float t = 0;

        if (originToCircleLengthSquared < radiusSquared) {
            //ray starts inside the circle
            t = a + f;


        } else {
            t = a - f;
        }


        if (result != null) {


            Vector2f point = new Vector2f(ray.getOrigin()).add(ray.getDirection().mul(t));
            Vector2f normal = new Vector2f(point).sub(circle.getCenter());

            normal.normalize();
            result.init(point, normal, t, true);


        }

        return true;


    }

}
