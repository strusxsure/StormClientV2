package com.menuanimations.gui;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

public class ShaderHelper {
    private static final Identifier BLUR_SHADER = Identifier.of("minecraft", "shaders/post/blur.json");
    private static PostEffectProcessor blurShader;

    public static void loadShader() {
        if (blurShader != null) {
            blurShader.close();
        }

        try {
            blurShader = new PostEffectProcessor(MinecraftClient.getInstance().getTextureManager(), MinecraftClient.getInstance().getResourceManager(), MinecraftClient.getInstance().getFramebuffer(), BLUR_SHADER);
            blurShader.setupDimensions(MinecraftClient.getInstance().getWindow().getFramebufferWidth(), MinecraftClient.getInstance().getWindow().getFramebufferHeight());
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderShader(float delta) {
        if (blurShader != null) {
            blurShader.render(delta);
        }
    }

    public static void closeShader() {
        if (blurShader != null) {
            blurShader.close();
            blurShader = null;
        }
    }
}