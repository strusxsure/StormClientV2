package com.menuanimations.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.ColorHelper;

public class RenderUtil {

    /**
     * Draws a rounded rectangle using the new DrawContext API (Minecraft 1.21+)
     */
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        // Draw the base rectangle (no blur, just color)
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + width, y + height - radius, color);

        // Draw corner circles (simulate round corners)
        drawCorner(context, x + radius, y + radius, radius, color); // top-left
        drawCorner(context, x + width - radius, y + radius, radius, color); // top-right
        drawCorner(context, x + radius, y + height - radius, radius, color); // bottom-left
        drawCorner(context, x + width - radius, y + height - radius, radius, color); // bottom-right

        RenderSystem.disableBlend();
    }

    private static void drawCorner(DrawContext context, int cx, int cy, int radius, int color) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x * x + y * y <= radius * radius) {
                    context.fill(cx + x, cy + y, cx + x + 1, cy + y + 1, color);
                }
            }
        }
    }

    /**
     * Draws a blurred background using alpha blending (for HUD / Settings screen)
     */
    public static void drawBlurBackground(DrawContext context, int x, int y, int width, int height, float alpha) {
        int blurColor = ColorHelper.Argb.getArgb((int)(255 * alpha), 0, 0, 0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.fill(x, y, x + width, y + height, blurColor);
        RenderSystem.disableBlend();
    }
}
