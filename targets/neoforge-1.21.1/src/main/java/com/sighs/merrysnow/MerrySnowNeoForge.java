package com.sighs.merrysnow;

import com.mojang.logging.LogUtils;
import com.sighs.merrysnow.spi.MerrySnowConfig;
import com.sighs.merrysnow.spi.MerrySnowPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MerrySnow.MOD_ID)
public final class MerrySnowNeoForge implements MerrySnowPlatform {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public MerrySnowNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        MerrySnowRuntime.install(this);
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
