package com.menuanimations.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.function.Consumer;

public class FeatherSliderWidget extends SliderWidget {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("menuanimations", "textures/gui/widgets.png");
    private final String label;
    private final Consumer<Double> action;

    // UV coordinates for the slider in widgets.png
    private static final int TRACK_U = 0;
    private static final int TRACK_V = 104;
    private static final int TRACK_WIDTH = 200;
    private static final int TRACK_HEIGHT = 8;

    private static final int HANDLE_U = 0;
    private static final int HANDLE_V = 112;
    private static final int HANDLE_HOVERED_U = 8;
    private static final int HANDLE_WIDTH = 8;
    private static final int HANDLE_HEIGHT = 16;

    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;


    public FeatherSliderWidget(int x, int y, int width, int height, String label, double initialValue, Consumer<Double> action) {
        super(x, y, width, height, Text.of(""), initialValue);
        this.label = label;
        this.action = action;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Text.of(String.format("%s: %.2f", label, this.value * 2.0f)));
    }

    @Override
    protected void applyValue() {
        action.accept(this.value);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Draw the label
        context.drawTextWithShadow(client.textRenderer, this.getMessage(), this.getX(), this.getY() - 12, 0xFFFFFFFF);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Draw the slider track
        int trackY = this.getY() + (this.height - TRACK_HEIGHT) / 2;
        context.drawTexture(WIDGETS_TEXTURE, this.getX(), trackY, TRACK_U, TRACK_V, this.width, TRACK_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // Draw the slider handle
        int handleU = this.isHovered() || this.isFocused() ? HANDLE_HOVERED_U : HANDLE_U;
        int handleX = this.getX() + (int)(this.value * (this.width - HANDLE_WIDTH));
        int handleY = this.getY() + (this.height - HANDLE_HEIGHT) / 2;
        context.drawTexture(WIDGETS_TEXTURE, handleX, handleY, handleU, HANDLE_V, HANDLE_WIDTH, HANDLE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        RenderSystem.disableBlend();
    }
}
