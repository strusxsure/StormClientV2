package dev.storm.client.mixins;

import dev.storm.client.StormClient;
import dev.storm.client.modules.Module;
import dev.storm.client.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinGuiIngame {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (StormClient.moduleManager.getModuleByName("HUD").isToggled()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            int y = 2;
            int delay = 0;
            for (Module module : StormClient.moduleManager.getModules()) {
                if (module.isToggled()) {
                    mc.textRenderer.drawWithShadow(matrices, module.getName(), 2, y, ColorUtils.rainbow(delay));
                    y += 10;
                    delay += 100;
                }
            }
        }
    }
}
