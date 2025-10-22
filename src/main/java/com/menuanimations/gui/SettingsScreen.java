package com.menuanimations.gui;

import com.menuanimations.config.ConfigManager;
import com.menuanimations.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class SettingsScreen extends Screen {

    private final Screen parent;
    private ModConfig config;
    private String selectedTab = "Animations";

    public SettingsScreen(Screen parent) {
        super(Text.of("Storm Settings"));
        this.parent = parent;
        this.config = ConfigManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();

        // Feather-style toggle
        this.addDrawableChild(new FeatherToggleWidget(this.width / 2 - 100, this.height / 2 - 40, 200, 20, "Animation", config.isMenuAnimationEnabled(), (enabled) -> {
            config.setMenuAnimationEnabled(enabled);
            ConfigManager.saveConfig();
        }));

        // Animation speed slider
        this.addDrawableChild(new SliderWidget(this.width / 2 - 100, this.height / 2 - 10, 200, 20, Text.of("Animation Speed: " + config.getAnimationSpeed()), config.getAnimationSpeed()) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Animation Speed: " + String.format("%.2f", value * 2.0f)));
            }

            @Override
            protected void applyValue() {
                config.setAnimationSpeed((float) this.value * 2.0f);
                ConfigManager.saveConfig();
            }
        });

        // Tabs
        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.of("Animations"), button -> selectedTab = "Animations")
                .dimensions(panelX + 10, panelY + 30, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.of("Performance"), button -> selectedTab = "Performance")
                .dimensions(panelX + 110, panelY + 30, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render the blurred background
        this.renderBackground(context, mouseX, mouseY, delta);
        RenderUtil.drawBlurBackground(context, 0, 0, this.width, this.height, 0.75f);

        // Main Panel
        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        int panelWidth = 400;
        int panelHeight = 250;

        // Draw the main panel with rounded corners
        RenderUtil.drawRoundedRect(context, panelX, panelY, panelWidth, panelHeight, 10, 0x96000000);

        // Header
        context.fill(panelX, panelY, panelX + panelWidth, panelY + 30, 0xC8141414); // Darker semi-transparent black
        context.drawTextWithShadow(this.textRenderer, "⚡ Storm Settings", panelX + 10, panelY + 10, 0xFFFFFFFF); // White

        if ("Animations".equals(selectedTab)) {
            context.fill(panelX + 10, panelY + 50, panelX + 110, panelY + 52, 0xFFFFFF00); // Yellow
        } else if ("Performance".equals(selectedTab)) {
            context.fill(panelX + 110, panelY + 50, panelX + 210, panelY + 52, 0xFFFFFF00); // Yellow
        }


        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
