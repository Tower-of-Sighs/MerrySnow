package com.sighs.merrysnow.compat.clothconfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.sighs.merrysnow.Config;
import java.util.ArrayList;

public class MerrySnowClothConfigScreen {

    private static final String TRANSLATE_TITLE = "config.merrysnow.title";
    private static final String TRANSLATE_MERRY_SNOW = "config.merrysnow.merry_snow";
    private static final String TRANSLATE_SUNNY_SNOW = "config.merrysnow.sunny_snow";
    private static final String TRANSLATE_BIOME_SNOW = "config.merrysnow.biome_snow";

    public enum TriState {DEFAULT, TRUE, FALSE}

    public static Screen createClothConfigScreen(Screen parent) {
        ConfigBuilder root = ConfigBuilder.create().setTitle(Component.translatable(TRANSLATE_TITLE)).setParentScreen(parent);
        root.setGlobalized(true);
        root.setGlobalizedExpanded(false);
        ConfigEntryBuilder entry = root.entryBuilder();

        ConfigCategory catMerry = root.getOrCreateCategory(Component.translatable(TRANSLATE_MERRY_SNOW));
        catMerry.addEntry(entry.startBooleanToggle(Component.translatable("config.merrysnow.merry_snow.merrySnowLayer"), Config.MERRY_SNOW_LAYER.get())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.merrysnow.merry_snow.merrySnowLayer.tooltip"))
                .setSaveConsumer(Config.MERRY_SNOW_LAYER::set)
                .build());
        catMerry.addEntry(entry.startBooleanToggle(Component.translatable("config.merrysnow.merry_snow.merryPowderSnow"), Config.MERRY_POWDER_SNOW.get())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.merrysnow.merry_snow.merryPowderSnow.tooltip"))
                .setSaveConsumer(Config.MERRY_POWDER_SNOW::set)
                .build());
        catMerry.addEntry(entry.startBooleanToggle(Component.translatable("config.merrysnow.merry_snow.merrySnowWeather"), Config.MERRY_SNOW_WEATHER.get())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.merrysnow.merry_snow.merrySnowWeather.tooltip"))
                .setSaveConsumer(Config.MERRY_SNOW_WEATHER::set)
                .build());

        ConfigCategory catSunny = root.getOrCreateCategory(Component.translatable(TRANSLATE_SUNNY_SNOW));
        catSunny.addEntry(entry.startDoubleField(Component.translatable("config.merrysnow.sunny_snow.sunnySnow"), Config.SUNNY_SNOW.get())
                .setMin(0.1).setMax(1.0).setDefaultValue(0.5)
                .setTooltip(Component.translatable("config.merrysnow.sunny_snow.sunnySnow.tooltip"))
                .setSaveConsumer(Config.SUNNY_SNOW::set)
                .build());
        catSunny.addEntry(entry.startBooleanToggle(Component.translatable("config.merrysnow.sunny_snow.randomSunnySnow"), Config.RANDOM_SUNNY_SNOW.get())
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.merrysnow.sunny_snow.randomSunnySnow.tooltip"))
                .setSaveConsumer(Config.RANDOM_SUNNY_SNOW::set)
                .build());

        ConfigCategory catBiome = root.getOrCreateCategory(Component.translatable(TRANSLATE_BIOME_SNOW));
        TriState snowWeather = toTriState(Config.ENFORCE_SNOW_WEATHER.get());
        catBiome.addEntry(entry.startEnumSelector(Component.translatable("config.merrysnow.biome_snow.enforceSnowWeather"), TriState.class, snowWeather)
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.enforceSnowWeather.tooltip"))
                .setSaveConsumer(v -> Config.ENFORCE_SNOW_WEATHER.set(fromTriState(v)))
                .build());
        TriState snowCover = toTriState(Config.ENFORCE_SNOW_COVER.get());
        catBiome.addEntry(entry.startEnumSelector(Component.translatable("config.merrysnow.biome_snow.enforceSnowCover"), TriState.class, snowCover)
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.enforceSnowCover.tooltip"))
                .setSaveConsumer(v -> Config.ENFORCE_SNOW_COVER.set(fromTriState(v)))
                .build());
        TriState iceFreeze = toTriState(Config.ENFORCE_ICE_FREEZE.get());
        catBiome.addEntry(entry.startEnumSelector(Component.translatable("config.merrysnow.biome_snow.enforceIceFreeze"), TriState.class, iceFreeze)
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.enforceIceFreeze.tooltip"))
                .setSaveConsumer(v -> Config.ENFORCE_ICE_FREEZE.set(fromTriState(v)))
                .build());

        catBiome.addEntry(entry.startStrList(Component.translatable("config.merrysnow.biome_snow.weatherModify"), new ArrayList<>(Config.WEATHER_MODIFY.get()))
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.weatherModify.tooltip"))
                .setSaveConsumer(Config.WEATHER_MODIFY::set)
                .build());
        catBiome.addEntry(entry.startStrList(Component.translatable("config.merrysnow.biome_snow.snowModify"), new ArrayList<>(Config.SNOW_MODIFY.get()))
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.snowModify.tooltip"))
                .setSaveConsumer(Config.SNOW_MODIFY::set)
                .build());
        catBiome.addEntry(entry.startStrList(Component.translatable("config.merrysnow.biome_snow.iceModify"), new ArrayList<>(Config.FREEZE_MODIFY.get()))
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Component.translatable("config.merrysnow.biome_snow.iceModify.tooltip"))
                .setSaveConsumer(Config.FREEZE_MODIFY::set)
                .build());

        root.setSavingRunnable(() -> {
        });
        return root.build();
    }

    private static TriState toTriState(String v) {
        if ("true".equalsIgnoreCase(v)) return TriState.TRUE;
        if ("false".equalsIgnoreCase(v)) return TriState.FALSE;
        return TriState.DEFAULT;
    }

    private static String fromTriState(TriState v) {
        return switch (v) {
            case TRUE -> "true";
            case FALSE -> "false";
            default -> "default";
        };
    }
}
