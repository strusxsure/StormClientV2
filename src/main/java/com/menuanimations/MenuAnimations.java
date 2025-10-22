package com.menuanimations;

import com.menuanimations.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class MenuAnimations implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigManager.loadConfig();
    }
}
