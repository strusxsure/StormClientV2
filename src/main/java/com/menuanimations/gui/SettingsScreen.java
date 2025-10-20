package com.menuanimations.gui;

import com.menuanimations.config.ConfigManager;
import com.menuanimations.config.ModConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class SettingsScreen extends Screen {

    private ModConfig config;
    private String selectedAddon = "Menu Animation";

    private int color(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public SettingsScreen() {
        super(Text.of("Storm Settings"));
        this.config = ConfigManager.getConfig();
        ShaderHelper.loadShader();
    }

    @Override
    protected void init() {
        super.init();
        addButtons();
    }

    private void addButtons() {
        this.clearChildren();
        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        int sidebarWidth = 100;

        // Sidebar button
        this.addDrawableChild(ButtonWidget.builder(Text.of("Menu Animation"), button -> {
            selectedAddon = "Menu Animation";
            addButtons();
        }).dimensions(panelX + 10, panelY + 40, sidebarWidth - 20, 20).build());

        // Toggle button for the animation setting
        if ("Menu Animation".equals(selectedAddon)) {
            this.addDrawableChild(ButtonWidget.builder(
                Text.of(config.isMenuAnimationEnabled() ? "ENABLED" : "DISABLED"),
                button -> {
                    config.setMenuAnimationEnabled(!config.isMenuAnimationEnabled());
                    ConfigManager.saveConfig();
                    addButtons();
                }
            ).dimensions(panelX + sidebarWidth + 20, panelY + 40, 100, 20).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ShaderHelper.renderShader(delta);
        super.render(context, mouseX, mouseY, delta);

        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        int panelWidth = 400;
        int panelHeight = 250;
        int cornerRadius = 10;

        // Main settings panel with rounded corners
        context.fill(panelX, panelY + cornerRadius, panelX + panelWidth, panelY + panelHeight - cornerRadius, color(30, 30, 30, 200));
        context.fill(panelX + cornerRadius, panelY, panelX + panelWidth - cornerRadius, panelY + panelHeight, color(30, 30, 30, 200));

        // Header
        context.fill(panelX, panelY, panelX + panelWidth, panelY + 30, color(20, 20, 20, 200));
        context.drawTextWithShadow(this.textRenderer, "⚡ StormClient", panelX + 10, panelY + 10, color(255, 255, 255, 255));

        // Sidebar
        int sidebarWidth = 100;
        context.fill(panelX, panelY + 30, panelX + sidebarWidth, panelY + panelHeight, color(25, 25, 25, 200));

        // Highlight selected addon
        if ("Menu Animation".equals(selectedAddon)) {
             context.fill(panelX + 10, panelY + 40, panelX + sidebarWidth - 10, panelY + 60, color(50, 50, 50, 200));
        }

        // Change toggle button color based on state
        if ("Menu Animation".equals(selectedAddon)) {
            if(config.isMenuAnimationEnabled()) {
                context.fill(panelX + sidebarWidth + 20, panelY + 40, panelX + sidebarWidth + 120, panelY + 60, color(255, 255, 0, 150));
            } else {
                 context.fill(panelX + sidebarWidth + 20, panelY + 40, panelX + sidebarWidth + 120, panelY + 60, color(255, 0, 0, 150));
            }
        }
    }

    @Override
    public void close() {
        ShaderHelper.closeShader();
        this.client.setScreen(null);
    }
}