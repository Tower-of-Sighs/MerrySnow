package com.sighs.merrysnow.logic;

import com.sighs.merrysnow.spi.MerrySnowConfig;

/**
 * Biome and weather decisions shared by every target.
 *
 * <p>All methods take the vanilla result as a parameter instead of reaching into Minecraft, so the
 * rules are pure and testable while the mixins only supply the biome id and the vanilla answer.
 */
public final class SnowRules {
    private SnowRules() {
    }

    /**
     * Replaces the vanilla {@code Biome#coldEnoughToSnow} decision used both by
     * {@code getPrecipitationAt} and by the mob spawn guard.
     */
    public static boolean isSnowing(MerrySnowConfig config, String biomeId, boolean vanillaColdEnoughToSnow) {
        String enforce = config.enforceSnowWeather();
        if (ConfigDefaults.DEFAULT.equals(enforce)) {
            String modifier = ModifierList.lookup(config.weatherModify(), biomeId);
            if (ConfigDefaults.DEFAULT.equals(modifier)) {
                return vanillaColdEnoughToSnow;
            }
            return "snow".equals(modifier);
        }
        return Boolean.parseBoolean(enforce);
    }

    /** {@code getPrecipitationAt}: a {@code none} weather modifier removes precipitation entirely. */
    public static boolean precipitationDisabled(MerrySnowConfig config, String biomeId) {
        return "none".equals(ModifierList.lookup(config.weatherModify(), biomeId));
    }

    /** Replaces the vanilla {@code Biome#warmEnoughToRain} decision used by {@code shouldSnow}. */
    public static boolean shouldSnowCover(MerrySnowConfig config, String biomeId, boolean vanillaWarmEnoughToRain) {
        return resolveIceOrSnow(config.enforceSnowCover(), ModifierList.lookup(config.snowModify(), biomeId),
                vanillaWarmEnoughToRain);
    }

    /** Replaces the vanilla {@code Biome#warmEnoughToRain} decision used by {@code shouldFreeze}. */
    public static boolean shouldFreeze(MerrySnowConfig config, String biomeId, boolean vanillaWarmEnoughToRain) {
        return resolveIceOrSnow(config.enforceIceFreeze(), ModifierList.lookup(config.freezeModify(), biomeId),
                vanillaWarmEnoughToRain);
    }

    private static boolean resolveIceOrSnow(String enforce, String modifier, boolean vanillaWarmEnoughToRain) {
        if (ConfigDefaults.DEFAULT.equals(enforce)) {
            if (ConfigDefaults.DEFAULT.equals(modifier)) {
                return vanillaWarmEnoughToRain;
            }
            return !Boolean.parseBoolean(modifier);
        }
        return !Boolean.parseBoolean(enforce);
    }

    /** Multiplier applied on top of a vanilla rain level to produce the sunny snow effect. */
    public static float rainLevelMultiplier(MerrySnowConfig config, float randomSunnySnow) {
        return config.randomSunnySnow() ? randomSunnySnow : config.sunnySnow();
    }

    /** Scales a rain level by the sunny snow multiplier. */
    public static float scaleRainLevel(MerrySnowConfig config, float rainLevel, float randomSunnySnow) {
        return rainLevel * rainLevelMultiplier(config, randomSunnySnow);
    }

    /** Deterministic per-second random factor used when {@code randomSunnySnow} is enabled. */
    public static float nextRandomSunnySnow(long millis) {
        return (millis % 8) / 10F + 0.3F;
    }

    /** Clamps the sunny snow coefficient to the configured range after a scroll wheel step. */
    public static double stepSunnySnow(double value, double delta) {
        if (delta < 0) {
            return Math.max(value - ConfigDefaults.SUNNY_SNOW_STEP, ConfigDefaults.SUNNY_SNOW_MIN);
        }
        return Math.min(value + ConfigDefaults.SUNNY_SNOW_STEP, ConfigDefaults.SUNNY_SNOW_MAX);
    }
}
