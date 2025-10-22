package com.menuanimations.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IconButtonWidget extends ButtonWidget {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("menuanimations", "textures/gui/widgets.png");
    private final int u;
    private final int v;
    private final int hoveredU;
    private final int hoveredV;
    private final int textureWidth;
    private final int textureHeight;
    private boolean selected;

    public IconButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredU, int hoveredV, int textureWidth, int textureHeight, PressAction onPress) {
        super(x, y, width, height, Text.literal(""), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.u = u;
        this.v = v;
        this.hoveredU = hoveredU;
        this.hoveredV = hoveredV;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.selected = false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int currentU = this.isHovered() || this.selected ? this.hoveredU : this.u;
        int currentV = this.isHovered() || this.selected ? this.hoveredV : this.v;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.drawTexture(WIDGETS_TEXTURE, this.getX(), this.getY(), currentU, currentV, this.width, this.height, this.textureWidth, this.textureHeight);
        RenderSystem.disableBlend();
    }
}
