package com.stormclient.mixin;

import com.stormclient.ui.StormSettingsScreen;
import com.stormclient.ui.IconButton;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    private static final Identifier WIDGETS_TEXTURE = Identifier.of("stormclient", "textures/gui/widgets.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        this.addDrawableChild(new IconButton(this.width - 25, 5, 20, 20, WIDGETS_TEXTURE, 0, 0, 256, 256, (button) -> {
            this.client.setScreen(new StormSettingsScreen());
        }));
    }
}
