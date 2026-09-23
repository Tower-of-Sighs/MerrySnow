package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.init.Utils;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Biome.class)
public class ClientBiomeMixin {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void noWeather(BlockPos p_265163_, CallbackInfoReturnable<Biome.Precipitation> cir) {
        String id = Utils.getBiomeId(Minecraft.getInstance().level, (Biome) (Object) this);
        if (SnowRules.precipitationDisabled(MerrySnowRuntime.config(), id)) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        }
    }

    @Redirect(method = "getPrecipitationAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean customSnowWeather(Biome biome, BlockPos blockPos) {
        String id = Utils.getBiomeId(Minecraft.getInstance().level, biome);
        return SnowRules.isSnowing(MerrySnowRuntime.config(), id, biome.coldEnoughToSnow(blockPos));
    }
}
