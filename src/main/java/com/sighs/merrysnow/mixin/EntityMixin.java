package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class)
public class EntityMixin {
    @Inject(method = "isFullyFrozen", at = @At("HEAD"), cancellable = true)
    private void merry(CallbackInfoReturnable<Boolean> cir) {
        if (!Config.MERRY_POWDER_SNOW.get()) return;
        cir.setReturnValue(false);
    }
}
