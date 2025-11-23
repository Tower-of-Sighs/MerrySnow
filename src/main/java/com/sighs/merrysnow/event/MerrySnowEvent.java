package com.sighs.merrysnow.event;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.MerrySnow;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MerrySnow.MODID)
public class MerrySnowEvent {
    @SubscribeEvent
    public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (!Config.MERRY_SNOW_WEATHER.get()) return;
        if (event.getSpawnType() != MobSpawnType.NATURAL) return;

        BlockPos blockPos = event.getEntity().blockPosition();
        Biome biome = event.getLevel().getBiome(blockPos).get();
        MobCategory category = event.getEntity().getClassification(false);

        boolean isSnowing = biome.getPrecipitationAt(blockPos).equals(Biome.Precipitation.SNOW);
        boolean isMonster = !category.isFriendly() && !category.isPersistent();

        if (isSnowing && isMonster) {
            event.setSpawnCancelled(true);
        }
    }
}
