package com.menuanimations.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class FeatherToggleWidget extends ButtonWidget {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("menuanimations", "textures/gui/widgets.png");
    private boolean enabled;
    private final String label;
    private final Consumer<Boolean> action;

    // UV coordinates for the toggle switch in widgets.png
    private static final int TOGGLE_WIDTH = 40;
    private static final int TOGGLE_HEIGHT = 20;
    private static final int OFF_U = 0;
    private static final int OFF_V = 64;
    private static final int ON_U = 0;
    private static final int ON_V = 84;
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

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

        // Draw the label text
        context.drawTextWithShadow(client.textRenderer, label, this.getX(), this.getY() + (this.height - 8) / 2, 0xFFFFFFFF); // White

        // Determine which part of the texture to use
        int u = enabled ? ON_U : OFF_U;
        int v = enabled ? ON_V : OFF_V;

        // Draw the toggle switch graphic at the right end of the widget area
        int toggleX = this.getX() + this.width - TOGGLE_WIDTH;
        int toggleY = this.getY();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.drawTexture(WIDGETS_TEXTURE, toggleX, toggleY, u, v, TOGGLE_WIDTH, TOGGLE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        RenderSystem.disableBlend();
    }
}
