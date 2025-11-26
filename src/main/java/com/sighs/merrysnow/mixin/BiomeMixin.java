package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.init.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Biome.class)
public class BiomeMixin {
    @Redirect(method = "getPrecipitationAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean customSnowWeather(Biome biome, BlockPos blockPos) {
        if (Config.ENFORCE_SNOW_WEATHER.get().equals("default")) {
            String id = Utils.getBiomeId(Minecraft.getInstance().level, biome);
            String result = Config.getWeatherModify(id);
            if (result.equals("default")) {
                return biome.coldEnoughToSnow(blockPos);
            } else return result.equals("snow");
        }
        else return Boolean.parseBoolean(Config.ENFORCE_SNOW_WEATHER.get());
    }
}
