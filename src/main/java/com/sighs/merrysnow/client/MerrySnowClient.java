package com.sighs.merrysnow.client;

import com.sighs.merrysnow.event.handler.ClientEventHandler;
import com.sighs.merrysnow.event.handler.ConfigAction;
import com.sighs.merrysnow.init.ModKeybindings;
import net.fabricmc.api.ClientModInitializer;

public class MerrySnowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeybindings.register();
        ClientEventHandler.init();
        ConfigAction.init();
    }
}
