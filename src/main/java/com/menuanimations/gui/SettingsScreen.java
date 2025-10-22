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

    private FeatherToggleWidget animationToggle;
    private SliderWidget animationSpeedSlider;

    public SettingsScreen(Screen parent) {
        super(Text.of("Storm Settings"));
        this.parent = parent;
        this.config = ConfigManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();

        int panelX = (this.width - 400) / 2;
        int contentX = panelX + 50; // Offset for sidebar

        // Feather-style toggle
        animationToggle = new FeatherToggleWidget(contentX, this.height / 2 - 40, 200, 20, "Animation", config.isMenuAnimationEnabled(), (enabled) -> {
            config.setMenuAnimationEnabled(enabled);
            ConfigManager.saveConfig();
        });
        this.addDrawableChild(animationToggle);

        // Animation speed slider
        animationSpeedSlider = new SliderWidget(contentX, this.height / 2 - 10, 200, 20, Text.of("Animation Speed: " + config.getAnimationSpeed()), config.getAnimationSpeed()) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Animation Speed: " + String.format("%.2f", value * 2.0f)));
            }

            @Override
            protected void applyValue() {
                config.setAnimationSpeed((float) this.value * 2.0f);
                ConfigManager.saveConfig();
            }
        };

        // Sidebar buttons
        int panelY = (this.height - 250) / 2;
        this.addDrawableChild(new IconButtonWidget(panelX + 10, panelY + 40, 30, 30, Text.of("A"), button -> {
            selectedTab = "Animations";
            updateWidgetVisibility();
        }));
        this.addDrawableChild(new IconButtonWidget(panelX + 10, panelY + 80, 30, 30, Text.of("⚡"), button -> {
            selectedTab = "Performance";
            updateWidgetVisibility();
        }));

        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {
        animationToggle.visible = "Animations".equals(selectedTab);
        animationSpeedSlider.visible = "Animations".equals(selectedTab);
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

        // Sidebar background
        RenderUtil.drawRoundedRect(context, panelX, panelY, 50, panelHeight, 10, 0x50000000);


        // Header
        context.fill(panelX, panelY, panelX + panelWidth, panelY + 30, 0xC8141414); // Darker semi-transparent black
        context.drawTextWithShadow(this.textRenderer, "⚡ Storm Settings", panelX + 10, panelY + 10, 0xFFFFFFFF); // White

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
