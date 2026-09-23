package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.init.Utils;
import com.sighs.merrysnow.logic.SnowRules;
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
    @Shadow public abstract void setRainLevel(float p_46735_);

    @Shadow @Final public boolean isClientSide;

    @Inject(method = "getRainLevel", at = @At("RETURN"), cancellable = true)
    private void clear(float p_46723_, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(SnowRules.scaleRainLevel(MerrySnowRuntime.config(), cir.getReturnValue(),
                MerrySnowRuntime.randomSunnySnow()));
    }

    @Inject(method = "setRainLevel", at = @At("RETURN"))
    private void print(float value, CallbackInfo ci) {
        if (isClientSide) {
            if (value == 1.0F) {
                MerrySnowRuntime.setRandomSunnySnow(SnowRules.nextRandomSunnySnow(System.currentTimeMillis()));
            } else if (value == MerrySnowRuntime.config().sunnySnow()) {
                setRainLevel(1.0F);
            }
        }
    }

    @Redirect(method = {"updateSkyBrightness", "isRaining"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRainLevel(F)F"))
    private float clear(Level instance, float v) {
        return Utils.redirectRainLevel(instance, v);
    }
}
