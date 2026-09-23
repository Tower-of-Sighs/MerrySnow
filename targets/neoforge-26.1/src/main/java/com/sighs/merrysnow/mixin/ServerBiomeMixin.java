package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.init.Utils;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 26.1 changed the injection points: {@code shouldSnow} now decides through
 * {@code getPrecipitationAt(BlockPos, int)} instead of {@code warmEnoughToRain}, while
 * {@code shouldFreeze} still goes through {@code warmEnoughToRain}.
 */
@Mixin(value = Biome.class)
public class ServerBiomeMixin {
    @Unique
    LevelReader levelReader;

    @Inject(method = "shouldSnow", at = @At("HEAD"))
    private void store(LevelReader _levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        this.levelReader = _levelReader;
    }

    @Inject(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at = @At("HEAD"))
    private void store(LevelReader _levelReader, BlockPos blockPos, boolean flag, CallbackInfoReturnable<Boolean> cir) {
        this.levelReader = _levelReader;
    }

    @Redirect(method = "shouldSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation snow(Biome biome, BlockPos blockPos, int seaLevel) {
        String id = Utils.getBiomeId(levelReader, biome);
        boolean warmEnoughToRain = biome.warmEnoughToRain(blockPos, seaLevel);
        return SnowRules.shouldSnowCover(MerrySnowRuntime.config(), id, warmEnoughToRain)
                ? Biome.Precipitation.RAIN
                : Biome.Precipitation.SNOW;
    }

    @Redirect(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;I)Z"))
    private boolean freeze(Biome biome, BlockPos blockPos, int seaLevel) {
        String id = Utils.getBiomeId(levelReader, biome);
        return SnowRules.shouldFreeze(MerrySnowRuntime.config(), id, biome.warmEnoughToRain(blockPos, seaLevel));
    }
}
