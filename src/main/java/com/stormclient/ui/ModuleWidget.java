package com.stormclient.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModuleWidget extends ClickableWidget {

    private final Identifier icon;
    private final ToggleButton toggleButton;
    private final int u;
    private final int v;

    public ModuleWidget(int x, int y, int width, int height, Text message, Identifier icon, int u, int v) {
        super(x, y, width, height, message);
        this.icon = icon;
        this.toggleButton = new ToggleButton(this.getX() + this.width - 45, this.getY() + (this.height - 20) / 2, 40, 20, Text.literal(""), true);
        this.u = u;
        this.v = v;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // Background
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0x66000000);

        // Icon
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.drawTexture(icon, this.getX() + 5, this.getY() + (this.height - 32) / 2, u, v, 32, 32, 256, 256);
        RenderSystem.disableBlend();

        // Title
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, this.getMessage(), this.getX() + 45, this.getY() + (this.height - 8) / 2, 0xFFFFFFFF);

        // Toggle Button
        toggleButton.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (toggleButton.isMouseOver(mouseX, mouseY)) {
            toggleButton.onPress();
            return true;
        }
        return false;
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
