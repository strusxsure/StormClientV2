package dev.storm.client.commands.impl;

import dev.storm.client.StormClient;
import dev.storm.client.commands.Command;
import dev.storm.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "Toggles a module.", new String[]{"t"});
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.RED + "Usage: .toggle <module>"), false);
            return;
        }

        Module module = StormClient.moduleManager.getModuleByName(args[0]);
        if (module == null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.RED + "Module not found."), false);
            return;
        }

        module.toggle();
        MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.GREEN + module.getName() + " has been " + (module.isToggled() ? "enabled" : "disabled") + "."), false);
    }
}
