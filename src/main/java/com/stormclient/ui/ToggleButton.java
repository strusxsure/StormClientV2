package com.stormclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ToggleButton extends ButtonWidget {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("stormclient", "textures/gui/widgets.png");
    private boolean toggled;

    // UV coordinates for the toggle switch in widgets.png
    private static final int TOGGLE_WIDTH = 40;
    private static final int TOGGLE_HEIGHT = 20;
    private static final int OFF_U = 0;
    private static final int OFF_V = 64;
    private static final int ON_U = 0;
    private static final int ON_V = 84;
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    public ToggleButton(int x, int y, int width, int height, Text message, boolean defaultState) {
        super(x, y, width, height, message, button -> {}, DEFAULT_NARRATION_SUPPLIER);
        this.toggled = defaultState;
    }

    @Override
    public void onPress() {
        this.toggled = !this.toggled;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // Determine which part of the texture to use
        int u = toggled ? ON_U : OFF_U;
        int v = toggled ? ON_V : OFF_V;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.drawTexture(WIDGETS_TEXTURE, this.getX(), this.getY(), u, v, TOGGLE_WIDTH, TOGGLE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        RenderSystem.disableBlend();
    }

    public boolean isToggled() {
        return toggled;
    }
}
