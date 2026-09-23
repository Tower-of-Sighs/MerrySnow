package com.sighs.merrysnow;

import com.mojang.logging.LogUtils;
import com.sighs.merrysnow.init.MerryCommands;
import com.sighs.merrysnow.spi.MerrySnowConfig;
import com.sighs.merrysnow.spi.MerrySnowPlatform;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MerrySnow.MOD_ID)
public final class MerrySnowForge implements MerrySnowPlatform {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public MerrySnowForge() {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        MerrySnowRuntime.install(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        MerryCommands.register(event.getDispatcher());
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
