package dev.storm.client.gui;

public class Animation {
    private double value;
    private long lastTime;
    private double speed;
    private boolean forward;

    public Animation(double speed) {
        this.speed = speed;
        this.lastTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastTime;
        lastTime = currentTime;

        if (forward) {
            value += delta * speed;
            if (value > 1) {
                value = 1;
            }
        } else {
            value -= delta * speed;
            if (value < 0) {
                value = 0;
            }
        }
    }

    public double getValue() {
        return value;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }
}
