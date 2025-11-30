package com.sighs.merrysnow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.sighs.merrysnow.event.SpawnEvents;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.SpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {
    @WrapOperation(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z"
            )
    )
    private boolean merrysnow$wrapSpawnerObstruction(Mob mob, LevelReader level, Operation<Boolean> original, @Local SpawnData spawnData) {
        var serverAccessor = (ServerLevelAccessor) level;
        var spawner = (BaseSpawner) (Object) this;
        var result = SpawnEvents.POSITION_CHECK.invoker().onCheck(mob, serverAccessor, EntitySpawnReason.SPAWNER, spawner);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        if (spawnData.getCustomSpawnRules().isPresent()) {
            return original.call(mob, level);
        }
        boolean rules = mob.checkSpawnRules(serverAccessor, EntitySpawnReason.SPAWNER);
        boolean obstruction = original.call(mob, level);
        return rules && obstruction;
    }
}