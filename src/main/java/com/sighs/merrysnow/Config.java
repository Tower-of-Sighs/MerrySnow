package com.sighs.merrysnow;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@SuppressWarnings("deprecation")
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.ConfigValue<Boolean> MERRY_SNOW_LAYER;
    public static ModConfigSpec.ConfigValue<Boolean> MERRY_POWDER_SNOW;
    public static ModConfigSpec.ConfigValue<Boolean> MERRY_SNOW_WEATHER;

    public static ModConfigSpec.DoubleValue SUNNY_SNOW;
    public static ModConfigSpec.ConfigValue<Boolean> RANDOM_SUNNY_SNOW;

    public static ModConfigSpec.ConfigValue<String> ENFORCE_SNOW_WEATHER;
    public static ModConfigSpec.ConfigValue<String> ENFORCE_SNOW_COVER;
    public static ModConfigSpec.ConfigValue<String> ENFORCE_ICE_FREEZE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> WEATHER_MODIFY;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SNOW_MODIFY;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FREEZE_MODIFY;

    static final ModConfigSpec SPEC;

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
                .defineInRange("sunnySnow", 0.5, 0.1, 1);
        RANDOM_SUNNY_SNOW = BUILDER
                .comment("随机晴雪效果。")
                .define("randomSunnySnow", false);
        BUILDER.pop();

        BUILDER.push("Biome Snow");
        ENFORCE_SNOW_WEATHER = BUILDER
                .comment("是否全群系下雪：true、false、default")
                .define("enforceSnowWeather", "true");
        ENFORCE_SNOW_COVER = BUILDER
                .comment("是否全群系积雪：true、false、default")
                .define("enforceSnowCover", "false");
        ENFORCE_ICE_FREEZE = BUILDER
                .comment("是否全群系结冰：true、false、default")
                .define("enforceIceFreeze", "default");
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

    public static String getWeatherModify(String id) {
        for (String b : WEATHER_MODIFY.get()) {
            String[] entry = b.split("=");
            if (entry[0].equals(id)) return entry[1];
        }
        return "default";
    }

    public static String getSnowModify(String id) {
        for (String b : SNOW_MODIFY.get()) {
            String[] entry = b.split("=");
            if (entry[0].equals(id)) return entry[1];
        }
        return "default";
    }

    public static String getFreezeModify(String id) {
        for (String b : FREEZE_MODIFY.get()) {
            String[] entry = b.split("=");
            if (entry[0].equals(id)) return entry[1];
        }
        return "default";
    }
}