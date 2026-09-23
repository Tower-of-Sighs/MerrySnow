package com.sighs.merrysnow;

import com.sighs.merrysnow.logic.ConfigDefaults;
import com.sighs.merrysnow.spi.MerrySnowConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MerrySnow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config implements MerrySnowConfig {
    public static final Config INSTANCE = new Config();

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_SNOW_LAYER;
    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_POWDER_SNOW;
    public static ForgeConfigSpec.ConfigValue<Boolean> MERRY_SNOW_WEATHER;

    public static ForgeConfigSpec.DoubleValue SUNNY_SNOW;
    public static ForgeConfigSpec.ConfigValue<Boolean> RANDOM_SUNNY_SNOW;

    public static ForgeConfigSpec.ConfigValue<String> ENFORCE_SNOW_WEATHER;
    public static ForgeConfigSpec.ConfigValue<String> ENFORCE_SNOW_COVER;
    public static ForgeConfigSpec.ConfigValue<String> ENFORCE_ICE_FREEZE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WEATHER_MODIFY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> SNOW_MODIFY;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FREEZE_MODIFY;

    static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Merry Snow");
        MERRY_SNOW_LAYER = BUILDER
                .comment("雪片无碰撞。")
                .define("merrySnowLayer", ConfigDefaults.MERRY_SNOW_LAYER);
        MERRY_POWDER_SNOW = BUILDER
                .comment("细雪有台阶碰撞箱且无冷冻伤害。")
                .define("merryPowderSnow", ConfigDefaults.MERRY_POWDER_SNOW);
        MERRY_SNOW_WEATHER = BUILDER
                .comment("下雪区块不刷怪。")
                .define("merrySnowWeather", ConfigDefaults.MERRY_SNOW_WEATHER);
        BUILDER.pop();

        BUILDER.push("Sunny Snow");
        SUNNY_SNOW = BUILDER
                .comment("晴雪效果系数。")
                .defineInRange("sunnySnow", ConfigDefaults.SUNNY_SNOW, ConfigDefaults.SUNNY_SNOW_MIN,
                        ConfigDefaults.SUNNY_SNOW_MAX);
        RANDOM_SUNNY_SNOW = BUILDER
                .comment("随机晴雪效果。")
                .define("randomSunnySnow", ConfigDefaults.RANDOM_SUNNY_SNOW);
        BUILDER.pop();

        BUILDER.push("Biome Snow");
        ENFORCE_SNOW_WEATHER = BUILDER
                .comment("是否全群系下雪：true、false、default")
                .define("enforceSnowWeather", ConfigDefaults.ENFORCE_SNOW_WEATHER);
        ENFORCE_SNOW_COVER = BUILDER
                .comment("是否全群系积雪：true、false、default")
                .define("enforceSnowCover", ConfigDefaults.ENFORCE_SNOW_COVER);
        ENFORCE_ICE_FREEZE = BUILDER
                .comment("是否全群系结冰：true、false、default")
                .define("enforceIceFreeze", ConfigDefaults.ENFORCE_ICE_FREEZE);
        WEATHER_MODIFY = BUILDER
                .comment("\"minecraft:forest=snow\", \"minecraft:ocean=rain\", \"minecraft:jungle=none\"")
                .defineList("weatherModify",
                        List.of(),
                        entry -> entry instanceof String
                );
        SNOW_MODIFY = BUILDER
                .comment("\"minecraft:forest=true\", \"minecraft:ocean=false\"")
                .defineList("snowModify",
                        List.of(),
                        entry -> entry instanceof String
                );
        FREEZE_MODIFY = BUILDER
                .comment("\"minecraft:forest=true\", \"minecraft:ocean=false\"")
                .defineList("iceModify",
                        List.of(),
                        entry -> entry instanceof String
                );
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    private Config() {
    }

    @Override
    public boolean merrySnowLayer() {
        return MERRY_SNOW_LAYER.get();
    }

    @Override
    public boolean merryPowderSnow() {
        return MERRY_POWDER_SNOW.get();
    }

    @Override
    public boolean merrySnowWeather() {
        return MERRY_SNOW_WEATHER.get();
    }

    @Override
    public float sunnySnow() {
        return SUNNY_SNOW.get().floatValue();
    }

    @Override
    public boolean randomSunnySnow() {
        return RANDOM_SUNNY_SNOW.get();
    }

    @Override
    public String enforceSnowWeather() {
        return ENFORCE_SNOW_WEATHER.get();
    }

    @Override
    public String enforceSnowCover() {
        return ENFORCE_SNOW_COVER.get();
    }

    @Override
    public String enforceIceFreeze() {
        return ENFORCE_ICE_FREEZE.get();
    }

    @Override
    public List<String> weatherModify() {
        return new ArrayList<>(WEATHER_MODIFY.get());
    }

    @Override
    public List<String> snowModify() {
        return new ArrayList<>(SNOW_MODIFY.get());
    }

    @Override
    public List<String> freezeModify() {
        return new ArrayList<>(FREEZE_MODIFY.get());
    }
}
