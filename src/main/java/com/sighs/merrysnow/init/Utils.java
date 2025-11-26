package com.sighs.merrysnow.init;

import com.sighs.merrysnow.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Utils {
    public static float redirectRainLevel(Level level, float value) {
        float origin = level.getRainLevel(value);
        if (Config.RANDOM_SUNNY_SNOW.get()) {
            return origin * ClientUtils.randomSunnySnow;
        } else {
            return origin * Config.SUNNY_SNOW.get().floatValue();
        }
    }

    public static String getBiomeId(Biome biome) {
        ResourceLocation rl = ForgeRegistries.BIOMES.getKey(biome);
        if (rl != null) return rl.toString();
        return null;
    }

    public static String getBiomeId(Level level, Biome biome) {
        try {
            ResourceLocation rl = level.registryAccess().registryOrThrow(Registries.BIOME).getKey(biome);
            return rl.toString();
        } catch (Exception ignored) {}
        return null;
    }

    public static String getBiomeId(Player player) {
        return getBiomeId(player.level(), player.level().getBiome(player.blockPosition()).get());
    }

    public static String getAlBiomeId(Player player) {
        List<String> result = new ArrayList<>();
        try {
            player.level().registryAccess().registryOrThrow(Registries.BIOME).keySet().forEach(r -> result.add(r.toString()));
        } catch (Exception ignored) {}
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
