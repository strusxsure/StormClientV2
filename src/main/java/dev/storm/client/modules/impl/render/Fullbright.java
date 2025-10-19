package dev.storm.client.modules.impl.render;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class Fullbright extends Module {

    private double oldGamma;

    public Fullbright() {
        super("Fullbright", "Makes the world brighter.", Category.RENDER, 0);
    }

    @Override
    public void onEnable() {
        if (mc.options != null) {
            oldGamma = mc.options.gamma;
            mc.options.gamma = 1000;
        }
    }

    @Override
    public void onDisable() {
        if (mc.options != null) {
            mc.options.gamma = oldGamma;
        }
    }
}
