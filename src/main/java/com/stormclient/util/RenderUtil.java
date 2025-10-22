package com.stormclient.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;

public class RenderUtil {

    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        float f = (color >> 24 & 255) / 255.0F;
        float g = (color >> 16 & 255) / 255.0F;
        float h = (color >> 8 & 255) / 255.0F;
        float k = (color & 255) / 255.0F;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        // Central rectangle
        bufferBuilder.vertex(matrix, x + radius, y + height - radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + height - radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + radius, y + radius, 0.0F).color(g, h, k, f);

        // Top rectangle
        bufferBuilder.vertex(matrix, x + radius, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + radius, y, 0.0F).color(g, h, k, f);

        // Bottom rectangle
        bufferBuilder.vertex(matrix, x + radius, y + height, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + height, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + height - radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + radius, y + height - radius, 0.0F).color(g, h, k, f);

        // Left rectangle
        bufferBuilder.vertex(matrix, x, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + radius, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + radius, y + height - radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x, y + height - radius, 0.0F).color(g, h, k, f);

        // Right rectangle
        bufferBuilder.vertex(matrix, x + width - radius, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width, y + radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width, y + height - radius, 0.0F).color(g, h, k, f);
        bufferBuilder.vertex(matrix, x + width - radius, y + height - radius, 0.0F).color(g, h, k, f);

        // Draw corners
        drawCorner(matrix, bufferBuilder, x + radius, y + radius, radius, 180, g, h, k, f);
        drawCorner(matrix, bufferBuilder, x + width - radius, y + radius, radius, 270, g, h, k, f);
        drawCorner(matrix, bufferBuilder, x + width - radius, y + height - radius, radius, 0, g, h, k, f);
        drawCorner(matrix, bufferBuilder, x + radius, y + height - radius, radius, 90, g, h, k, f);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    private static void drawCorner(Matrix4f matrix, BufferBuilder bufferBuilder, int x, int y, int radius, int startAngle, float r, float g, float b, float a) {
        for (int i = 0; i < 90; i += 3) {
            float angle1 = (float) Math.toRadians(startAngle + i);
            float angle2 = (float) Math.toRadians(startAngle + i + 3);
            float x1 = x + (float) Math.cos(angle1) * radius;
            float y1 = y + (float) Math.sin(angle1) * radius;
            float x2 = x + (float) Math.cos(angle2) * radius;
            float y2 = y + (float) Math.sin(angle2) * radius;
            bufferBuilder.vertex(matrix, x, y, 0.0F).color(r, g, b, a);
            bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(r, g, b, a);
            bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(r, g, b, a);
            bufferBuilder.vertex(matrix, x, y, 0.0F).color(r, g, b, a);
        }
    }
}
