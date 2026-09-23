package com.sighs.merrysnow.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ClientEvent {
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> weather());
    }

    private static void weather() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.isRaining()) {
            level.setRainLevel(1.0F);
        }
    }
}
