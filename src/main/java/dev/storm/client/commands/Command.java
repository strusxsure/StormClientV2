package dev.storm.client.commands;

public abstract class Command {
    private String name;
    private String description;
    private String[] aliases;

    public Command(String name, String description, String[] aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public abstract void execute(String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }
}
