package com.sighs.merrysnow.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ServerLevelAccessor;

public class SpawnEvents {
    public enum Result {
        SUCCEED,
        DEFAULT,
        FAIL
    }

    public static final Event<SpawnPlacementCheck> SPAWN_PLACEMENT_CHECK = EventFactory.createArrayBacked(
            SpawnPlacementCheck.class,
            callbacks -> (entityType, level, spawnType, pos, random, defaultResult) -> {
                Result result = Result.DEFAULT;
                for (SpawnPlacementCheck cb : callbacks) {
                    Result r = cb.onCheck(entityType, level, spawnType, pos, random, defaultResult);
                    if (r == Result.FAIL) {
                        result = Result.FAIL;
                    } else if (r == Result.SUCCEED && result != Result.FAIL) {
                        result = Result.SUCCEED;
                    }
                }
                return result;
            }
    );

    public static final Event<PositionCheck> POSITION_CHECK = EventFactory.createArrayBacked(
            PositionCheck.class,
            callbacks -> (mob, level, spawnType, spawner) -> {
                Result result = Result.DEFAULT;
                for (PositionCheck cb : callbacks) {
                    Result r = cb.onCheck(mob, level, spawnType, spawner);
                    if (r == Result.FAIL) {
                        result = Result.FAIL;
                    } else if (r == Result.SUCCEED && result != Result.FAIL) {
                        result = Result.SUCCEED;
                    }
                }
                return result;
            }
    );

    @FunctionalInterface
    public interface SpawnPlacementCheck {
        Result onCheck(EntityType<?> entityType, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random, boolean defaultResult);
    }

    @FunctionalInterface
    public interface PositionCheck {
        Result onCheck(Mob mob, ServerLevelAccessor level, EntitySpawnReason spawnType, BaseSpawner spawner);
    }
}
