package dev.storm.client.gui;

import dev.storm.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Button {
    private int x, y, width, height;
    private Module module;

    public Button(int x, int y, int width, int height, Module module) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.module = module;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int color = module.isToggled() ? 0xFF00FF00 : 0xFFFFFFFF;
        DrawableHelper.fill(matrices, x, y, x + width, y + height, 0x80000000);
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, module.getName(), x + 2, y + 2, color);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            module.toggle();
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
