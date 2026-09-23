package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.init.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LevelRenderer.class)
public class LevelRendererMixin {
    @Redirect(method = {"renderSky", "tickRain", "renderSnowAndRain"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float clear(ClientLevel instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}
