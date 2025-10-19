package dev.storm.client.gui;

import dev.storm.client.StormClient;
import dev.storm.client.modules.Category;
import dev.storm.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import dev.storm.client.gui.Animation;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    private int x, y, width, height;
    private Category category;
    private boolean dragging;
    private int dragX, dragY;
    private List<Button> buttons = new ArrayList<>();
    private Animation animation;

    public Panel(int x, int y, int width, int height, Category category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        this.animation = new Animation(0.1);
        this.animation.setForward(true);

        int yOffset = y + height;
        for (Module module : StormClient.moduleManager.getModules()) {
            if (module.getCategory() == category) {
                buttons.add(new Button(x, yOffset, width, height, module));
                yOffset += height;
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        animation.update();
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        int alpha = (int) (0x80 * animation.getValue());
        DrawableHelper.fill(matrices, x, y, x + width, y + height, alpha << 24);
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, category.getName(), x + 2, y + 2, -1);

        for (Button button : buttons) {
            button.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = (int) (mouseX - x);
            dragY = (int) (mouseY - y);
        }

        for (Button b : buttons) {
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
