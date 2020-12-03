package physics2D.rigidbody;

import Renderer.Line2D;
import org.joml.Vector2f;
import org.lwjgl.system.CallbackI;
import physics2D.primitives.AABB;
import physics2D.primitives.Box2D;
import physics2D.primitives.Circle;
import util.JMath;

import java.util.Vector;

public class IntersectorDetector2D {

    //==============================================
    //point intersection function
    //============================================

    public static boolean pointOnLine(Vector2f point, Line2D line) {

        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if(dx==0f){
            return JMath.compare(point.x,line.getStart().x);
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
    public static boolean LineCircle( Line2D l,  Circle c) {
        if(pointIncircle(l.getStart(),c)||pointIncircle(l.getEnd() ,c)){
            return true;
        }


        Vector2f ab=new Vector2f(l.getEnd()).sub(new Vector2f(l.getStart()));
        Vector2f center=c.getCenter();
        float t=(center.sub(l.getStart())).dot(ab)/ab.dot(ab);
        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        Vector2f closestPoint=new Vector2f(l.getStart()).add(ab.mul(t));
        Vector2f circleToClosest= closestPoint.sub(center);
        return pointIncircle(closestPoint,c);
    }


}
