package dev.storm.client.utils;

public class Timer {
    private long lastMS = System.currentTimeMillis();

    public boolean hasPassed(long milliseconds) {
        return System.currentTimeMillis() - lastMS >= milliseconds;
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }
}
