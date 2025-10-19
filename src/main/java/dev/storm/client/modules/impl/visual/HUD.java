package dev.storm.client.modules.impl.visual;

import dev.storm.client.StormClient;
import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;

public class HUD extends Module {

    public HUD() {
        super("HUD", "Toggles the custom overlay.", Category.VISUAL, 0);
        this.toggle(); // Enabled by default
    }

    @Override
    public void onTick() {
        // The HUD rendering logic will be handled by the HUDManager and a mixin.
    }
}
