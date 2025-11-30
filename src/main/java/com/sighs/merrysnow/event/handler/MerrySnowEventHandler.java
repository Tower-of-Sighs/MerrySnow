package com.sighs.merrysnow.event.handler;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.event.SpawnEvents;
import com.sighs.merrysnow.init.Utils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ServerLevelAccessor;

public class MerrySnowEventHandler {

    public static void init() {
        SpawnEvents.POSITION_CHECK.register(MerrySnowEventHandler::onFinalizeSpawn);
    }

    public static SpawnEvents.Result onFinalizeSpawn(Mob mob, ServerLevelAccessor level, MobSpawnType spawnType, BaseSpawner spawner) {
        if (!Config.MERRY_SNOW_WEATHER.get()) return SpawnEvents.Result.DEFAULT;
        if (spawnType != MobSpawnType.NATURAL) return SpawnEvents.Result.DEFAULT;

        var blockPos = mob.blockPosition();
        var biome = level.getBiome(blockPos).value();
        if (!level.getLevel().isRaining()) return SpawnEvents.Result.DEFAULT;
//        if (!biome.hasPrecipitation()) return SpawnEvents.Result.DEFAULT;
        var category = mob.getType().getCategory();

        boolean isSnowing;
        if (Config.ENFORCE_SNOW_WEATHER.get().equals("default")) {
            String id = Utils.getBiomeId(level.getLevel(), biome);
            String result = Config.getWeatherModify(id);
            if (result.equals("default")) {
                isSnowing = biome.coldEnoughToSnow(blockPos);
            } else isSnowing = result.equals("snow");
        } else isSnowing = Boolean.parseBoolean(Config.ENFORCE_SNOW_WEATHER.get());
        boolean isMonster = !category.isFriendly() && !category.isPersistent();

        if (isSnowing && isMonster) {
            return SpawnEvents.Result.FAIL;
        }
        return SpawnEvents.Result.DEFAULT;
    }
}
