package dev.storm.client.mixins;

import dev.storm.client.StormClient;
import dev.storm.client.modules.impl.render.ESP;
import dev.storm.client.modules.impl.visual.NameTags;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"))
    private void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ESP esp = (ESP) StormClient.moduleManager.getModuleByName("ESP");
        if (esp != null && esp.isToggled() && entity.isAlive()) {
            // This is a simplified way to render an outline.
            // A more robust implementation would use a custom RenderLayer.
            entity.setGlowing(true);
        }

        NameTags nameTags = (NameTags) StormClient.moduleManager.getModuleByName("NameTags");
        if (nameTags != null && nameTags.isToggled()) {
            // This is a simplified way to make nametags larger.
            // A more robust implementation would involve custom text rendering.
            matrices.push();
            matrices.scale(1.5f, 1.5f, 1.5f);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderTail(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ESP esp = (ESP) StormClient.moduleManager.getModuleByName("ESP");
        if (esp != null && esp.isToggled() && entity.isAlive()) {
            entity.setGlowing(false);
        }

        NameTags nameTags = (NameTags) StormClient.moduleManager.getModuleByName("NameTags");
        if (nameTags != null && nameTags.isToggled()) {
            matrices.pop();
        }
    }
}
