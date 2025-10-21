package com.menuanimations.mixin;

import com.menuanimations.config.ConfigManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(Screen.class)
public class ScreenMixin {

    @Unique
    private long startTime = 0;
    @Unique
    private boolean animating = false;

    @Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        if (ConfigManager.getConfig().isMenuAnimationEnabled()) {
            this.startTime = System.currentTimeMillis();
            this.animating = true;
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (animating && ConfigManager.getConfig().isMenuAnimationEnabled()) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            float animationTime = ConfigManager.getConfig().getAnimationSpeed() * 1000.0f;

            float progress = Math.min(elapsedTime / animationTime, 1.0f);

            // Fade-in and scale animation
            float scale = 0.95f + (0.05f * progress);
            float alpha = progress;

            // Set the alpha for the fade-in effect
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

            context.getMatrices().push();
            // Use the screen's actual width and height for centering
            context.getMatrices().translate(((Screen)(Object)this).width / 2.0, ((Screen)(Object)this).height / 2.0, 0);
            context.getMatrices().scale(scale, scale, 1.0f);
            context.getMatrices().translate(-((Screen)(Object)this).width / 2.0, -((Screen)(Object)this).height / 2.0, 0);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (animating && ConfigManager.getConfig().isMenuAnimationEnabled()) {
            context.getMatrices().pop();
            // Reset the shader color
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            float animationTime = ConfigManager.getConfig().getAnimationSpeed() * 1000.0f;

            if (elapsedTime >= animationTime) {
                animating = false;
            }
        }
    }
}
