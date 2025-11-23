package com.sighs.merrysnow.init;

import com.sighs.merrysnow.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class Utils {
    public static float redirectRainLevel(Level level, float value) {
        float origin = level.getRainLevel(value);
//        return origin * Config.SUNNY_SNOW.get().floatValue();
        return origin;
    }

    public static String getBiomeId(Biome biome) {
        ResourceLocation rl = ForgeRegistries.BIOMES.getKey(biome);
        if (rl != null) return rl.toString();
        return null;
    }
}
