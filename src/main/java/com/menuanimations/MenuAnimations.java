package com.menuanimations;

import com.menuanimations.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

public class MenuAnimations implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
    }
}
