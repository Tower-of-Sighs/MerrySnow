package com.sighs.merrysnow.init;

import com.sighs.merrysnow.MerrySnow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
//        Minecraft.getInstance().levelRenderer.renderSky();
    }
}
