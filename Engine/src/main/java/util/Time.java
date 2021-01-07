package util;

public class Time {
    public static float timeStarted=System.nanoTime();
    /**nanoTime function returns 1e9 ns/s while we need s/ns so we inverse it to 1e-9
     *
     */


    public static float getTime(){return (float)((System.nanoTime()-timeStarted) * 1E-9);}

}