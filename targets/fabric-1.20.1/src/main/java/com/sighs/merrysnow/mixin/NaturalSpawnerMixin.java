package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.init.Utils;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fabric API 0.92 exposes no spawn-cancelling event, so the snow guard is implemented as a mixin.
 *
 * <p>{@code isValidPositionForMob} is only reached from the {@code NATURAL} spawn path of
 * {@code NaturalSpawner}, which is the same place Forge/NeoForge fire their finalize-spawn event.
 * Returning {@code false} skips the mob before it is added to the level.
 */
@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {
    @Inject(method = "isValidPositionForMob", at = @At("HEAD"), cancellable = true)
    private static void merrysnow$cancelSnowyMonsterSpawn(ServerLevel level, Mob mob, double distance, CallbackInfoReturnable<Boolean> cir) {
        if (!MerrySnowRuntime.config().merrySnowWeather()) return;

        BlockPos blockPos = mob.blockPosition();
        Biome biome = level.getBiome(blockPos).value();
        if (!level.isRaining()) return;
        if (!biome.hasPrecipitation()) return;
        MobCategory category = mob.getType().getCategory();

        boolean isSnowing = SnowRules.isSnowing(MerrySnowRuntime.config(),
                Utils.getBiomeId(level, biome),
                biome.coldEnoughToSnow(blockPos));
        boolean isMonster = !category.isFriendly() && !category.isPersistent();

        if (isSnowing && isMonster) {
            cir.setReturnValue(false);
        }
    }
}
