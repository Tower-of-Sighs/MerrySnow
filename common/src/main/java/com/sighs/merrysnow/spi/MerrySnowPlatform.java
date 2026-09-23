package com.sighs.merrysnow.spi;

/**
 * Entry point every target installs into {@link com.sighs.merrysnow.MerrySnowRuntime} during
 * loader initialisation, so that the shared code can reach the target config and logger.
 */
public interface MerrySnowPlatform {
    MerrySnowConfig config();

    void info(String message);

    void warn(String message);
}
