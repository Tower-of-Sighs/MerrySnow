package com.sighs.merrysnow;

import com.mojang.logging.LogUtils;
import com.sighs.merrysnow.init.MerryCommands;
import com.sighs.merrysnow.spi.MerrySnowConfig;
import com.sighs.merrysnow.spi.MerrySnowPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;

public final class MerrySnowFabric implements ModInitializer, MerrySnowPlatform {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        MerrySnowRuntime.install(this);

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> MerryCommands.register(dispatcher));
    }

    @Override
    public MerrySnowConfig config() {
        return Config.INSTANCE;
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }
}
