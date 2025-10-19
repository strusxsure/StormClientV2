package dev.storm.client.managers;

import dev.storm.client.commands.Command;
import dev.storm.client.commands.impl.BindCommand;
import dev.storm.client.commands.impl.HelpCommand;
import dev.storm.client.commands.impl.HUDCommand;
import dev.storm.client.commands.impl.ToggleCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private List<Command> commands = new ArrayList<>();
    private String prefix = ".";

    public CommandManager() {
        commands.add(new ToggleCommand());
        commands.add(new BindCommand());
        commands.add(new HelpCommand());
        commands.add(new HUDCommand());
    }

    public void handleChatMessage(String message) {
        if (!message.startsWith(prefix)) {
            return;
        }

        message = message.substring(prefix.length());
        String[] args = message.split(" ");
        String commandName = args[0];

        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(commandName) || Arrays.stream(command.getAliases()).anyMatch(alias -> alias.equalsIgnoreCase(commandName))) {
                command.execute(Arrays.copyOfRange(args, 1, args.length));
                return;
            }
        }

        MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.RED + "Unknown command: " + commandName), false);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
