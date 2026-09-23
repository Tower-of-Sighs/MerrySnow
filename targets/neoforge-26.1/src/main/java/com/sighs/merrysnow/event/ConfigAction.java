package com.sighs.merrysnow.event;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.MerrySnow;
import com.sighs.merrysnow.init.ClientUtils;
import com.sighs.merrysnow.init.ModKeybindings;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MOD_ID)
public class ConfigAction {
    @SubscribeEvent
    public static void wheelAction(InputEvent.MouseScrollingEvent event) {
        if (Minecraft.getInstance().player == null) return;
        if (!ClientUtils.isKeyPressed(ModKeybindings.CONTROL_KEY.getKey().getValue())) return;
        modifyValue(event.getScrollDeltaY());
        event.setCanceled(true);
    }

    public static void modifyValue(double delta) {
        double value = SnowRules.stepSunnySnow(Config.SUNNY_SNOW.get(), delta);
        Config.SUNNY_SNOW.set(value);
        Config.SUNNY_SNOW.save();
        // 26.1 replaced Player#displayClientMessage(Component, true) with sendOverlayMessage(Component).
        Minecraft.getInstance().player.sendOverlayMessage(Component.translatable("message.merrysnow.sunny_snow", String.valueOf(value).substring(0, 3)));
    }
}
