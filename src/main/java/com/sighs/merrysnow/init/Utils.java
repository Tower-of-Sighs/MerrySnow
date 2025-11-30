package com.sighs.merrysnow.init;

import com.sighs.merrysnow.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static float redirectRainLevel(Level level, float value) {
        float origin = level.getRainLevel(value);
        boolean random;
        float factor;
        try {
            random = Config.RANDOM_SUNNY_SNOW.get();
        } catch (IllegalStateException e) {
            random = false;
        }
        if (random) {
            factor = ClientUtils.randomSunnySnow;
        } else {
            try {
                factor = Config.SUNNY_SNOW.get().floatValue();
            } catch (IllegalStateException e) {
                factor = 0.5F;
            }
        }
        return origin * factor;
    }

    public static String getBiomeId(LevelReader level, Biome biome) {
        try {
            ResourceLocation rl = level.registryAccess().registryOrThrow(Registries.BIOME).getKey(biome);
            return rl.toString();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getBiomeId(Player player) {
        return getBiomeId(player.level(), player.level().getBiome(player.blockPosition()).value());
    }

    public static String getAlBiomeId(Player player) {
        List<String> result = new ArrayList<>();
        try {
            player.level().registryAccess().registryOrThrow(Registries.BIOME).keySet().forEach(r -> result.add(r.toString()));
        } catch (Exception ignored) {
        }
        return toJsonArray(result);
    }

    public static String toJsonArray(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(items.get(i)).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }
}