package dev.storm.client;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.storm.client.managers.ModuleManager;
import dev.storm.client.managers.CommandManager;
import dev.storm.client.managers.ConfigManager;
import dev.storm.client.managers.HUDManager;

public class StormClient implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("StormClient");

    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static ConfigManager configManager;
    public static HUDManager hudManager;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing StormClient...");

        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        hudManager = new HUDManager();

        configManager.load();

        LOGGER.info("StormClient initialized.");
    }
}
