package com.sighs.merrysnow.event.handler;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;

public class ClientEventHandler {
    public static void init() {
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            var level = Minecraft.getInstance().level;
            if (level != null && level.isRaining()) {
                level.setRainLevel(1.0F);
            }
        });
    }
}
