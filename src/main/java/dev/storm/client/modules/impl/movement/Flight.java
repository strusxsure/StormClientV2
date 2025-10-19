package dev.storm.client.modules.impl.movement;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class Flight extends Module {

    public Flight() {
        super("Flight", "Allows you to fly.", Category.MOVEMENT, 0);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.abilities.allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null && !mc.player.isCreative()) {
            mc.player.abilities.allowFlying = false;
        }
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.player.abilities.allowFlying) {
            mc.player.abilities.flying = true;
        }
    }
}
