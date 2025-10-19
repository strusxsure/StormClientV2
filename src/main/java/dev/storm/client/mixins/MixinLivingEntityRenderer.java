package dev.storm.client.mixins;

import dev.storm.client.StormClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(LivingEntity livingEntity, float f, float g, net.minecraft.client.util.math.MatrixStack matrixStack, net.minecraft.client.render.VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (StormClient.moduleManager.getModuleByName("FPSBoost").isToggled()) {
            if (livingEntity.hasVehicle()) {
                // Don't render shadows for entities that are riding other entities.
                ci.cancel();
            }
        }
    }
}
