package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.init.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FogRenderer.class)
public class FogRendererMixin {
    @Redirect(method = "setupColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private static float clear(ClientLevel instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}
