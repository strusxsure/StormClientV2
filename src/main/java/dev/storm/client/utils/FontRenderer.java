package dev.storm.client.utils;

import net.minecraft.client.MinecraftClient;

import net.minecraft.client.util.math.MatrixStack;

public class FontRenderer {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawString(MatrixStack matrices, String text, float x, float y, int color) {
        mc.textRenderer.draw(matrices, text, x, y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrices, String text, float x, float y, int color) {
        mc.textRenderer.drawWithShadow(matrices, text, x, y, color);
    }

    public static int getStringWidth(String text) {
        return mc.textRenderer.getWidth(text);
    }
}
