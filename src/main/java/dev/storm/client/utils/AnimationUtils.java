package dev.storm.client.utils;

public class AnimationUtils {

    public static double easeInOut(double t) {
        return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
}
