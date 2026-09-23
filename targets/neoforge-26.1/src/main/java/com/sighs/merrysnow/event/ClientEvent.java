package com.sighs.merrysnow.event;

import com.sighs.merrysnow.MerrySnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(modid = MerrySnow.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void weather(ClientPlayerNetworkEvent.LoggingIn event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.isRaining()) {
            level.setRainLevel(1.0F);
        }
    }
}
