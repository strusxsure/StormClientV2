package com.menuanimations.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
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
    private void onRender(CallbackInfo ci) {
        long elapsedTime = System.currentTimeMillis() - this.animationStartTime;
        float alpha = 1.0f;

        if (this.animationState == AnimationState.FADE_IN) {
            if (elapsedTime < 500) {
                alpha = (float) elapsedTime / 500.0f;
            } else {
                this.animationState = AnimationState.NONE;
            }
        } else if (this.animationState == AnimationState.FADE_OUT) {
            if (elapsedTime < 500) {
                alpha = 1.0f - ((float) elapsedTime / 500.0f);
            } else {
                MinecraftClient.getInstance().setScreen(null);
            }
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void afterRender(CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Unique
    private enum AnimationState {
        NONE,
        FADE_IN,
        FADE_OUT
    }
}