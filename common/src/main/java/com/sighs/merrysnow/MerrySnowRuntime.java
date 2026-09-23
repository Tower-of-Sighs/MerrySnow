package com.sighs.merrysnow;

import com.sighs.merrysnow.spi.MerrySnowConfig;
import com.sighs.merrysnow.spi.MerrySnowPlatform;

/**
 * Service locator for the loader-independent code plus the shared client-side sunny snow state.
 *
 * <p>Targets install their {@link MerrySnowPlatform} during initialisation; the mixins and event
 * hooks then read the config through {@link #config()}. The random sunny snow factor is shared
 * state written by the rain level hooks and read by the rain level redirects, so it lives here
 * instead of in a target-specific class.
 */
public final class MerrySnowRuntime {
    private static volatile MerrySnowPlatform platform;
    private static volatile float randomSunnySnow;

    private MerrySnowRuntime() {
    }

    public static void install(MerrySnowPlatform platform) {
        MerrySnowRuntime.platform = platform;
    }

    public static MerrySnowPlatform platform() {
        MerrySnowPlatform current = platform;
        if (current == null) {
            throw new IllegalStateException("MerrySnow platform has not been installed yet");
        }
        return current;
    }

    public static MerrySnowConfig config() {
        return platform().config();
    }

    public static float randomSunnySnow() {
        return randomSunnySnow;
    }

    public static void setRandomSunnySnow(float value) {
        randomSunnySnow = value;
    }
}
