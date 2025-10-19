package dev.storm.client.gui;

import dev.storm.client.StormClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Map;

public class HudEditor extends Screen {

    private String draggingComponent;
    private int dragX, dragY;

    public HudEditor() {
        super(Text.of("HUD Editor"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Map.Entry<String, int[]> entry : StormClient.hudManager.getComponentPositions().entrySet()) {
            String componentName = entry.getKey();
            int[] position = entry.getValue();
            int x = position[0];
            int y = position[1];

            if (draggingComponent != null && draggingComponent.equals(componentName)) {
                x = mouseX - dragX;
                y = mouseY - dragY;
                StormClient.hudManager.getComponentPositions().put(componentName, new int[]{x, y});
            }

            fill(matrices, x - 1, y - 1, x + minecraft.textRenderer.getWidth(componentName) + 1, y + minecraft.textRenderer.fontHeight + 1, 0x80000000);
            minecraft.textRenderer.drawWithShadow(matrices, componentName, x, y, -1);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Map.Entry<String, int[]> entry : StormClient.hudManager.getComponentPositions().entrySet()) {
            String componentName = entry.getKey();
            int[] position = entry.getValue();
            int x = position[0];
            int y = position[1];

            if (mouseX >= x && mouseX <= x + minecraft.textRenderer.getWidth(componentName) && mouseY >= y && mouseY <= y + minecraft.textRenderer.fontHeight) {
                draggingComponent = componentName;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingComponent = null;
        StormClient.hudManager.saveLayout();
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
