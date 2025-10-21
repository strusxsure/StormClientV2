package com.menuanimations.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.awt.Color;
import java.util.function.Consumer;

public class FeatherToggleWidget extends ButtonWidget {

    private boolean enabled;
    private final String label;
    private final Consumer<Boolean> action;

    public FeatherToggleWidget(int x, int y, int width, int height, String label, boolean initialValue, Consumer<Boolean> action) {
        super(x, y, width, height, Text.of(""), button -> {}, DEFAULT_NARRATION_SUPPLIER);
        this.enabled = initialValue;
        this.label = label;
        this.action = action;
    }

    @Override
    public void onPress() {
        this.enabled = !this.enabled;
        this.action.accept(this.enabled);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        // Background
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, new Color(50, 50, 50, 200).getRGB());
        // Slider
        int sliderX = enabled ? this.getX() + this.width - 20 : this.getX();
        context.fill(sliderX, this.getY(), sliderX + 20, this.getY() + this.height, enabled ? Color.YELLOW.getRGB() : Color.GRAY.getRGB());
        // Text
        context.drawTextWithShadow(client.textRenderer, label, this.getX() + 5, this.getY() + 6, Color.WHITE.getRGB());
        context.drawTextWithShadow(client.textRenderer, enabled ? "ON" : "OFF", this.getX() + this.width - 30, this.getY() + 6, Color.WHITE.getRGB());
    }
}
