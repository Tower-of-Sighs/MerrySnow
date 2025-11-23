package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.init.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientLevel.class)
public class ClientLevelMixin {
    @Redirect(method = {"getSkyColor", "getSkyDarken"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float clear(ClientLevel instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}
