package com.sighs.merrysnow;

import com.sighs.merrysnow.event.handler.MerrySnowEventHandler;
import com.sighs.merrysnow.init.MerryCommands;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public class MerrySnow implements ModInitializer {
    public static final String MODID = "merrysnow";

    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, Config.SPEC);
        MerryCommands.init();
        MerrySnowEventHandler.init();
    }
}
