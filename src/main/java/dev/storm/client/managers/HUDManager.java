package dev.storm.client.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.storm.client.gui.HudEditor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HUDManager {

    private final File hudLayoutFile = new File("stormclient", "hudlayout.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Map<String, int[]> componentPositions = new HashMap<>();
    private HudEditor hudEditor;

    public HUDManager() {
        loadLayout();
        hudEditor = new HudEditor();
    }

    public void render() {
        // In a real scenario, you would iterate through registered HUD components
        // and render them at their respective positions.
        // For now, this is a placeholder.
    }

    public void saveLayout() {
        try (FileWriter writer = new FileWriter(hudLayoutFile)) {
            JsonObject layout = new JsonObject();
            for (Map.Entry<String, int[]> entry : componentPositions.entrySet()) {
                JsonObject position = new JsonObject();
                position.addProperty("x", entry.getValue()[0]);
                position.addProperty("y", entry.getValue()[1]);
                layout.add(entry.getKey(), position);
            }
            gson.toJson(layout, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLayout() {
        if (!hudLayoutFile.exists()) {
            // Set default positions
            componentPositions.put("FPS", new int[]{2, 2});
            componentPositions.put("Ping", new int[]{2, 12});
            componentPositions.put("Coordinates", new int[]{2, 22});
            componentPositions.put("CPS", new int[]{2, 32});
            componentPositions.put("Module List", new int[]{2, 42});
            return;
        }

        try (FileReader reader = new FileReader(hudLayoutFile)) {
            JsonParser parser = new JsonParser();
            JsonObject layout = (JsonObject) parser.parse(reader);
            for (Map.Entry<String, com.google.gson.JsonElement> entry : layout.entrySet()) {
                JsonObject position = entry.getValue().getAsJsonObject();
                int x = position.get("x").getAsInt();
                int y = position.get("y").getAsInt();
                componentPositions.put(entry.getKey(), new int[]{x, y});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HudEditor getHudEditor() {
        return hudEditor;
    }

    public Map<String, int[]> getComponentPositions() {
        return componentPositions;
    }
}
