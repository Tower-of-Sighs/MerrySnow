package com.sighs.merrysnow.event;

import com.sighs.merrysnow.MerrySnow;
import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.init.MerryCommands;
import com.sighs.merrysnow.init.Utils;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = MerrySnow.MOD_ID)
public class MerrySnowEvent {
    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (!MerrySnowRuntime.config().merrySnowWeather()) return;
        if (event.getSpawnType() != EntitySpawnReason.NATURAL) return;

        BlockPos blockPos = event.getEntity().blockPosition();
        Biome biome = event.getLevel().getBiome(blockPos).value();
        if (!event.getLevel().getLevel().isRaining()) return;
        if (!biome.hasPrecipitation()) return;
        MobCategory category = event.getEntity().getType().getCategory();

        boolean isSnowing = SnowRules.isSnowing(MerrySnowRuntime.config(),
                Utils.getBiomeId(event.getLevel().getLevel(), biome),
                biome.coldEnoughToSnow(blockPos, event.getLevel().getLevel().getSeaLevel()));
        boolean isMonster = !category.isFriendly() && !category.isPersistent();

        if (isSnowing && isMonster) {
            event.setSpawnCancelled(true);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        MerryCommands.register(event.getDispatcher());
    }
}
