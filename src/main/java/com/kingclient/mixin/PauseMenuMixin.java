package com.kingclient.mixin;

import com.kingclient.gui.KingPauseScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class PauseMenuMixin {
    
    @Inject(method = \"pauseGame\", at = @At(\"HEAD\"), cancellable = true)
    private void onPauseGame(CallbackInfoReturnable<PauseScreen> cir) {
        MinecraftClient client = (MinecraftClient)(Object)this;
        if (client.currentScreen == null) {
            cir.setReturnValue(new KingPauseScreen());
        }
    }
}
