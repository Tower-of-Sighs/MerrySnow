package com.sighs.merrysnow.event;

import com.sighs.merrysnow.MerrySnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.logging.Level;

@Mod.EventBusSubscriber(modid = MerrySnow.MODID, value = Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    public static void weather(ClientPlayerNetworkEvent.LoggingIn event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.isRaining()) {
            level.setRainLevel(1.0F);
        }
    }
}
