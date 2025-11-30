package com.sighs.merrysnow.compat.modmenu;

import com.sighs.merrysnow.compat.clothconfig.MerrySnowClothConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            return MerrySnowClothConfigScreen::createClothConfigScreen;
        }
        return parent -> null;
    }
}