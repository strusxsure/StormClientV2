package dev.storm.client.managers;

import dev.storm.client.modules.Module;
import dev.storm.client.modules.impl.combat.*;
import dev.storm.client.modules.impl.movement.*;
import dev.storm.client.modules.impl.render.*;
import dev.storm.client.modules.impl.visual.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        modules.add(new KillAura());

        // Movement
        modules.add(new Sprint());
        modules.add(new Flight());

        // Render
        modules.add(new Fullbright());
        modules.add(new ESP());
        modules.add(new FPSBoost());

        // Visual
        modules.add(new HUD());
        modules.add(new NameTags());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
