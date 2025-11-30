package com.sighs.merrysnow.init;

import com.sighs.merrysnow.event.SpawnEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.SpawnData;

public class EventHooks {
    public static boolean checkSpawnPlacements(EntityType<?> entityType, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random, boolean defaultResult) {
        var result = SpawnEvents.SPAWN_PLACEMENT_CHECK.invoker().onCheck(entityType, level, spawnType, pos, random, defaultResult);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        return defaultResult;
    }

    public static boolean checkSpawnPosition(Mob mob, ServerLevelAccessor level, EntitySpawnReason spawnType) {
        var result = SpawnEvents.POSITION_CHECK.invoker().onCheck(mob, level, spawnType, null);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        return mob.checkSpawnRules(level, spawnType) && mob.checkSpawnObstruction(level);
    }

    public static boolean checkSpawnPositionSpawner(Mob mob, ServerLevelAccessor level, EntitySpawnReason spawnType, SpawnData spawnData, BaseSpawner spawner) {
        var result = SpawnEvents.POSITION_CHECK.invoker().onCheck(mob, level, spawnType, spawner);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        return (spawnData.getCustomSpawnRules().isPresent() || mob.checkSpawnRules(level, spawnType)) && mob.checkSpawnObstruction(level);
    }

    private EventHooks() {
    }
}
