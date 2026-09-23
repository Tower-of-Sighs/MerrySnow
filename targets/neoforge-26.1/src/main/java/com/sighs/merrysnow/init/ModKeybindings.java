package com.sighs.merrysnow.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.sighs.merrysnow.MerrySnow;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MOD_ID)
public class ModKeybindings {
    /** 26.1 replaced the category string with {@code KeyMapping.Category}; its label resolves to
     * {@code key.category.merrysnow.main}. */
    public static final KeyMapping.Category CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(MerrySnow.MOD_ID, "main"));

    public static final KeyMapping CONTROL_KEY = new KeyMapping("key.merrysnow.sunny_snow",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            CATEGORY
    );

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(CONTROL_KEY);
    }
}
