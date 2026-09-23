package com.sighs.merrysnow.spi;

import java.util.List;

/**
 * Config contract implemented once per target.
 *
 * <p>Every method is expected to return the live value of the platform config, because the
 * sunny snow coefficient can be edited in game through the scroll wheel binding. Values are
 * plain Java types only so that the decision logic can stay in {@code common}.
 */
public interface MerrySnowConfig {
    boolean merrySnowLayer();

    boolean merryPowderSnow();

    boolean merrySnowWeather();

    /** Sunny snow coefficient, clamped to {@code [0.1, 1.0]}. */
    float sunnySnow();

    boolean randomSunnySnow();

    /** {@code "true"}, {@code "false"} or {@code "default"}. */
    String enforceSnowWeather();

    /** {@code "true"}, {@code "false"} or {@code "default"}. */
    String enforceSnowCover();

    /** {@code "true"}, {@code "false"} or {@code "default"}. */
    String enforceIceFreeze();

    /** Raw {@code "minecraft:forest=snow"} entries, resolved by the shared rule logic. */
    List<String> weatherModify();

    /** Raw {@code "minecraft:forest=true"} entries, resolved by the shared rule logic. */
    List<String> snowModify();

    /** Raw {@code "minecraft:forest=true"} entries, resolved by the shared rule logic. */
    List<String> freezeModify();
}
