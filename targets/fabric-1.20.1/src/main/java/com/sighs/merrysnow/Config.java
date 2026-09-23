package com.sighs.merrysnow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sighs.merrysnow.logic.ConfigDefaults;
import com.sighs.merrysnow.spi.MerrySnowConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Fabric has no built-in config API, so the options live in {@code config/merrysnow.json}.
 *
 * <p>Key names mirror the Forge/NeoForge option names, so a config can be copied between
 * loaders with the TOML to JSON translation only.
 */
public final class Config implements MerrySnowConfig {
    public static final Config INSTANCE = new Config();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("merrysnow.json");

    private JsonObject root = new JsonObject();

    private Config() {
        load();
    }

    public void load() {
        if (!Files.isRegularFile(FILE)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(FILE, StandardCharsets.UTF_8)) {
            JsonObject parsed = GSON.fromJson(reader, JsonObject.class);
            if (parsed != null) {
                root = parsed;
            }
        } catch (IOException | RuntimeException e) {
            MerrySnowRuntime.platform().warn("Failed to read " + FILE + ": " + e.getMessage());
        }
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }
        } catch (IOException e) {
            MerrySnowRuntime.platform().warn("Failed to write " + FILE + ": " + e.getMessage());
        }
    }

    private boolean getBoolean(String key, boolean fallback) {
        JsonElement value = root.get(key);
        return value == null || value.isJsonNull() ? fallback : value.getAsBoolean();
    }

    private String getString(String key, String fallback) {
        JsonElement value = root.get(key);
        return value == null || value.isJsonNull() ? fallback : value.getAsString();
    }

    private List<String> getList(String key) {
        List<String> result = new ArrayList<>();
        JsonElement value = root.get(key);
        if (value == null || !value.isJsonArray()) {
            return result;
        }
        JsonArray array = value.getAsJsonArray();
        for (JsonElement element : array) {
            result.add(element.getAsString());
        }
        return result;
    }

    @Override
    public boolean merrySnowLayer() {
        return getBoolean("merrySnowLayer", ConfigDefaults.MERRY_SNOW_LAYER);
    }

    @Override
    public boolean merryPowderSnow() {
        return getBoolean("merryPowderSnow", ConfigDefaults.MERRY_POWDER_SNOW);
    }

    @Override
    public boolean merrySnowWeather() {
        return getBoolean("merrySnowWeather", ConfigDefaults.MERRY_SNOW_WEATHER);
    }

    /** Raw coefficient, kept as a double so the scroll wheel does not accumulate float error. */
    public double sunnySnowDouble() {
        JsonElement value = root.get("sunnySnow");
        if (value == null || value.isJsonNull()) {
            return ConfigDefaults.SUNNY_SNOW;
        }
        try {
            return Math.min(Math.max(value.getAsDouble(), ConfigDefaults.SUNNY_SNOW_MIN), ConfigDefaults.SUNNY_SNOW_MAX);
        } catch (RuntimeException e) {
            return ConfigDefaults.SUNNY_SNOW;
        }
    }

    @Override
    public float sunnySnow() {
        return (float) sunnySnowDouble();
    }

    public void setSunnySnow(double value) {
        root.addProperty("sunnySnow", value);
    }

    @Override
    public boolean randomSunnySnow() {
        return getBoolean("randomSunnySnow", ConfigDefaults.RANDOM_SUNNY_SNOW);
    }

    @Override
    public String enforceSnowWeather() {
        return getString("enforceSnowWeather", ConfigDefaults.ENFORCE_SNOW_WEATHER);
    }

    @Override
    public String enforceSnowCover() {
        return getString("enforceSnowCover", ConfigDefaults.ENFORCE_SNOW_COVER);
    }

    @Override
    public String enforceIceFreeze() {
        return getString("enforceIceFreeze", ConfigDefaults.ENFORCE_ICE_FREEZE);
    }

    @Override
    public List<String> weatherModify() {
        return getList("weatherModify");
    }

    @Override
    public List<String> snowModify() {
        return getList("snowModify");
    }

    @Override
    public List<String> freezeModify() {
        return getList("iceModify");
    }
}
