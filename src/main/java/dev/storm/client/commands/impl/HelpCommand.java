package dev.storm.client.commands.impl;

import dev.storm.client.StormClient;
import dev.storm.client.commands.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Shows a list of commands.", new String[]{"h"});
    }

    @Override
    public void execute(String[] args) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.BOLD + "StormClient Commands:"), false);
        for (Command command : StormClient.commandManager.getCommands()) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.GRAY + "." + command.getName() + " - " + command.getDescription()), false);
        }
    }
}
