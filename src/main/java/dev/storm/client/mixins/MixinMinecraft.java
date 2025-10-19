package dev.storm.client.mixins;

import dev.storm.client.StormClient;
import dev.storm.client.gui.ClickGUI;
import dev.storm.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraft {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && StormClient.moduleManager != null) {
            if (mc.currentScreen == null) {
                if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                    mc.setScreen(new ClickGUI());
                }

                for (Module module : StormClient.moduleManager.getModules()) {
                    if (module.getKeybind() != 0 && InputUtil.isKeyPressed(mc.getWindow().getHandle(), module.getKeybind())) {
                        module.toggle();
                    }
                }
            }

            for (Module module : StormClient.moduleManager.getModules()) {
                if (module.isToggled()) {
                    module.onTick();
                }
            }
        }
    }
}
