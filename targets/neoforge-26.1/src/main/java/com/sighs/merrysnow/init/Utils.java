package com.sighs.merrysnow.init;

import com.sighs.merrysnow.MerrySnowRuntime;
import com.sighs.merrysnow.logic.SnowRules;
import com.sighs.merrysnow.logic.StringLists;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static float redirectRainLevel(Level level, float value) {
        return SnowRules.scaleRainLevel(MerrySnowRuntime.config(), level.getRainLevel(value),
                MerrySnowRuntime.randomSunnySnow());
    }

    public static String getBiomeId(LevelReader level, Biome biome) {
        try {
            Identifier id = level.registryAccess().lookupOrThrow(Registries.BIOME).getKey(biome);
            return id.toString();
        } catch (Exception ignored) {}
        return null;
    }

    public static String getBiomeId(Player player) {
        return getBiomeId(player.level(), player.level().getBiome(player.blockPosition()).value());
    }

    public static String getAlBiomeId(Player player) {
        List<String> result = new ArrayList<>();
        try {
            player.level().registryAccess().lookupOrThrow(Registries.BIOME).keySet().forEach(r -> result.add(r.toString()));
        } catch (Exception ignored) {}
        return StringLists.toJsonArray(result);
    }
}
