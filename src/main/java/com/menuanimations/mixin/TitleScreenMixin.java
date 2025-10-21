package com.menuanimations.mixin;

import com.menuanimations.gui.SettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.of("Storm Settings"), button -> {
            this.client.setScreen(new SettingsScreen(this));
        }).dimensions(this.width / 2 - 100, this.height / 4 + 48 + 24 * 3, 200, 20).build());
    }
}
