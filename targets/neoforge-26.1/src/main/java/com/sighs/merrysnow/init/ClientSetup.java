package com.sighs.merrysnow.init;

import com.sighs.merrysnow.MerrySnow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
//        Minecraft.getInstance().levelRenderer.renderSky();
    }
}
