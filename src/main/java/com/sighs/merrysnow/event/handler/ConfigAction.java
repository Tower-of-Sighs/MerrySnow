package com.sighs.merrysnow.event.handler;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.event.InputEvent;
import com.sighs.merrysnow.init.ClientUtils;
import com.sighs.merrysnow.init.ModKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ConfigAction {
    public static void init() {
        InputEvent.MOUSE_SCROLL.register(ConfigAction::wheelAction);
    }

    public static boolean wheelAction(double scrollDeltaX, double scrollDeltaY, double mouseX, double mouseY,
                                      boolean leftDown, boolean middleDown, boolean rightDown) {
        if (Minecraft.getInstance().player == null) return false;
        if (!ClientUtils.isKeyPressed(ModKeybindings.CONTROL_KEY.key.getValue())) return false;
        modifyValue(scrollDeltaY);
        return true;
    }

    public static void modifyValue(double delta) {
        double value = Config.SUNNY_SNOW.get();
        if (delta < 0) {
            value = Math.max(value - 0.1, 0.1);
        } else {
            value = Math.min(value + 0.1, 1);
        }
        Config.SUNNY_SNOW.set(value);
        Config.SUNNY_SNOW.save();
        Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.merrysnow.sunny_snow", String.valueOf(value).substring(0, 3)), true);
    }
}
