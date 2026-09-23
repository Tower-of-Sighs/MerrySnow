package com.sighs.merrysnow.event;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.init.ClientUtils;
import com.sighs.merrysnow.init.ModKeybindings;
import com.sighs.merrysnow.logic.SnowRules;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Fabric has no cancellable mouse scroll event, so {@code MouseHandlerMixin} forwards the raw
 * scroll delta here and cancels the vanilla handling when this returns {@code true}.
 */
public class ConfigAction {
    public static boolean wheelAction(double delta) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen != null) return false;
        if (!ClientUtils.isKeyPressed(KeyBindingHelper.getBoundKeyOf(ModKeybindings.CONTROL_KEY).getValue())) {
            return false;
        }
        modifyValue(delta);
        return true;
    }

    public static void modifyValue(double delta) {
        double value = SnowRules.stepSunnySnow(Config.INSTANCE.sunnySnowDouble(), delta);
        Config.INSTANCE.setSunnySnow(value);
        Config.INSTANCE.save();
        Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.merrysnow.sunny_snow", String.valueOf(value).substring(0, 3)), true);
    }
}
