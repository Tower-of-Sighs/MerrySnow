package com.sighs.merrysnow.init;

import com.sighs.merrysnow.MerrySnow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MODID)
public class Test {
    @SubscribeEvent
    public static void test(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().level == null) return;
//        System.out.print(Minecraft.getInstance().level.getRainLevel(1.0F));
    }
}
