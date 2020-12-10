package util;

import org.joml.Vector2f;

import java.util.Vector;

public class JMath {


    public static void  rotate(Vector2f vec,float angleDeg,Vector2f origin){

        //bringing back to the origin
        float x=vec.x-origin.x;
        float y=vec.y-origin.y;

        float cos=(float)Math.cos(Math.toRadians(angleDeg));
        float sin=(float)Math.sin(Math.toRadians(angleDeg));

        float xRotated=(x*cos)-(y*sin);
        float yRotated=(x*sin)+(y*cos);
        //returning it back to its point
        xRotated+=origin.x;
        yRotated+=origin.y;

        vec.x=xRotated;
        vec.y=yRotated;

    }


    public static boolean compare(float x,float y,float epsilon){
     return Math.abs(x-y) <=epsilon*Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)));

    }
    public static boolean compare(Vector2f vec1,Vector2f vec2,float epsilon){
        return compare(vec1.x,vec2.x,epsilon)&&compare(vec1.y,vec2.y,epsilon);

    }

    public static boolean compare(float x,float y){

        return Math.abs(x-y) <= Float.MIN_VALUE*Math.max(1.0f,Math.max(Math.abs(x),Math.abs(y)));

    }
    public static boolean compare(Vector2f vec1,Vector2f vec2){
        return compare(vec1.x,vec2.x)&&compare(vec1.y,vec2.y);

    }

}
