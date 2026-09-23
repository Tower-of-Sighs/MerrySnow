package com.sighs.merrysnow.event;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.MerrySnow;
import com.sighs.merrysnow.init.ClientUtils;
import com.sighs.merrysnow.init.ModKeybindings;
import com.sighs.merrysnow.logic.SnowRules;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MerrySnow.MOD_ID)
public class ConfigAction {
    @SubscribeEvent
    public static void wheelAction(InputEvent.MouseScrollingEvent event) {
        if (Minecraft.getInstance().player == null) return;
        if (!ClientUtils.isKeyPressed(ModKeybindings.CONTROL_KEY.getKey().getValue())) return;
        modifyValue(event.getScrollDelta());
        event.setCanceled(true);
    }

    public static void modifyValue(double delta) {
        double value = SnowRules.stepSunnySnow(Config.SUNNY_SNOW.get(), delta);
        Config.SUNNY_SNOW.set(value);
        Config.SUNNY_SNOW.save();
        Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.merrysnow.sunny_snow", String.valueOf(value).substring(0, 3)), true);
    }
}
