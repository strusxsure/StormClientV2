package dev.storm.client.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.storm.client.StormClient;
import dev.storm.client.modules.Module;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {

    private final File configDir = new File("stormclient");
    private final File configFile = new File(configDir, "config.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager() {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject config = new JsonObject();
            JsonObject modules = new JsonObject();

            for (Module module : StormClient.moduleManager.getModules()) {
                JsonObject moduleConfig = new JsonObject();
                moduleConfig.addProperty("toggled", module.isToggled());
                moduleConfig.addProperty("keybind", module.getKeybind());
                modules.add(module.getName(), moduleConfig);
            }

            config.add("modules", modules);
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (!configFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonParser parser = new JsonParser();
            JsonObject config = (JsonObject) parser.parse(reader);

            if (config.has("modules")) {
                JsonObject modules = config.getAsJsonObject("modules");
                for (Module module : StormClient.moduleManager.getModules()) {
                    if (modules.has(module.getName())) {
                        JsonObject moduleConfig = modules.getAsJsonObject(module.getName());
                        if (moduleConfig.has("toggled") && moduleConfig.get("toggled").getAsBoolean()) {
                            module.toggle();
                        }
                        if (moduleConfig.has("keybind")) {
                            module.setKeybind(moduleConfig.get("keybind").getAsInt());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
