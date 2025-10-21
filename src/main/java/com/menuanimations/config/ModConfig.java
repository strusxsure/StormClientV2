package com.menuanimations.config;

public class ModConfig {

    private boolean menuAnimationEnabled = true;
    private float animationSpeed = 0.7f;

    public boolean isMenuAnimationEnabled() {
        return menuAnimationEnabled;
    }

    public void setMenuAnimationEnabled(boolean menuAnimationEnabled) {
        this.menuAnimationEnabled = menuAnimationEnabled;
    }

    public float getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}
