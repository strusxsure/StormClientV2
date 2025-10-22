package com.menuanimations.gui;

import com.menuanimations.config.ConfigManager;
import com.menuanimations.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends Screen {

    private final Screen parent;
    private ModConfig config;
    private Tab selectedTab = Tab.ANIMATIONS;

    private FeatherToggleWidget animationToggle;
    private FeatherSliderWidget animationSpeedSlider;
    private List<IconButtonWidget> tabButtons = new ArrayList<>();

    private enum Tab {
        ANIMATIONS("Animations", 0, 0, 0, 32, 256, 256), // Assuming icon size is 32x32
        PERFORMANCE("Performance", 32, 0, 32, 32, 256, 256);

        private final String name;
        private final int u;
        private final int v;
        private final int hoveredU;
        private final int hoveredV;
        private final int textureWidth;
        private final int textureHeight;

        Tab(String name, int u, int v, int hoveredU, int hoveredV, int textureWidth, int textureHeight) {
            this.name = name;
            this.u = u;
            this.v = v;
            this.hoveredU = hoveredU;
            this.hoveredV = hoveredV;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
        }
    }

    public SettingsScreen(Screen parent) {
        super(Text.of("Storm Settings"));
        this.parent = parent;
        this.config = ConfigManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();

        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        int contentX = panelX + 50; // Offset for sidebar

        // Sidebar buttons
        tabButtons.clear();
        for (Tab tab : Tab.values()) {
            IconButtonWidget button = new IconButtonWidget(
                panelX + 10,
                panelY + 40 + (tab.ordinal() * 40),
                30, 30,
                tab.u, tab.v,
                tab.hoveredU, tab.hoveredV,
                tab.textureWidth, tab.textureHeight,
                b -> {
                    selectedTab = tab;
                    updateWidgetVisibility();
                    for (IconButtonWidget btn : tabButtons) {
                        btn.setSelected(btn == b);
                    }
                }
            );
            button.setSelected(tab == selectedTab);
            this.addDrawableChild(button);
            tabButtons.add(button);
        }

        // Feather-style toggle
        animationToggle = new FeatherToggleWidget(contentX + 10, this.height / 2 - 40, 200, 20, "Animation", config.isMenuAnimationEnabled(), (enabled) -> {
            config.setMenuAnimationEnabled(enabled);
            ConfigManager.saveConfig();
        });
        this.addDrawableChild(animationToggle);

        // Animation speed slider
        animationSpeedSlider = new FeatherSliderWidget(contentX + 10, this.height / 2 - 10, 200, 20, "Animation Speed", config.getAnimationSpeed() / 2.0, value -> {
            config.setAnimationSpeed(value.floatValue() * 2.0f);
            ConfigManager.saveConfig();
        });
        this.addDrawableChild(animationSpeedSlider);

        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {
        animationToggle.visible = selectedTab == Tab.ANIMATIONS;
        animationSpeedSlider.visible = selectedTab == Tab.ANIMATIONS;
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
