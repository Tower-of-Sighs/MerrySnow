package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.init.ClientUtils;
import com.sighs.merrysnow.init.Utils;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow
    public abstract void setRainLevel(float f);

    @Shadow
    @Final
    private boolean isClientSide;

    @Inject(method = "getRainLevel", at = @At("RETURN"), cancellable = true)
    private void clear(float p_46723_, CallbackInfoReturnable<Float> cir) {
        if (Config.RANDOM_SUNNY_SNOW.get()) {
            cir.setReturnValue(cir.getReturnValue() * ClientUtils.randomSunnySnow);
        } else {
            cir.setReturnValue(cir.getReturnValue() * Config.SUNNY_SNOW.get().floatValue());
        }
    }

    @Inject(method = "setRainLevel", at = @At("RETURN"))
    private void print(float value, CallbackInfo ci) {
        if (isClientSide) {
            if (value == 1.0F) {
                ClientUtils.randomSunnySnow = (System.currentTimeMillis() % 8) / 10F + 0.3F;
            } else if (value == Config.SUNNY_SNOW.get().floatValue()) {
                setRainLevel(1.0F);
            }
        }
    }

    @Redirect(method = {"updateSkyBrightness", "isRaining"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"))
    private float clear(Level instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}