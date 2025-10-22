package com.stormclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stormclient.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class StormSettingsScreen extends Screen {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("stormclient", "textures/gui/widgets.png");
    private List<IconButton> tabButtons = new ArrayList<>();
    private ModuleWidget animationModule;
    private int selectedTab = 0;
    private float alpha = 0;

    public StormSettingsScreen() {
        super(Text.literal("Storm Settings"));
    }

    @Override
    protected void init() {
        super.init();

        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;

        // Sidebar buttons
        tabButtons.clear();
        IconButton settingsButton = new IconButton(panelX + 15, panelY + 40, 30, 30, WIDGETS_TEXTURE, 0, 0, 256, 256, button -> {
            selectedTab = 0;
            updateWidgetVisibility();
        });
        settingsButton.setSelected(true);
        this.addDrawableChild(settingsButton);
        tabButtons.add(settingsButton);

        IconButton aboutButton = new IconButton(panelX + 15, panelY + 80, 30, 30, WIDGETS_TEXTURE, 32, 0, 256, 256, button -> {
            selectedTab = 1;
            updateWidgetVisibility();
        });
        this.addDrawableChild(aboutButton);
        tabButtons.add(aboutButton);

        // Module
        animationModule = new ModuleWidget(panelX + 70, panelY + 40, 320, 50, Text.literal("Menu Animations"), WIDGETS_TEXTURE, 32, 0);
        this.addDrawableChild(animationModule);

        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {
        animationModule.visible = (selectedTab == 0);
        for (int i = 0; i < tabButtons.size(); i++) {
            tabButtons.get(i).setSelected(i == selectedTab);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (alpha < 1) {
            alpha += delta / 10;
            if (alpha > 1) {
                alpha = 1;
            }
        }

        int panelColor = new Color(0, 0, 0, (int) (150 * alpha)).getRGB();
        int sidebarColor = new Color(0, 0, 0, (int) (80 * alpha)).getRGB();
        int textColor = new Color(255, 255, 255, (int) (255 * alpha)).getRGB();
        int accentColor = new Color(233, 75, 75, (int) (255 * alpha)).getRGB();

        // Render the blurred background
        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(0, 0, this.width, this.height, new Color(0, 0, 0, (int) (128 * alpha)).getRGB());

        // Main Panel
        int panelX = (this.width - 400) / 2;
        int panelY = (this.height - 250) / 2;
        int panelWidth = 400;
        int panelHeight = 250;
        RenderUtil.drawRoundedRect(context, panelX, panelY, panelWidth, panelHeight, 10, panelColor);

        // Sidebar
        int sidebarWidth = 60;
        RenderUtil.drawRoundedRect(context, panelX, panelY, sidebarWidth, panelHeight, 10, sidebarColor);

        // Title
        context.drawTextWithShadow(this.textRenderer, "Storm Settings", panelX + sidebarWidth + 10, panelY + 10, textColor);

        // Accent bar
        if (!tabButtons.isEmpty()) {
            int selectedTabY = tabButtons.get(selectedTab).getY();
            context.fill(panelX, selectedTabY, panelX + 3, selectedTabY + 30, accentColor);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
