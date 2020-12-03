package physics2D;

import Renderer.Line2D;
import org.joml.Vector2f;
import org.junit.Test;
import physics2D.rigidbody.IntersectorDetector2D;

import static junit.framework.TestCase.assertTrue;

public class collisionDetectorTests {
    private final float Epsilon= 0.000001f;

    @Test
    public void pointOnLine2DShouldReturnTrueTest(){
        Line2D line=new Line2D(new Vector2f(0,0),new Vector2f(12,4));
        Vector2f point =new Vector2f(0,0);
        assertTrue(IntersectorDetector2D.pointOnLine(point,line));
    }
    @Test
    public void pointOnLine2DShouldReturnTrueTestTwo(){
        Line2D line=new Line2D(new Vector2f(0,0),new Vector2f(12,4));
        Vector2f point =new Vector2f(12,4);
        assertTrue(IntersectorDetector2D.pointOnLine(point,line));
    }
    @Test
    public void pointOnVerticalLineShouldReturnTrue(){
        Line2D line=new Line2D(new Vector2f(12,0),new Vector2f(12,4));
        Vector2f point =new Vector2f(12,2);
        assertTrue(IntersectorDetector2D.pointOnLine(point,line));
    }


}
