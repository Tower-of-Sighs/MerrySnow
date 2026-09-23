package com.sighs.merrysnow;

import com.sighs.merrysnow.event.ClientEvent;
import com.sighs.merrysnow.init.ModKeybindings;
import net.fabricmc.api.ClientModInitializer;

public final class MerrySnowFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeybindings.register();
        ClientEvent.register();
    }
}
