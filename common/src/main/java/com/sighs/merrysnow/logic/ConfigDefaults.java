package com.sighs.merrysnow.logic;

/** Default values and sentinel strings shared by every target config implementation. */
public final class ConfigDefaults {
    /** Value meaning "fall back to the vanilla decision". */
    public static final String DEFAULT = "default";

    public static final boolean MERRY_SNOW_LAYER = true;
    public static final boolean MERRY_POWDER_SNOW = true;
    public static final boolean MERRY_SNOW_WEATHER = true;

    public static final double SUNNY_SNOW = 0.5D;
    public static final double SUNNY_SNOW_MIN = 0.1D;
    public static final double SUNNY_SNOW_MAX = 1.0D;
    public static final double SUNNY_SNOW_STEP = 0.1D;
    public static final boolean RANDOM_SUNNY_SNOW = false;

    public static final String ENFORCE_SNOW_WEATHER = "true";
    public static final String ENFORCE_SNOW_COVER = "false";
    public static final String ENFORCE_ICE_FREEZE = DEFAULT;

    private ConfigDefaults() {
    }
}
