package com.menuanimations.gui;

import com.menuanimations.config.ConfigManager;
import com.menuanimations.config.ModConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import java.awt.Color;

public class SettingsScreen extends Screen {

    private ModConfig config;

    public SettingsScreen() {
        super(Text.of("Storm Settings"));
        this.config = ConfigManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();
        addButtons();
    }

    private void addButtons() {
        this.clearChildren();
        int panelX = this.width / 4;
        int panelY = this.height / 4;
        int sidebarWidth = (this.width / 2) / 4;

        // Sidebar button
        this.addDrawableChild(ButtonWidget.builder(Text.of("MenuAnimation"), button -> {
            // Future-proofing: This will be used to switch between different addon settings
        }).dimensions(panelX + 10, panelY + 10, sidebarWidth - 20, 20).build());

        // Toggle button for the animation setting
        this.addDrawableChild(ButtonWidget.builder(
            Text.of("Animation: " + (config.isMenuAnimationEnabled() ? "ON" : "OFF")),
            button -> {
                config.setMenuAnimationEnabled(!config.isMenuAnimationEnabled());
                ConfigManager.saveConfig();
                addButtons(); // Re-create buttons to update the text
            }
        ).dimensions(panelX + sidebarWidth + 20, panelY + 10, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Dark background
        context.fill(0, 0, this.width, this.height, new Color(0, 0, 0, 150).getRGB());

        // Main settings panel
        int panelX = this.width / 4;
        int panelY = this.height / 4;
        int panelWidth = this.width / 2;
        int panelHeight = this.height / 2;
        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, new Color(30, 30, 30).getRGB());

        // Sidebar
        int sidebarWidth = panelWidth / 4;
        context.fill(panelX, panelY, panelX + sidebarWidth, panelY + panelHeight, new Color(20, 20, 20).getRGB());

        // Highlight selected item in yellow
        context.fill(panelX + 10, panelY + 10, panelX + sidebarWidth - 10, panelY + 30, new Color(255, 255, 0, 100).getRGB());
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }
}