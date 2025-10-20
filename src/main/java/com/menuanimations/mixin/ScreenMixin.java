package com.menuanimations.mixin;

import com.menuanimations.animation.AnimationState;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Unique
    private long animationStartTime;

    @Unique
    private AnimationState animationState = AnimationState.FADE_IN;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("HEAD"))
    private void onInit(MinecraftClient client, int width, int height, CallbackInfo ci) {
        this.animationStartTime = System.currentTimeMillis();
        this.animationState = AnimationState.FADE_IN;
    }

    @Inject(method = "close", at = @At("HEAD"), cancellable = true)
    private void onClose(CallbackInfo ci) {
        if (this.animationState != AnimationState.FADE_OUT) {
            this.animationStartTime = System.currentTimeMillis();
            this.animationState = AnimationState.FADE_OUT;
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        long elapsedTime = System.currentTimeMillis() - this.animationStartTime;
        float duration = 700.0f;
        float alpha = 1.0f;
        float scale = 1.0f;

        if (this.animationState == AnimationState.FADE_IN) {
            if (elapsedTime < duration) {
                float progress = elapsedTime / duration;
                alpha = progress;
                scale = 0.95f + (progress * 0.05f);
            } else {
                this.animationState = AnimationState.NONE;
            }
        } else if (this.animationState == AnimationState.FADE_OUT) {
            if (elapsedTime < duration) {
                float progress = elapsedTime / duration;
                alpha = 1.0f - progress;
                scale = 1.0f - (progress * 0.05f);
            } else {
                MinecraftClient.getInstance().setScreen(null);
            }
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

        context.getMatrices().push();
        context.getMatrices().translate(this.width / 2.0, this.height / 2.0, 0.0);
        context.getMatrices().scale(scale, scale, 1.0f);
        context.getMatrices().translate(-this.width / 2.0, -this.height / 2.0, 0.0);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void afterRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.getMatrices().pop();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}