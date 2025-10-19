package dev.storm.client.commands.impl;

import dev.storm.client.StormClient;
import dev.storm.client.commands.Command;
import net.minecraft.client.MinecraftClient;

public class HUDCommand extends Command {

    public HUDCommand() {
        super("hud", "Toggles the HUD Editor.", new String[]{});
    }

    @Override
    public void execute(String[] args) {
        MinecraftClient.getInstance().openScreen(StormClient.hudManager.getHudEditor());
    }
}
