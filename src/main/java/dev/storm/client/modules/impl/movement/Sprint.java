package dev.storm.client.modules.impl.movement;

import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints.", Category.MOVEMENT, 0);
    }

    @Override
    public void onTick() {
        if (mc.player != null && !mc.player.isSprinting() && mc.player.forwardSpeed > 0) {
            mc.player.setSprinting(true);
        }
    }
}
