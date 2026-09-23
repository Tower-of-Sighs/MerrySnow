package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.init.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ClientLevel.class)
public class ClientLevelMixin {
    @Redirect(method = {"getSkyColor", "getSkyDarken", "getCloudColor"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float clear(ClientLevel instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}
