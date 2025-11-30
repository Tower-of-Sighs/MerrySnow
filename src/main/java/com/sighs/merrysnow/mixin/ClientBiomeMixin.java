package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.init.Utils;
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
    private void noWeather(BlockPos blockPos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        String id = Utils.getBiomeId(Minecraft.getInstance().level, (Biome) (Object) this);
        String enforce = Config.ENFORCE_SNOW_WEATHER.get();
        if (!"default".equals(enforce)) {
            cir.setReturnValue(Boolean.parseBoolean(enforce) ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN);
            return;
        }
        String result = Config.getWeatherModify(id);
        if ("none".equals(result)) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        } else if ("snow".equals(result)) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        } else if ("rain".equals(result)) {
            cir.setReturnValue(Biome.Precipitation.RAIN);
        }
    }

    @Inject(method = "hasPrecipitation", at = @At("HEAD"), cancellable = true)
    private void forceHasPrecipitation(CallbackInfoReturnable<Boolean> cir) {
        String enforce = Config.ENFORCE_SNOW_WEATHER.get();
        if (!"default".equals(enforce)) {
            cir.setReturnValue(true);
            return;
        }
        String id = Utils.getBiomeId(Minecraft.getInstance().level, (Biome) (Object) this);
        String result = Config.getWeatherModify(id);
        if (!"default".equals(result) && !"none".equals(result)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "getPrecipitationAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean customSnowWeather(Biome biome, BlockPos blockPos) {
        if (Config.ENFORCE_SNOW_WEATHER.get().equals("default")) {
            String id = Utils.getBiomeId(Minecraft.getInstance().level, biome);
            String result = Config.getWeatherModify(id);
            if (result.equals("default")) {
                return biome.coldEnoughToSnow(blockPos);
            } else return result.equals("snow");
        } else return Boolean.parseBoolean(Config.ENFORCE_SNOW_WEATHER.get());
    }
}