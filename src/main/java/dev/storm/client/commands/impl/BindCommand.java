package dev.storm.client.commands.impl;

import dev.storm.client.StormClient;
import dev.storm.client.commands.Command;
import dev.storm.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind", "Binds a module to a key.", new String[]{"b"});
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.RED + "Usage: .bind <module> <key>"), false);
            return;
        }

        Module module = StormClient.moduleManager.getModuleByName(args[0]);
        if (module == null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.RED + "Module not found."), false);
            return;
        }

        int key = InputUtil.fromTranslationKey("key.keyboard." + args[1].toLowerCase()).getCode();

        module.setKeybind(key);
        MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.GREEN + module.getName() + " has been bound to " + args[1].toUpperCase() + "."), false);
    }
}
