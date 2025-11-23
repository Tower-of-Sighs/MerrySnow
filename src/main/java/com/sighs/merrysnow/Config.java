package com.sighs.merrysnow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = MerrySnow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_SNOW_LAYER;
    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_POWDER_SNOW;
    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_SNOW_WEATHER;
    public static ForgeConfigSpec.DoubleValue SUNNY_SNOW;
    public static ForgeConfigSpec.ConfigValue<Boolean> RANDOM_SUNNY_SNOW;

    static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Merry Snow");
        MERRY_SNOW_LAYER = BUILDER
                .comment("雪片无碰撞。")
                .define("merrySnowLayer", true);
        MERRY_POWDER_SNOW = BUILDER
                .comment("细雪有台阶碰撞箱且无冷冻伤害。")
                .define("merryPowderSnow", true);
        MERRY_SNOW_WEATHER = BUILDER
                .comment("下雪区块不刷怪。")
                .define("merrySnowWeather", true);
        BUILDER.pop();

        BUILDER.push("Sunny Snow");
        SUNNY_SNOW = BUILDER
                .comment("晴雪效果系数。")
                .defineInRange("sunnySnow", 0.5, 0, 1);
        RANDOM_SUNNY_SNOW = BUILDER
                .comment("随机晴雪效果。")
                .define("merrySnowWeather", false);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
